package ru.practicum.ewm.service.compilation;

import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.NewCompilationDto;

/**
 * Интерфейс, описывающий логику для работы сервиса подборок событий
 */
public interface CompilationService {
    CompilationDto create(NewCompilationDto newCompilation);
}
