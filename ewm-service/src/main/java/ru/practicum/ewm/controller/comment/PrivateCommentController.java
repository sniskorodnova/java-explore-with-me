package ru.practicum.ewm.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.comment.CommentDto;
import ru.practicum.ewm.model.comment.CommentMapper;
import ru.practicum.ewm.model.comment.NewCommentDto;
import ru.practicum.ewm.model.comment.UpdateCommentDto;
import ru.practicum.ewm.service.comment.CommentService;

import java.util.List;
import java.util.stream.Collectors;

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
    public CommentDto create(@RequestHeader(value = "X-Sharer-User-Id") long userHeader,
                             @PathVariable long userId, @PathVariable long eventId,
                             @RequestBody @Validated NewCommentDto newComment) {
        log.debug("Входящий запрос на создание комментария: " + newComment.toString());
        return CommentMapper.toCommentDto(commentService.create(userId, eventId, CommentMapper.newToComment(newComment)));
    }

    /**
     * Метод для редактирования комментария
     */
    @PutMapping("/users/{userId}/comments/{commentId}")
    public CommentDto update(@RequestHeader(value = "X-Sharer-User-Id") long userHeader,
                             @PathVariable long userId, @PathVariable long commentId,
                             @RequestBody @Validated UpdateCommentDto updateComment) {
        log.debug("Входящий запрос на редактирование комментария c id = " + commentId + " : "
                + updateComment.toString());
        return CommentMapper.toCommentDto(commentService.update(userId, commentId,
                CommentMapper.updateToComment(updateComment)));
    }

    /**
     * Метод для удаления комментария пользователем
     */
    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public void deleteById(@RequestHeader(value = "X-Sharer-User-Id") long userHeader,
                           @PathVariable long userId, @PathVariable long commentId) {
        log.debug("Входящий запрос на удаление комментария с id = " + commentId + " пользователем с id = " + userId);
        commentService.deleteById(userId, commentId);
    }

    /**
     * Метод для получения комментария пользователем по id
     */
    @GetMapping("/users/{userId}/comments/{commentId}")
    public CommentDto getById(@RequestHeader(value = "X-Sharer-User-Id") long userHeader,
                              @PathVariable long userId, @PathVariable long commentId) {
        log.debug("Входящий запрос на получение комментария с id = " + commentId + " пользователем с id = " + userId);
        return CommentMapper.toCommentDto(commentService.getById(userId, commentId));
    }

    /**
     * Метод для получения всех опубликованных комментариев к событию
     */
    @GetMapping("/events/{eventId}/comments")
    public List<CommentDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") long userHeader,
                                   @PathVariable long eventId, @RequestParam(defaultValue = "0") int from,
                                   @RequestParam(defaultValue = "10") int size) {
        log.debug("Входящий запрос на получение всех комментариев для события с id = " + eventId);
        return commentService.getAll(eventId, from, size)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
