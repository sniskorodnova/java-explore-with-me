package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.event.EventDtoWithViews;
import ru.practicum.ewm.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс-контроллер для работы с публичными запросами для событий
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final EventService eventService;

    /**
     * Метод для получения событий с параметрами фильтрации
     */
    @GetMapping
    public List<EventDtoWithViews> getAllPublic(@RequestParam String text, @RequestParam List<Integer> categories,
                                                @RequestParam Boolean paid, @RequestParam(required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                LocalDateTime rangeStart,
                                                @RequestParam(required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                LocalDateTime rangeEnd,
                                                @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                @RequestParam String sort,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size,
                                                HttpServletRequest request) {
        log.info("Входящий запрос на получение всех событий неавторизованным пользователем с параметрами "
                + "фильтрации: text = " + text + ", categories = " + categories + ", paid = " + paid + ", rangeStart = "
                + rangeStart + ", rangeEnd = " + rangeEnd + ", onlyAvailable = " + onlyAvailable + ", sort = "
                + sort + ", from = " + from + ", size = " + size);
        return eventService.getAllPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size,
                request.getRemoteAddr(), request.getRequestURI());
    }

    /**
     * Метод для получения информации о событии по его id
     */
    @GetMapping("/{eventId}")
    public EventDtoWithViews getByIdPublic(@PathVariable Long eventId, HttpServletRequest request) {
        log.info("Входящий запрос на получение информации о событии с id = " + eventId
                + " неавторизованным пользователем");
        return eventService.getByIdPublic(eventId, request.getRemoteAddr(), request.getRequestURI());
    }
}
