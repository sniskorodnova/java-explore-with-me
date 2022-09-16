package ru.practicum.ewm.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.EventCantBeModifiedException;
import ru.practicum.ewm.exception.IllegalEventStateException;
import ru.practicum.ewm.exception.UserNotAllowedToViewEventException;
import ru.practicum.ewm.model.event.*;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.users.User;
import ru.practicum.ewm.storage.event.EventRepository;
import ru.practicum.ewm.storage.location.LocationRepository;
import ru.practicum.ewm.storage.users.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository,
                            UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EventDto create(NewEventDto newEvent, Long userId) {
        Event event = EventMapper.newToEvent(newEvent);
        event.setState(State.PENDING);
        Location location = event.getLocation();
        locationRepository.save(location);
        User user = userRepository.findById(userId).orElseThrow();
        event.setInitiator(user);
        return EventMapper.toEventDto(eventRepository.save(event));
    }

    @Override
    public EventDto updateByUser(NewShortEventDto newShortEvent, Long userId) {
        Event event = eventRepository.findById(newShortEvent.getId()).orElseThrow();
        if (!event.getState().equals(State.PENDING)) {
            throw new IllegalEventStateException("Event with id = " + event.getId() + " is not PENDING");
        } else {
            event.setTitle(newShortEvent.getTitle());
            event.setAnnotation(newShortEvent.getAnnotation());
            event.setDescription(newShortEvent.getDescription());
            event.setEventDate(newShortEvent.getEventDate());
            event.setCategoryId(newShortEvent.getCategoryId());
            event.setPaid(newShortEvent.getPaid());
            event.setParticipantLimit(newShortEvent.getParticipantLimit());
            return EventMapper.toEventDto(eventRepository.save(event));
        }
    }

    @Override
    public List<EventDto> getAllForUser(Long userId, Integer from, Integer size) {
        List<EventDto> foundList = new ArrayList<>();
        for (Event event : eventRepository.findByInitiator_IdOrderByIdAsc(userId,
                PageRequest.of(from / size, size))) {
            foundList.add(EventMapper.toEventDto(event));
        }
        return foundList;
    }

    @Override
    public EventDto getById(Long userId, Long eventId) {
        if (!eventRepository.findById(eventId).orElseThrow().getInitiator().getId().equals(userId)) {
            throw new UserNotAllowedToViewEventException("Event doesn't belong to user with id = " + userId);
        } else {
            return EventMapper.toEventDto(eventRepository.findById(eventId).orElseThrow());
        }
    }

    @Override
    public EventDto rejectByUser(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!event.getInitiator().getId().equals(userId)) {
            throw new UserNotAllowedToViewEventException("Event doesn't belong to user with id = " + userId);
        } else {
            if (event.getState() != State.PENDING) {
                throw new EventCantBeModifiedException("Event with id = " + userId + " can't be modified");
            } else {
                event.setState(State.REJECTED);
                eventRepository.save(event);
                return EventMapper.toEventDto(event);
            }
        }
    }

    @Override
    public List<EventDto> getAllForAdmin(List<Integer> users, List<String> states, List<Integer> categories,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        List<EventDto> foundList = new ArrayList<>();
        List<Event> events = eventRepository.findAllForAdmin(users, states, categories, rangeStart, rangeEnd,
                PageRequest.of(from / size, size));
        if (events != null) {
            for (Event event : events) {
                foundList.add(EventMapper.toEventDto(event));
            }
            return foundList;
        } else {
            return null;
        }
    }

    @Override
    public EventDto publishByAdmin(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (event.getState() == State.PENDING && event.getEventDate().isAfter(LocalDateTime.now().plusHours(1))) {
            event.setState(State.PUBLISHED);
            return EventMapper.toEventDto(eventRepository.save(event));
        } else {
            throw new EventCantBeModifiedException("Event can't be published");
        }
    }

    @Override
    public EventDto rejectByAdmin(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (event.getState() != State.PUBLISHED) {
            event.setState(State.REJECTED);
            return EventMapper.toEventDto(eventRepository.save(event));
        } else {
            throw new EventCantBeModifiedException("Event can't be rejected");
        }
    }

    @Override
    public EventDto updateByAdmin(Long eventId, NewEventDto newEvent) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        locationRepository.save(newEvent.getLocation());
        event.setTitle(newEvent.getTitle());
        event.setAnnotation(newEvent.getAnnotation());
        event.setDescription(newEvent.getDescription());
        event.setLocation(newEvent.getLocation());
        event.setEventDate(newEvent.getEventDate());
        event.setCategoryId(newEvent.getCategoryId());
        event.setPaid(newEvent.getPaid());
        event.setParticipantLimit(newEvent.getParticipantLimit());
        return EventMapper.toEventDto(eventRepository.save(event));
    }

    @Override
    public List<EventDto> getAllPublic(String text, Integer[] categories, Boolean paid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        List<EventDto> foundList = new ArrayList<>();
        return foundList;
    }

    @Override
    public EventDto getByIdPublic(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (event.getState() == State.PUBLISHED) {
            return EventMapper.toEventDto(event);
        } else {
            throw new UserNotAllowedToViewEventException("Event can't be viewed by unauthorized user");
        }
    }
}
