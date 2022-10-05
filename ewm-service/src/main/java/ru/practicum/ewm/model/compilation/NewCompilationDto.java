package ru.practicum.ewm.model.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Класс, описывающий dto подборки событий для получения от клиента по api
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    @NotEmpty
    @NotBlank
    private String title;
    private Boolean pinned = false;
    @JsonProperty("events")
    @NotNull
    Set<Long> eventsId;
}
