package ru.practicum.ewm.storage.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.comment.Comment;
import ru.practicum.ewm.model.comment.CommentStatus;

import java.util.List;

/**
 * Репозиторий для работы с комментариями к событиям
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEventIdAndStatus(Long eventId, CommentStatus status);

    List<Comment> findByEventIdAndUserId(Long eventId, Long userId);
}
