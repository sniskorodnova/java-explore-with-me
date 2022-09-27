package ru.practicum.ewm.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.event.EventDto;
import ru.practicum.ewm.model.event.EventDtoWithViews;
import ru.practicum.ewm.model.event.NewEventDto;
import ru.practicum.ewm.model.event.NewShortEventDto;
import ru.practicum.ewm.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
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
     * Метод для получения информации о событиях админом с учетом условий фильтрации
     */
    @GetMapping("/admin/events")
    public List<EventDtoWithViews> getAllForAdmin(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                                  @RequestParam List<Integer> users, @RequestParam List<String> states,
                                                  @RequestParam List<Integer> categories,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                  LocalDateTime rangeStart,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                  LocalDateTime rangeEnd, @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        log.info("Входящий запрос на получение информации о всех событиях для админа: users = " + users
                + ", states = " + states + ", categories = " + categories + ", rangeStart = " + rangeStart
                + ", rangeEnd = " + rangeEnd);
        return eventService.getAllForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Метод для редактирования события админом
     */
    @PutMapping("/admin/events/{eventId}")
    public EventDto updateByAdmin(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                  @PathVariable Long eventId, @RequestBody NewEventDto newEvent) {
        log.info("Входящий запрос на редактирования события с id = " + eventId + " админом. Новые данные: "
                + newEvent.toString());
        return eventService.updateByAdmin(eventId, newEvent);
    }

    /**
     * Метод для публикации события админом
     */
    @PatchMapping("/admin/events/{eventId}/publish")
    public EventDto publishByAdmin(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                   @PathVariable Long eventId) {
        log.info("Входящий запрос на публикацию события с id = " + eventId + " админом");
        return eventService.publishByAdmin(eventId);
    }

    /**
     * Метод для отклонения события админом
     */
    @PatchMapping("/admin/events/{eventId}/reject")
    public EventDto rejectByAdmin(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                  @PathVariable Long eventId) {
        log.info("Входящий запрос на отклонение события с id = " + eventId + " админом");
        return eventService.rejectByAdmin(eventId);
    }


}
