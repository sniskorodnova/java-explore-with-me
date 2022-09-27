package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.users.User;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Класс, описывающий dto события для отдачи клиенту по api
 */
@Data
@AllArgsConstructor
public class EventDto {
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
    @Future
    private LocalDateTime eventDate;
    @NotNull
    private CategoryDto category;
    @NotNull
    private Boolean paid;
    @NotNull
    @PositiveOrZero
    private Long participantLimit;
    @NotNull
    private Boolean requestModeration;
    @NotNull
    private User initiator;
    @NotNull
    private EventState status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @NotNull
    private LocalDateTime createdOn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime publishedOn;
}
