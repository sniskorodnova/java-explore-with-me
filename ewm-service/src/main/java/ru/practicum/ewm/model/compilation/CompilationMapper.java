package ru.practicum.ewm.model.compilation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventDtoWithViews;
import ru.practicum.ewm.model.event.EventMapper;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс, описывающий маппинг сущности подборки событий в dto и обратно
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        Set<EventDtoWithViews> eventsWithViews = new HashSet<>();
        for (Event event : compilation.getEvents()) {
            EventDtoWithViews eventView = EventMapper.toEventDtoWithViews(event);
            eventsWithViews.add(eventView);
        }
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                eventsWithViews
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
