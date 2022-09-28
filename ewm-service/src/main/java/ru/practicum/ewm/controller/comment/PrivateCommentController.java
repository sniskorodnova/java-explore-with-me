package ru.practicum.ewm.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.comment.CommentDto;
import ru.practicum.ewm.model.comment.NewCommentDto;
import ru.practicum.ewm.service.comment.CommentService;

import java.util.List;

/**
 * Класс-контроллер для работы с приватными запросами для комментариев к событиям
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PrivateCommentController {
    private final CommentService commentService;

    /**
     * Метод для создания комментария
     */
    @PostMapping("/users/{userId}/events/{eventId}/comments")
    public CommentDto create(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                             @PathVariable Long userId, @PathVariable Long eventId,
                             @RequestBody @Validated NewCommentDto newComment) {
        log.info("Входящий запрос на создание комментария: " + newComment.toString());
        return commentService.create(userId, eventId, newComment);
    }

    /**
     * Метод для редактирования комментария
     */
    @PutMapping("/users/{userId}/comments/{commentId}")
    public CommentDto update(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                             @PathVariable Long userId, @PathVariable Long commentId,
                             @RequestBody @Validated NewCommentDto newComment) {
        log.info("Входящий запрос на редактирование комментария c id = " + commentId + " : " + newComment.toString());
        return commentService.update(userId, commentId, newComment);
    }

    /**
     * Метод для удаления комментария пользователем
     */
    @DeleteMapping("/user/{userId}/comments/{commentId}")
    public void deleteById(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                           @PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Входящий запрос на удаление комментария с id = " + commentId + " пользователем с id = " + userId);
        commentService.deleteById(userId, commentId);
    }

    /**
     * Метод для получения комментария пользователем по id
     */
    @GetMapping("/users/{userId}/comments/{commentId}")
    public CommentDto getById(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                              @PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Входящий запрос на получение комментария с id = " + commentId + " пользователем с id = " + userId);
        return commentService.getById(userId, commentId);
    }

    /**
     * Метод для получения всех опубликованных комментариев к событию
     */
    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                   @PathVariable Long eventId) {
        log.info("Входящий запрос на получение всех комментариев для события с id = " + eventId);
        return commentService.getAll(eventId);
    }
}
