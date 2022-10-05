package ru.practicum.ewm.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.request.RequestDto;
import ru.practicum.ewm.service.request.RequestService;

import java.util.List;

/**
 * Класс-контроллер для работы с приватными запросами для заявок на участие в событиях
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}")
public class PrivateRequestController {
    private final RequestService requestService;

    /**
     * Метод для создания заявки на участие в событии от текущего пользователя
     */
    @PostMapping("/requests")
    public RequestDto create(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                             @PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Входящий запрос на создание заявки на участие в событии с id = " + eventId
                + " от пользователя с id = " + userId);
        return requestService.create(userId, eventId);
    }

    /**
     * Метод для отмены текущим пользователем своей заявки на участие в событии
     */
    @PatchMapping("/requests/{requestId}/cancel")
    public RequestDto cancel(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                             @PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Входящий запрос на отмену заявки на участие с id = " + requestId
                + " от пользователя с id = " + userId);
        return requestService.cancel(userId, requestId);
    }

    /**
     * Метод для получения информации о заявках текущего пользователя на участие в чужих событиях
     */
    @GetMapping("/requests")
    public List<RequestDto> getAllForUser(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                          @PathVariable Long userId) {
        log.info("Входящий запрос на получение всех заявок пользователя с id = " + userId + " на участие "
                + "в чужих событиях");
        return requestService.getAllForUser(userId);
    }

    /**
     * Метод для получения информации о заявках на участие в событиях текущего пользователя
     */
    @GetMapping("/events/{eventId}/requests")
    public List<RequestDto> getAllForUserEvents(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                                @PathVariable Long userId, @PathVariable Long eventId) {
        log.info("Входящий запрос на получение всех заявок на участие в событиях пользователя с id = " + userId);
        return requestService.getAllForUserEvents(userId, eventId);
    }

    /**
     * Метод для подтверждения чужой заявки на участие в событии текущего пользователя
     */
    @PatchMapping("/events/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmRequest(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                     @PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long reqId) {
        log.info("Входящий запрос на подтверждение заявки с id = " + reqId + " на событие с id = " + eventId
                + " пользователем с id = " + userId);
        return requestService.confirmRequest(userId, eventId, reqId);
    }

    /**
     * Метод для отклонения чужой заявки на участие в событии текущего пользователя
     */
    @PatchMapping("/events/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectRequest(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                    @PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long reqId) {
        log.info("Входящий запрос на отклонение заявки с id = " + reqId + " на событие с id = " + eventId
                + " пользователем с id = " + userId);
        return requestService.rejectRequest(userId, eventId, reqId);
    }
}
