package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewShortEventDto {
    @JsonProperty("eventId")
    private Long id;
    private String title;
    private String annotation;
    private String description;
    private LocalDateTime eventDate;
    @JsonProperty("category")
    private Long categoryId;
    private Boolean paid;
    private Long participantLimit;
}
