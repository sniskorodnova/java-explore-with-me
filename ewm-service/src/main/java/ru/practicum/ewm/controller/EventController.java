package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exception.NoHeaderException;
import ru.practicum.ewm.model.event.EventDto;
import ru.practicum.ewm.model.event.NewEventDto;
import ru.practicum.ewm.service.event.EventService;

/**
 * Класс-контроллер для работы с событиями
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class EventController {
    private final EventService eventService;

    /**
     * Метод для создания события
     */
    @PostMapping("/{userId}/events")
    public EventDto create(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                           @PathVariable Long userId, @RequestBody NewEventDto newEvent) {
        log.info("Входящий запрос на создание события: " + newEvent.toString());
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.create(newEvent);
        }
    }
}
