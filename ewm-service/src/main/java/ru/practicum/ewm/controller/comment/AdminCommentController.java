package ru.practicum.ewm.controller.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.comment.CommentDto;
import ru.practicum.ewm.service.comment.CommentService;

/**
 * Класс-контроллер для работы с администраторскими запросами для комментариев к событиям
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class AdminCommentController {
    private final CommentService commentService;

    /**
     * Метод для публикации комментария админом
     */
    @PatchMapping("/admin/comments/{commentId}/publish")
    public CommentDto publishByAdmin(@RequestHeader(value = "X-Sharer-User-Id") long userHeader,
                                     @PathVariable long commentId) {
        log.debug("Входящий запрос на публикацию админом комментария с id = " + commentId);
        return commentService.publishByAdmin(commentId);
    }

    /**
     * Метод для отклонения комментария админом
     */
    @PatchMapping("/admin/comments/{commentId}/reject")
    public CommentDto rejectByAdmin(@RequestHeader(value = "X-Sharer-User-Id") long userHeader,
                                    @PathVariable long commentId) {
        log.debug("Входящий запрос на отклонение админом комментария с id = " + commentId);
        return commentService.rejectByAdmin(commentId);
    }
}
