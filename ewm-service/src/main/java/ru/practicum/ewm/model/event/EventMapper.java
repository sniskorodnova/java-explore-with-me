package ru.practicum.ewm.model.event;

import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.NewCategoryDto;
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
                event.getStatus()
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
                null
        );
    }

 /*   public static Event toEvent(EventDto eventDto) {
        return new Event(
                categoryDto.getId(),
                categoryDto.getName()
        );
    }*/
}
