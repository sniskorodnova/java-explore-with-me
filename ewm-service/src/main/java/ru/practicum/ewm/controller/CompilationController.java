package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exception.NoHeaderException;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.NewCompilationDto;
import ru.practicum.ewm.service.compilation.CompilationService;

import java.util.List;

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
     * Метод для создания подборки событий. Метод доступен только админу
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

    /**
     * Метод для удаления подборки событий по ее id. Метод доступен только админу
     */
    @DeleteMapping("/admin/compilations/{compId}")
    public void deleteById(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                           @PathVariable Long compId) {
        log.info("Входящий запрос на удаление подборки событий с id = " + compId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            compilationService.deleteById(compId);
        }
    }

    /**
     * Метод для удаления события из подборки событий по его id. Метод доступен только админу
     */
    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    public void deleteEventByIdFromCompilation(@RequestHeader(value = "X-Sharer-User-Id", required = false)
                                               Long userHeader, @PathVariable Long compId,
                                               @PathVariable Long eventId) {
        log.info("Входящий запрос на удаление события с id = " + eventId + " из подборки событий с id = " + compId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            compilationService.deleteEventByIdFromCompilation(compId, eventId);
        }
    }

    /**
     * Метод для добавления события в подборку событий по его id. Метод доступен только админу
     */
    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    public void addEventByIdToCompilation(@RequestHeader(value = "X-Sharer-User-Id", required = false)
                                          Long userHeader, @PathVariable Long compId,
                                          @PathVariable Long eventId) {
        log.info("Входящий запрос на добавление события с id = " + eventId + " в подборку событий с id = " + compId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            compilationService.addEventByIdToCompilation(compId, eventId);
        }
    }

    /**
     * Метод для открепления подборки событий на главной странице. Метод доступен только админу
     */
    @DeleteMapping("/admin/compilations/{compId}/pin")
    public void unpin(@RequestHeader(value = "X-Sharer-User-Id", required = false)
                      Long userHeader, @PathVariable Long compId) {
        log.info("Входящий запрос на открепление с главной страницы подборки событий с id = " + compId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            compilationService.unpin(compId);
        }
    }

    /**
     * Метод для закрепления подборки событий. Метод доступен только админу
     */
    @PatchMapping("/admin/compilations/{compId}/pin")
    public void pin(@RequestHeader(value = "X-Sharer-User-Id", required = false)
                      Long userHeader, @PathVariable Long compId) {
        log.info("Входящий запрос на закрепление на главной странице подборки событий с id = " + compId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            compilationService.pin(compId);
        }
    }

    /**
     * Метод для получения информации по подборке событий на главной странице. Метод не требует авторизации
     */
    @GetMapping("/compilations/{compId}")
    public CompilationDto getById(@PathVariable Long compId) {
        log.info("Входящий запрос на получение информации по подборке событий с id = " + compId);
        return compilationService.getById(compId);
    }

    /**
     * Метод для получения подборок событий по заданным фильтрам. Метод не требует авторизации
     */
    @GetMapping("/compilations")
    public List<CompilationDto> getAll(@RequestParam Boolean pinned, @RequestParam (defaultValue = "0") Integer from,
                                       @RequestParam (defaultValue = "10") Integer size) {
        log.info("Входящий запрос на получение подборок по фильтрам pinned = " + pinned + ", from = "
                + from + ", size = " + size);
        return compilationService.getAll(pinned, from, size);
    }
}
