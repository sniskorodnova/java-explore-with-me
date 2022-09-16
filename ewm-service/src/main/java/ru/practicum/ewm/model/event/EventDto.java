package ru.practicum.ewm.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
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
    private LocalDateTime eventDate;
    private Long categoryId;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private User initiator;
    private State status;
}
