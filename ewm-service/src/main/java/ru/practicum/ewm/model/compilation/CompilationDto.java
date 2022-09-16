package ru.practicum.ewm.model.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.event.Event;

import java.util.Set;

@Data
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    Set<Event> events;
}
