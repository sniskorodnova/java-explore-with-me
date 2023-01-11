package ru.practicum.ewm.service.comment;

import ru.practicum.ewm.model.comment.CommentDto;
import ru.practicum.ewm.model.comment.NewCommentDto;
import ru.practicum.ewm.model.comment.UpdateCommentDto;

import java.util.List;

/**
 * Интерфейс, описывающий логику для работы сервиса комментариев
 */
public interface CommentService {
    CommentDto create(long userId, long eventId, NewCommentDto newComment);

    CommentDto update(long userId, long commentId, UpdateCommentDto updateComment);

    void deleteById(long userId, long commentId);

    CommentDto getById(long userId, long commentId);

    List<CommentDto> getAll(long eventId, int from, int size);

    CommentDto publishByAdmin(long commentId);

    CommentDto rejectByAdmin(long commentId);
}
