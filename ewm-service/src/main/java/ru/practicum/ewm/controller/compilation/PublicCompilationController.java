package ru.practicum.ewm.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.service.compilation.CompilationService;

import java.util.List;

/**
 * Класс-контроллер для работы с публичными запросами для подборок событий
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class PublicCompilationController {
    private final CompilationService compilationService;

    /**
     * Метод для получения информации по подборке событий на главной странице. Метод не требует авторизации
     */
    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.info("Входящий запрос на получение информации по подборке событий с id = " + compId);
        return compilationService.getById(compId);
    }

    /**
     * Метод для получения подборок событий по заданным фильтрам. Метод не требует авторизации
     */
    @GetMapping
    public List<CompilationDto> getAll(@RequestParam Boolean pinned, @RequestParam (defaultValue = "0") Integer from,
                                       @RequestParam (defaultValue = "10") Integer size) {
        log.info("Входящий запрос на получение подборок по фильтрам pinned = " + pinned + ", from = "
                + from + ", size = " + size);
        return compilationService.getAll(pinned, from, size);
    }
}
