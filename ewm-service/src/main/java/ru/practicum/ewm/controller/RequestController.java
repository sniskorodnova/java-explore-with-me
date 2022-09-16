package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exception.NoHeaderException;
import ru.practicum.ewm.model.request.RequestDto;
import ru.practicum.ewm.service.request.RequestService;

import java.util.List;

/**
 * Класс-контроллер для работы с заявками на участие в событиях
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class RequestController {
    private final RequestService requestService;

    /**
     * Метод для создания заявки на участие в событии
     */
    @PostMapping("/{userId}/requests")
    public RequestDto create(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                             @PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Входящий запрос на создание заявки на участие в событии с id = " + eventId
                + " от пользователя с id = " + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return requestService.create(userId, eventId);
        }
    }

    /**
     * Метод для создания заявки на участие в событии
     */
    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public void cancel(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                             @PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Входящий запрос на отмену заявки на участие с id = " + requestId
                + " от пользователя с id = " + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            requestService.cancel(userId, requestId);
        }
    }

    /**
     * Метод для получения информации о заявках текущего пользователя на участие в чужих событиях
     */
    @GetMapping("/{userId}/requests")
    public List<RequestDto> getAllForUser(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                   @PathVariable Long userId) {
        log.info("Входящий запрос на получение всех заявок на участие в чужих событиях для пользователя с id = "
                + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return requestService.getAllForUser(userId);
        }
    }
}
