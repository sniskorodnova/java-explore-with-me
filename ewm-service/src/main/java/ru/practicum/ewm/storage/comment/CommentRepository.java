package ru.practicum.ewm.storage.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.comment.Comment;

/**
 * Репозиторий для работы с комментариями к событиям
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
