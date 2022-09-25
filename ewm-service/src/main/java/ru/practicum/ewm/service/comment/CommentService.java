package ru.practicum.ewm.service.comment;

import ru.practicum.ewm.model.comment.CommentDto;
import ru.practicum.ewm.model.comment.NewCommentDto;

import java.util.List;

/**
 * Интерфейс, описывающий логику для работы сервиса комментариев
 */
public interface CommentService {
    CommentDto create(Long userId, Long eventId, NewCommentDto newComment);

    CommentDto update(Long userId, Long commentId, NewCommentDto newComment);

    CommentDto publishByAdmin(Long commentId);

    CommentDto rejectByAdmin(Long commentId);

    void deleteById(Long userId, Long commentId);

    CommentDto getById(Long userId, Long commentId);

    List<CommentDto> getAll(Long eventId);
}
