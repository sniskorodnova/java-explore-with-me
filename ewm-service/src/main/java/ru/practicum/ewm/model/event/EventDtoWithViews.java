package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.users.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
public class EventDtoWithViews {
    private Long id;
    private String title;
    private String annotation;
    private String description;
    private Location location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime eventDate;
    private CategoryDto category;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private User initiator;
    private EventState status;
    private Long views;
    private Long confirmedRequests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDtoWithViews that = (EventDtoWithViews) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title)
                && Objects.equals(annotation, that.annotation)
                && Objects.equals(description, that.description)
                && Objects.equals(location, that.location)
                && Objects.equals(eventDate, that.eventDate)
                && Objects.equals(category, that.category)
                && Objects.equals(paid, that.paid)
                && Objects.equals(participantLimit, that.participantLimit)
                && Objects.equals(requestModeration, that.requestModeration)
                && Objects.equals(initiator, that.initiator)
                && status == that.status
                && Objects.equals(views, that.views)
                && Objects.equals(confirmedRequests, that.confirmedRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, annotation, description, location, eventDate, category, paid,
                participantLimit, requestModeration, initiator, status, views, confirmedRequests);
    }
}
