package ru.practicum.ewm.model.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.event.EventDtoWithViews;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Класс, описывающий dto подборки событий для отдачи клиенту по api
 */
@Data
@AllArgsConstructor
public class CompilationDto {
    @NotNull
    private Long id;
    @NotEmpty
    @NotBlank
    private String title;
    private Boolean pinned;
    @NotNull
    Set<EventDtoWithViews> events;
}
