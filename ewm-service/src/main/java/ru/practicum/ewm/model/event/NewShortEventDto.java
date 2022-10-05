package ru.practicum.ewm.model.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс, описывающий dto события для получения от клиента по api при редактировании события
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewShortEventDto {
    @JsonProperty("eventId")
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
    @Future
    private LocalDateTime eventDate;
    @JsonProperty("category")
    @NotNull
    private Long categoryId;
    private Boolean paid = false;
    private Long participantLimit = 0L;
}
