package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exception.NoHeaderException;
import ru.practicum.ewm.model.event.EventDto;
import ru.practicum.ewm.model.event.NewEventDto;
import ru.practicum.ewm.model.event.NewShortEventDto;
import ru.practicum.ewm.service.event.EventService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-контроллер для работы с событиями
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class EventController {
    private final EventService eventService;

    /**
     * Метод для создания события
     */
    @PostMapping("/users/{userId}/events")
    public EventDto create(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                           @PathVariable Long userId, @RequestBody NewEventDto newEvent) {
        log.info("Входящий запрос на создание события: " + newEvent.toString());
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.create(newEvent, userId);
        }
    }

    /**
     * Метод для редактирования события
     */
    @PatchMapping("/users/{userId}/events")
    public EventDto updateByUser(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                           @PathVariable Long userId, @RequestBody NewShortEventDto newShortEvent) {
        log.info("Входящий запрос на редактирование события: " + newShortEvent.toString());
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.updateByUser(newShortEvent, userId);
        }
    }

    /**
     * Метод для получения всех событий, добавленный текущим пользователем
     */
    @GetMapping("/users/{userId}/events")
    public List<EventDto> getAllForUser(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                        @PathVariable Long userId, @RequestParam (defaultValue = "0") Integer from,
                                        @RequestParam (defaultValue = "10") Integer size) {
        log.info("Входящий запрос на получение списка всех событий для пользователя с id = : " + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.getAllForUser(userId, from, size);
        }
    }

    /**
     * Метод для получения информации о событии, добавленном текущим пользователем
     */
    @GetMapping("/users/{userId}/events/{eventId}")
    public EventDto getById(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                            @PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Входящий запрос на получение информации о событии с id = " + eventId + ", добавленном пользователем"
                + " с id = " + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.getById(userId, eventId);
        }
    }

    /**
     * Метод для отмены события, добавленного текущим пользователем
     */
    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventDto rejectByUser(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                 @PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Входящий запрос на отмену события с id = " + eventId + ", добавленного пользователем"
                + " с id = " + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.rejectByUser(userId, eventId);
        }
    }

    /**
     * Метод для получения информации о событиях админом
     */
    @GetMapping("/admin/events")
    public List<EventDto> getAllForAdmin(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                         @RequestParam List<Integer> users, @RequestParam List<String> states,
                                         @RequestParam List<Integer> categories,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                             LocalDateTime rangeStart,
                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                             LocalDateTime rangeEnd, @RequestParam (defaultValue = "0") Integer from,
                                         @RequestParam(defaultValue = "10") Integer size) {
        //дописать лог
        log.info("Входящий запрос на получение информации о всех событиях для админа");
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.getAllForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
        }
    }

    /**
     * Метод для редактирования события админом
     */
    @PutMapping("/admin/events/{eventId}")
    public EventDto updateByAdmin(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                  @PathVariable Long eventId, @RequestBody NewEventDto newEvent) {
        log.info("Входящий запрос на редактирования события с id = " + eventId + " админом");
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.updateByAdmin(eventId, newEvent);
        }
    }

    /**
     * Метод для публикации события админом
     */
    @PatchMapping("/admin/events/{eventId}/publish")
    public EventDto publishByAdmin(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                         @PathVariable Long eventId) {
        log.info("Входящий запрос на публикацию события с id = " + eventId + " админом");
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.publishByAdmin(eventId);
        }
    }

    /**
     * Метод для отклонения события админом
     */
    @PatchMapping("/admin/events/{eventId}/reject")
    public EventDto rejectByAdmin(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                   @PathVariable Long eventId) {
        log.info("Входящий запрос на отклонение события с id = " + eventId + " админом");
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return eventService.rejectByAdmin(eventId);
        }
    }

    /**
     * Метод для получения событий неавторизованным пользователем
     */
    @GetMapping("/events")
    public List<EventDto> getAllPublic(@RequestParam String text, @RequestParam Integer[] categories,
                                       @RequestParam Boolean paid,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                           LocalDateTime rangeStart,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                           LocalDateTime rangeEnd,
                                       @RequestParam Boolean onlyAvailable, @RequestParam String sort,
                                       @RequestParam (defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size
                                       ) {
        log.info("Входящий запрос на получение всех событий неавторизованным пользователем");
        return eventService.getAllPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    /**
     * Метод для получения информации о событии неавторизованным пользователем
     */
    @GetMapping("/events/{eventId}")
    public EventDto getByIdPublic(@PathVariable Long eventId) {
        log.info("Входящий запрос на получение информации о событии с id = " + eventId
                + " неавторизованным пользователем");
        return eventService.getByIdPublic(eventId);
    }
}
