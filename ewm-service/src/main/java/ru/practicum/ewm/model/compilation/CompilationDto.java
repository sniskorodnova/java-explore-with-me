package ru.practicum.ewm.model.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.event.EventDtoWithViews;

import java.util.Set;

@Data
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    Set<EventDtoWithViews> events;
}
