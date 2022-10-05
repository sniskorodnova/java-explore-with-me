package ru.practicum.ewm.service.compilation;

import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.NewCompilationDto;

import java.util.List;

/**
 * Интерфейс, описывающий логику для работы сервиса подборок событий
 */
public interface CompilationService {
    CompilationDto create(NewCompilationDto newCompilation);

    void deleteById(Long compId);

    void deleteEventByIdFromCompilation(Long compId, Long eventId);

    void addEventByIdToCompilation(Long compId, Long eventId);

    void unpin(Long compId);

    void pin(Long compId);

    CompilationDto getById(Long compId);

    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);
}
