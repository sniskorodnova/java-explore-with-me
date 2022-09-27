package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.event.EventDto;
import ru.practicum.ewm.model.event.EventDtoWithViews;
import ru.practicum.ewm.model.event.NewEventDto;
import ru.practicum.ewm.model.event.NewShortEventDto;
import ru.practicum.ewm.service.event.EventService;

import java.util.List;

/**
 * Класс-контроллер для работы с приватными запросами для событий
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    /**
     * Метод для создания события. В хедере передается авторизация для залогиненного пользователя
     */
    @PostMapping
    public EventDto create(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                           @PathVariable Long userId, @RequestBody @Validated NewEventDto newEvent) {
        log.info("Входящий запрос на создание события: " + newEvent.toString());
        return eventService.create(newEvent, userId);
    }

    /**
     * Метод для редактирования события. В хедере передается авторизация для залогиненного пользователя
     */
    @PatchMapping
    public EventDto updateByUser(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                 @PathVariable Long userId, @RequestBody @Validated NewShortEventDto newShortEvent) {
        log.info("Входящий запрос на редактирование события: " + newShortEvent.toString());
        return eventService.updateByUser(newShortEvent, userId);
    }

    /**
     * Метод для получения всех событий, добавленных текущим пользователем с пагинацией
     */
    @GetMapping
    public List<EventDtoWithViews> getAllForUser(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                                 @PathVariable Long userId, @RequestParam(defaultValue = "0")
                                                 Integer from, @RequestParam(defaultValue = "10") Integer size) {
        log.info("Входящий запрос на получение списка всех событий для пользователя с id = " + userId);
        return eventService.getAllForUser(userId, from, size);
    }

    /**
     * Метод для получения информации о событии по id, добавленном текущим пользователем
     */
    @GetMapping("/{eventId}")
    public EventDtoWithViews getByIdForUser(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                            @PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Входящий запрос на получение информации о событии с id = " + eventId + ", добавленном пользователем"
                + " с id = " + userId);
        return eventService.getByIdForUser(userId, eventId);
    }

    /**
     * Метод для отмены события, добавленного текущим пользователем
     */
    @PatchMapping("/{eventId}")
    public EventDto rejectByUser(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                 @PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Входящий запрос на отмену события с id = " + eventId + ", добавленного пользователем"
                + " с id = " + userId);
        return eventService.rejectByUser(userId, eventId);
    }
}
