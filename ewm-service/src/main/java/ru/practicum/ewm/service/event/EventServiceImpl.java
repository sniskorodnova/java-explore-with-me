package ru.practicum.ewm.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.*;
import ru.practicum.ewm.model.category.CategoryMapper;
import ru.practicum.ewm.model.event.*;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.model.request.RequestState;
import ru.practicum.ewm.model.stats.NewStatsDto;
import ru.practicum.ewm.model.users.User;
import ru.practicum.ewm.storage.category.CategoryRepository;
import ru.practicum.ewm.storage.event.EventRepository;
import ru.practicum.ewm.storage.location.LocationRepository;
import ru.practicum.ewm.storage.request.RequestRepository;
import ru.practicum.ewm.storage.users.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс, имплементирующий интерфейс для работы сервиса событий
 */
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final EventClient eventClient;

    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository,
                            UserRepository userRepository, EventClient eventClient,
                            RequestRepository requestRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.eventClient = eventClient;
        this.requestRepository = requestRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Метод для создания события. Дата и время события не может быть раньше, чем через два часа от текущего момента
     */
    @Override
    public EventDto create(NewEventDto newEvent, Long userId) {
        if (newEvent.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EventCantBeCreatedException("Event time is less than in 2 hours or in the past");
        } else {
            if (userRepository.findById(userId).isEmpty()) {
                throw new UserNotFoundException("User with id = " + userId + " doesn't exist");
            } else {
                Event event = EventMapper.newToEvent(newEvent);
                event.setEventState(EventState.PENDING);
                Location location = event.getLocation();
                locationRepository.save(location);
                User user = userRepository.findById(userId).get();
                event.setInitiator(user);
                return EventMapper.toEventDto(eventRepository.save(event));
            }
        }
    }

    /**
     * Метод для редактирования события текущим пользователем. Дата и время события не может быть раньше,
     * чем через два часа от текущего момента. Изменить можно только отмененные события или события в состоянии
     * ожидания модерации. Если редактируется отмененное событие, то оно автоматически переходит в состояние
     * ожидания модерации
     */
    @Override
    public EventDto updateByUser(NewShortEventDto newShortEvent, Long userId) {
        if (eventRepository.findById(newShortEvent.getId()).isEmpty()) {
            throw new EventNotFoundException("Event with id = " + newShortEvent.getId() + " doesn't exist");
        } else {
            Event event = eventRepository.findById(newShortEvent.getId()).get();
            if (!event.getInitiator().getId().equals(userId)) {
                throw new EventCantBeModifiedException("Event with id = " + newShortEvent.getId()
                        + " doesn't belong to user with id = " + userId);
            } else {
                if (!event.getEventState().equals(EventState.PENDING)
                        && !event.getEventState().equals(EventState.REJECTED)) {
                    throw new IllegalEventStateException("Event with id = " + event.getId()
                            + " is not PENDING or REJECTED");
                } else {
                    if (newShortEvent.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                        throw new EventCantBeCreatedException("Event time is less than in 2 hours or in the past");
                    } else {
                        if (newShortEvent.getParticipantLimit() != 0
                                && newShortEvent.getParticipantLimit()
                                > requestRepository.findByEventIdAndRequestStateIsNotIn(newShortEvent.getId(),
                                List.of(RequestState.CANCELLED, RequestState.REJECTED)).size()) {
                            throw new EventCantBeModifiedException("Participant limit can't be more than "
                                    + "amount of requests in statuses PENDING, ACCEPTED");
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
                }
            }
        }
    }

    /**
     * Метод для получения всех событий, добавленных текущим пользователем с пагинацией (в ответе выводится
     * количество просмотров по этому событию)
     */
    @Override
    public List<EventDtoWithViews> getAllForUser(Long userId, Integer from, Integer size) {
        List<EventDtoWithViews> foundList = new ArrayList<>();
        LocalDateTime minDate = eventRepository.findMinDate().getEventDate();
        LocalDateTime maxDate = eventRepository.findMaxDate().getEventDate();
        for (Event event : eventRepository.findByInitiatorIdOrderByIdAsc(userId,
                PageRequest.of(from / size, size))) {
            String uri = "/events/" + event.getId();
            ResponseEntity<Object> list = eventClient.get(minDate, maxDate, uri, false);
            @SuppressWarnings({"unchecked"}) List<Map<String, Object>> listStat = (List<Map<String, Object>>) list.getBody();
            long hits = 0L;
            if (listStat != null && listStat.size() != 0) {
                Map<String, Object> el = listStat.get(0);
                hits = Long.parseLong(String.valueOf(el.get("hits")));
            }
            EventDtoWithViews eventDtoWithViews = EventMapper.toEventDtoWithViews(event);
            eventDtoWithViews.setViews(hits);
            eventDtoWithViews.setCategory(CategoryMapper.toCategoryDto(categoryRepository
                    .findById(eventDtoWithViews.getCategory().getId()).orElseThrow()));
            List<Request> confirmed = requestRepository.findByEventIdAndRequestState(event.getId(),
                    RequestState.ACCEPTED);
            eventDtoWithViews.setConfirmedRequests(Long.parseLong(String.valueOf(confirmed.size())));
            foundList.add(eventDtoWithViews);
        }
        return foundList;
    }

    /**
     * Метод для получения информации о событии по его id, добавленном текущим пользователем (в ответе выводится
     * количество просмотров по этому событию)
     */
    @Override
    public EventDtoWithViews getByIdForUser(Long userId, Long eventId) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            if (!eventRepository.findById(eventId).orElseThrow().getInitiator().getId().equals(userId)) {
                throw new UserNotAllowedToViewEventException("Event doesn't belong to user with id = " + userId);
            } else {
                long hits = getHits(eventId);
                EventDtoWithViews eventDtoWithView = EventMapper
                        .toEventDtoWithViews(eventRepository.findById(eventId).get());
                eventDtoWithView.setViews(hits);
                eventDtoWithView.setCategory(CategoryMapper.toCategoryDto(categoryRepository
                        .findById(eventDtoWithView.getCategory().getId()).orElseThrow()));
                List<Request> confirmed = requestRepository.findByEventIdAndRequestState(eventId,
                        RequestState.ACCEPTED);
                eventDtoWithView.setConfirmedRequests(Long.parseLong(String.valueOf(confirmed.size())));
                return eventDtoWithView;
            }
        }
    }

    /**
     * Метод для отмены события, добавленного текущим пользователем. Отменить можно событие только в
     * состоянии ожидания модерации
     */
    @Override
    public EventDto rejectByUser(Long userId, Long eventId) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            Event event = eventRepository.findById(eventId).get();
            if (!event.getInitiator().getId().equals(userId)) {
                throw new UserNotAllowedToViewEventException("Event doesn't belong to user with id = " + userId);
            } else {
                if (event.getEventState() != EventState.PENDING) {
                    throw new EventCantBeModifiedException("Event with id = " + userId + " can't be modified due "
                            + "to its status");
                } else {
                    event.setEventState(EventState.REJECTED);
                    eventRepository.save(event);
                    return EventMapper.toEventDto(event);
                }
            }
        }
    }

    /**
     * Метод для получения списка всех событий админом с учетом условий фильтрации
     */
    @Override
    public List<EventDtoWithViews> getAllForAdmin(List<Integer> users, List<String> states, List<Integer> categories,
                                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from,
                                                  Integer size) {
        List<EventDtoWithViews> eventListWithView = new ArrayList<>();
        List<Event> events = eventRepository.findAllForAdmin(users, states, categories, rangeStart, rangeEnd,
                PageRequest.of(from / size, size));
        if (events != null) {
            for (Event event : events) {
                long hits = getHits(event.getId());
                EventDtoWithViews eventWithViews = EventMapper.toEventDtoWithViews(event);
                eventWithViews.setViews(hits);
                eventWithViews.setCategory(CategoryMapper.toCategoryDto(categoryRepository
                        .findById(eventWithViews.getCategory().getId()).orElseThrow()));
                List<Request> confirmed = requestRepository.findByEventIdAndRequestState(event.getId(),
                        RequestState.ACCEPTED);
                eventWithViews.setConfirmedRequests(Long.parseLong(String.valueOf(confirmed.size())));
                eventListWithView.add(eventWithViews);
            }
            return eventListWithView;
        } else {
            return null;
        }
    }

    /**
     * Метод для публикации события админом. Дата начала события должна быть не раньше, чем за час от даты
     * публикации. Событие должно быть в состоянии ожидания модерации
     */
    @Override
    public EventDto publishByAdmin(Long eventId) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            Event event = eventRepository.findById(eventId).get();
            if (event.getEventState() == EventState.PENDING
                    && event.getEventDate().isAfter(LocalDateTime.now().plusHours(1))) {
                event.setEventState(EventState.PUBLISHED);
                return EventMapper.toEventDto(eventRepository.save(event));
            } else {
                throw new EventCantBeModifiedException("Event can't be published");
            }
        }
    }


    /**
     * Метод для отклонения события админом. Событие не должно быть уже опубликовано
     */
    @Override
    public EventDto rejectByAdmin(Long eventId) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            Event event = eventRepository.findById(eventId).get();
            if (event.getEventState() != EventState.PUBLISHED) {
                event.setEventState(EventState.REJECTED);
                return EventMapper.toEventDto(eventRepository.save(event));
            } else {
                throw new EventCantBeModifiedException("Event can't be rejected");
            }
        }
    }

    /**
     * Метод для редактирования события админом
     */
    @Override
    public EventDto updateByAdmin(Long eventId, NewEventDto newEvent) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            Event event = eventRepository.findById(eventId).get();
            if (newEvent.getParticipantLimit() != 0
                    && newEvent.getParticipantLimit()
                    > requestRepository.findByEventIdAndRequestStateIsNotIn(eventId,
                    List.of(RequestState.CANCELLED, RequestState.REJECTED)).size()) {
                throw new EventCantBeModifiedException("Participant limit can't be more than "
                        + "amount of requests in statuses PENDING, ACCEPTED");
            } else {
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
        }
    }

    /**
     * Метод для получения списка событий с параметрами фильтрации неавторизованным пользователем
     */
    @Override
    public List<EventDtoWithViews> getAllPublic(String text, List<Integer> categories, Boolean paid,
                                                LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                Boolean onlyAvailable, String sort, Integer from, Integer size,
                                                String ip, String uri) {
        List<Event> foundList;
        eventClient.create(new NewStatsDto("ewm", uri, ip));
        if (rangeStart == null && rangeEnd == null) {
            LocalDateTime date = LocalDateTime.now();
            foundList = eventRepository.getAllPublicAfterNow(text, categories, paid, date,
                     EventState.PUBLISHED.name());
        } else {
            foundList = eventRepository.getAllPublicInRange(text, categories, paid, rangeStart, rangeEnd,
                    EventState.PUBLISHED.name());
        }
        if (foundList != null) {
            if (onlyAvailable) {
                foundList.removeIf(event
                        -> eventRepository.findById(event.getId()).orElseThrow().getParticipantLimit() != 0
                        && (eventRepository.findById(event.getId()).orElseThrow().getParticipantLimit()
                        .equals(Long.parseLong(String.valueOf(requestRepository
                                .findByEventIdAndRequestState(event.getId(), RequestState.ACCEPTED).size())))));
            }
            List<EventDtoWithViews> eventListWithView = new ArrayList<>();
            for (Event eventInList : foundList) {
                long hits = getHits(eventInList.getId());
                EventDtoWithViews eventWithViews = EventMapper.toEventDtoWithViews(eventInList);
                eventWithViews.setViews(hits);
                eventWithViews.setCategory(CategoryMapper.toCategoryDto(categoryRepository
                        .findById(eventWithViews.getCategory().getId()).orElseThrow()));
                List<Request> confirmed = requestRepository.findByEventIdAndRequestState(eventInList.getId(),
                        RequestState.ACCEPTED);
                eventWithViews.setConfirmedRequests(Long.parseLong(String.valueOf(confirmed.size())));
                eventListWithView.add(eventWithViews);
            }
            if (sort.equals("EVENT_DATE")) {
                eventListWithView.sort(new EventDateComparator());
            } else if (sort.equals("VIEWS")) {
                eventListWithView.sort(new EventViewsComparator());
            }
            int fromIndex = from * size;
            if (eventListWithView.size() <= fromIndex) {
                return Collections.emptyList();
            }
            return eventListWithView.subList(fromIndex, Math.min(fromIndex + size, eventListWithView.size()));
        } else {
            return null;
        }
    }

    /**
     * Метод для получения информации о событии по его id неавторизованным пользователем
     */
    @Override
    public EventDtoWithViews getByIdPublic(Long eventId, String ip, String uri) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            Event event = eventRepository.findById(eventId).orElseThrow();
            if (event.getEventState() == EventState.PUBLISHED) {
                eventClient.create(new NewStatsDto("ewm", uri, ip));
                EventDtoWithViews eventWithViews = EventMapper.toEventDtoWithViews(event);
                long hits = getHits(eventId);
                eventWithViews.setViews(hits);
                eventWithViews.setCategory(CategoryMapper.toCategoryDto(categoryRepository
                        .findById(eventWithViews.getCategory().getId()).orElseThrow()));
                List<Request> confirmed = requestRepository.findByEventIdAndRequestState(eventId, RequestState.ACCEPTED);
                eventWithViews.setConfirmedRequests(Long.parseLong(String.valueOf(confirmed.size())));
                return eventWithViews;
            } else {
                throw new UserNotAllowedToViewEventException("Event can't be viewed due to its status");
            }
        }
    }

    /**
     * Метод для получения количества просмотров по событию
     */
    private long getHits(Long eventId) {
        LocalDateTime minDate = eventRepository.findMinDate().getEventDate();
        LocalDateTime maxDate = eventRepository.findMaxDate().getEventDate();
        String uriToFind = "/events/" + eventId;
        ResponseEntity<Object> list = eventClient.get(minDate, maxDate, uriToFind, false);
        @SuppressWarnings({"unchecked"}) List<Map<String, Object>> listStat = (List<Map<String, Object>>) list.getBody();
        long hits = 0L;
        if (listStat != null && listStat.size() != 0) {
            Map<String, Object> el = listStat.get(0);
            hits = Long.parseLong(String.valueOf(el.get("hits")));
        }
        return hits;
    }
}
