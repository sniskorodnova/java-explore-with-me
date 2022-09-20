package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.location.Location;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String annotation;
    @NotEmpty
    private String description;
    @NotNull
    private Location location;
    @NotNull
    private LocalDateTime eventDate;
    @JsonProperty("category")
    @NotNull
    private Long categoryId;
    private Boolean paid = false;
    private Long participantLimit = 0L;
    private Boolean requestModeration = true;
}
