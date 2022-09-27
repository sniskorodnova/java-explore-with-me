package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.users.User;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс, описывающий dto события с просмотрами и созданными запросами для отдачи клиенту по api
 */
@Data
@AllArgsConstructor
public class EventDtoWithViews {
    @NotNull
    private Long id;
    @NotEmpty
    @NotBlank
    private String title;
    @NotEmpty
    @NotBlank
    private String annotation;
    @NotEmpty
    @NotBlank
    private String description;
    @NotNull
    private Location location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @NotNull
    @Future
    private LocalDateTime eventDate;
    @NotNull
    private CategoryDto category;
    @NotNull
    private Boolean paid;
    @PositiveOrZero
    @NotNull
    private Long participantLimit;
    @NotNull
    private Boolean requestModeration;
    @NotNull
    private User initiator;
    @NotNull
    private EventState status;
    @NotNull
    private Long views;
    @NotNull
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @NotNull
    private LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime publishedOn;

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
