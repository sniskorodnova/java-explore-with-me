package ru.practicum.ewm.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.compilation.CompilationDto;
import ru.practicum.ewm.model.compilation.NewCompilationDto;
import ru.practicum.ewm.service.compilation.CompilationService;

/**
 * Класс-контроллер для работы с администраторскими запросами для подборок событий
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class AdminController {
    private final CompilationService compilationService;

    /**
     * Метод для создания подборки событий. Метод доступен только админу
     */
    @PostMapping
    public CompilationDto create(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                 @RequestBody NewCompilationDto newCompilation) {
        log.info("Входящий запрос на создание подборки событий " + newCompilation);
        return compilationService.create(newCompilation);
    }

    /**
     * Метод для удаления подборки событий по ее id. Метод доступен только админу
     */
    @DeleteMapping("/{compId}")
    public void deleteById(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader, @PathVariable Long compId) {
        log.info("Входящий запрос на удаление подборки событий с id = " + compId);
        compilationService.deleteById(compId);
    }

    /**
     * Метод для удаления события из подборки событий по его id. Метод доступен только админу
     */
    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventByIdFromCompilation(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                               @PathVariable Long compId, @PathVariable Long eventId) {
        log.info("Входящий запрос на удаление события с id = " + eventId + " из подборки событий с id = " + compId);
        compilationService.deleteEventByIdFromCompilation(compId, eventId);
    }

    /**
     * Метод для добавления события в подборку событий по его id. Метод доступен только админу
     */
    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventByIdToCompilation(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                          @PathVariable Long compId, @PathVariable Long eventId) {
        log.info("Входящий запрос на добавление события с id = " + eventId + " в подборку событий с id = " + compId);
        compilationService.addEventByIdToCompilation(compId, eventId);
    }

    /**
     * Метод для открепления подборки событий на главной странице. Метод доступен только админу
     */
    @DeleteMapping("/{compId}/pin")
    public void unpin(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader, @PathVariable Long compId) {
        log.info("Входящий запрос на открепление с главной страницы подборки событий с id = " + compId);
        compilationService.unpin(compId);
    }

    /**
     * Метод для закрепления подборки событий. Метод доступен только админу
     */
    @PatchMapping("/{compId}/pin")
    public void pin(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader, @PathVariable Long compId) {
        log.info("Входящий запрос на закрепление на главной странице подборки событий с id = " + compId);
        compilationService.pin(compId);
    }
}
