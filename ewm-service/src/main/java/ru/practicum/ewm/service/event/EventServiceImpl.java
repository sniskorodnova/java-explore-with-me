package ru.practicum.ewm.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.event.*;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.storage.event.EventRepository;
import ru.practicum.ewm.storage.location.LocationRepository;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, LocationRepository locationRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public EventDto create(NewEventDto newEvent) {
        Event event = EventMapper.newToEvent(newEvent);
        event.setStatus(Status.PENDING);
        Location location = event.getLocation();
        locationRepository.save(location);
        return EventMapper.toEventDto(eventRepository.save(event));
    }
}
