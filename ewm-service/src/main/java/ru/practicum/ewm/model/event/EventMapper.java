package ru.practicum.ewm.model.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.location.Location;

/**
 * Класс, описывающий маппинг сущности события в dto и обратно
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static EventDto toEventDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                event.getLocation(),
                event.getEventDate(),
                new CategoryDto(event.getCategoryId(), null),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getRequestModeration(),
                event.getInitiator(),
                event.getEventState(),
                event.getCreatedOn(),
                event.getPublishedOn()
        );
    }

    public static EventDtoWithViews toEventDtoWithViews(Event event) {
        return new EventDtoWithViews(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                event.getLocation(),
                event.getEventDate(),
                new CategoryDto(event.getCategoryId(), null),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getRequestModeration(),
                event.getInitiator(),
                event.getEventState(),
                null,
                null,
                event.getCreatedOn(),
                event.getPublishedOn()
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
                null,
                null,
                null
        );
    }
}
