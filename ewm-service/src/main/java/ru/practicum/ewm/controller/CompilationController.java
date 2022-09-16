package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exception.NoHeaderException;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.NewCompilationDto;
import ru.practicum.ewm.service.compilation.CompilationService;

/**
 * Класс-контроллер для работы с подборками событий
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class CompilationController {
    private final CompilationService compilationService;

    /**
     * Метод для создания подборки событий
     */
    @PostMapping("/admin/compilations")
    public CompilationDto create(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                 @RequestBody NewCompilationDto newCompilation) {
        log.info("Входящий запрос на создание подборки событий " + newCompilation);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return compilationService.create(newCompilation);
        }
    }
}
