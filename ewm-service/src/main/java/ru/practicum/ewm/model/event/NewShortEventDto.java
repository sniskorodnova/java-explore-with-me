package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewShortEventDto {
    @JsonProperty("eventId")
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String annotation;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDateTime eventDate;
    @JsonProperty("category")
    @NotNull
    private Long categoryId;
    private Boolean paid = false;
    private Long participantLimit = 0L;
}
