package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exception.NoHeaderException;
import ru.practicum.ewm.model.comment.CommentDto;
import ru.practicum.ewm.model.comment.NewCommentDto;
import ru.practicum.ewm.service.comment.CommentService;

import java.util.List;

/**
 * Класс-контроллер для работы с комментариями к событиям
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class CommentController {
    private final CommentService commentService;

    /**
     * Метод для создания комментария. В хедере передается авторизация для пользователя
     */
    @PostMapping("/users/{userId}/events/{eventId}/comments")
    public CommentDto create(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                             @PathVariable Long userId, @PathVariable Long eventId,
                             @RequestBody @Validated NewCommentDto newComment) {
        log.info("Входящий запрос на создание комментария: " + newComment.toString());
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return commentService.create(userId, eventId, newComment);
        }
    }

    /**
     * Метод для редактирования комментария. В хедере передается авторизация для пользователя
     */
    @PutMapping("/users/{userId}/comments/{commentId}")
    public CommentDto update(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                             @PathVariable Long userId, @PathVariable Long commentId,
                             @RequestBody @Validated NewCommentDto newComment) {
        log.info("Входящий запрос на редактирование комментария c id = " + commentId + " : " + newComment.toString());
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return commentService.update(userId, commentId, newComment);
        }
    }

    /**
     * Метод для публикации комментария админом. В хедере передается авторизация для админа
     */
    @PatchMapping("/admin/comments/{commentId}/publish")
    public CommentDto publishByAdmin(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                     @PathVariable Long commentId) {
        log.info("Входящий запрос на публикацию админом комментария с id = " + commentId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return commentService.publishByAdmin(commentId);
        }
    }

    /**
     * Метод для отклонения комментария админом. В хедере передается авторизация для админа
     */
    @PatchMapping("/admin/comments/{commentId}/reject")
    public CommentDto rejectByAdmin(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                     @PathVariable Long commentId) {
        log.info("Входящий запрос на отклонение админом комментария с id = " + commentId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return commentService.rejectByAdmin(commentId);
        }
    }

    /**
     * Метод для удаления комментария пользователем. В хедере передается авторизация для пользователя
     */
    @DeleteMapping("/user/{userId}/comments/{commentId}")
    public void deleteById(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                    @PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Входящий запрос на удаление комментария с id = " + commentId + " пользователем с id = " + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            commentService.deleteById(userId, commentId);
        }
    }

    /**
     * Метод для получения комментария пользователем по id. В хедере передается авторизация для пользователя
     */
    @GetMapping("/users/{userId}/comments/{commentId}")
    public CommentDto getById(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                           @PathVariable Long userId, @PathVariable Long commentId) {
        log.info("Входящий запрос на получение комментария с id = " + commentId + " пользователем с id = " + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return commentService.getById(userId, commentId);
        }
    }

    /**
     * Метод для получения всех опубликованных комментариев к событию.
     * В хедере передается авторизация для пользователя
     */
    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                    @PathVariable Long eventId) {
        log.info("Входящий запрос на получение всех комментариев для события с id = " + eventId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return commentService.getAll(eventId);
        }
    }
}
