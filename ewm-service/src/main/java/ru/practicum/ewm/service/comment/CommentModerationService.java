package ru.practicum.ewm.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.CommentNotFoundException;
import ru.practicum.ewm.model.comment.Comment;
import ru.practicum.ewm.model.comment.CommentStatus;
import ru.practicum.ewm.storage.comment.CommentRepository;

import java.util.Optional;

/**
 * Класс-сервис комментариев (администраторские методы)
 */
@Service
public class CommentModerationService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentModerationService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Метод для публикации комментария админом. Опубликовать комментарий можно в статусе PENDING. После
     * публикации статус комментария меняется на PUBLISHED
     */
    @Transactional
    public Comment publishByAdmin(long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment comment = commentOptional.orElseThrow(()
                -> new CommentNotFoundException("There is no comment with id = " + commentId));

        if (!(comment.getStatus().equals(CommentStatus.PENDING))) {
            return comment;
        } else {
            comment.setStatus(CommentStatus.PUBLISHED);
            return commentRepository.save(comment);
        }
    }

    /**
     * Метод для отклонения комментария админом. Отклонить комментарий можно в статусах PENDING и
     * PUBLISHED. После отклонения статус комментария меняется на REJECTED
     */
    @Transactional
    public Comment rejectByAdmin(long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment comment = commentOptional.orElseThrow(()
                -> new CommentNotFoundException("There is no comment with id = " + commentId));

        if (comment.getStatus().equals(CommentStatus.REJECTED)) {
            return comment;
        } else {
            comment.setStatus(CommentStatus.REJECTED);
            return commentRepository.save(comment);
        }
    }
}
