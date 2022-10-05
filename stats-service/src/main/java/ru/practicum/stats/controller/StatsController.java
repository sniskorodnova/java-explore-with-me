package ru.practicum.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.model.NewStatsDto;
import ru.practicum.stats.model.StatsDto;
import ru.practicum.stats.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-контроллер для работы со статистикой
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class StatsController {
    private final StatsService statsService;

    /**
     * Метод для создания записи об обращении к эндпойнту
     */
    @PostMapping("/hit")
    public void create(@RequestBody NewStatsDto newStats) {
        log.info("Входящий запрос на создание записи в статистику: " + newStats.toString());
        statsService.create(newStats);
    }

    /**
     * Метод для получения количества просмотров по событию
     */
    @GetMapping("/stats")
    public List<StatsDto> create(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
                                 @RequestParam List<String> uris,
                                 @RequestParam (defaultValue = "false") Boolean unique) {
        log.info("Входящий запрос на получение статистики по параметрам: start = " + start + ", end = "
                + end + ", uris = " + uris + ", unique = " + unique);
        return statsService.get(start, end, uris, unique);
    }
}
