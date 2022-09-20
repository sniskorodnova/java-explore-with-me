package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.users.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventDto {
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
}
