package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.location.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    private String title;
    private String annotation;
    private String description;
    private Location location;
    private LocalDateTime eventDate;
    @JsonProperty("category")
    private Long categoryId;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
}
