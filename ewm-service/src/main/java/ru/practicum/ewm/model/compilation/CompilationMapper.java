package ru.practicum.ewm.model.compilation;

import ru.practicum.ewm.model.event.Event;

import java.util.HashSet;
import java.util.Set;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                compilation.getEvents()
        );
    }

    public static Compilation newToCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> events = new HashSet<>();
        Set<Long> eventsId = newCompilationDto.getEventsId();
        for (Long id : eventsId) {
            Event event = new Event();
            event.setId(id);
            events.add(event);
        }
        return new Compilation(
                null,
                newCompilationDto.getTitle(),
                newCompilationDto.getPinned(),
                events
        );
    }
}
