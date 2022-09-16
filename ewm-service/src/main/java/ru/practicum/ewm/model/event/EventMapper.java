package ru.practicum.ewm.model.event;

import ru.practicum.ewm.model.location.Location;

public class EventMapper {
    public static EventDto toEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                event.getLocation(),
                event.getEventDate(),
                event.getCategoryId(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getRequestModeration(),
                event.getInitiator(),
                event.getState()
        );
    }

    public static Event newToEvent(NewEventDto newEventDto) {
        return new Event(
                null,
                newEventDto.getTitle(),
                newEventDto.getAnnotation(),
                newEventDto.getDescription(),
                new Location(null, newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon()),
                newEventDto.getEventDate(),
                newEventDto.getCategoryId(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(),
                null,
                null
        );
    }
}
