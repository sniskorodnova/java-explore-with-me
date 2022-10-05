package ru.practicum.ewm.storage.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.comment.Comment;
import ru.practicum.ewm.model.comment.CommentStatus;
import ru.practicum.ewm.model.event.Event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с комментариями к событиям
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from comment "
            + "where event_id = :eventId and status = :status",
            nativeQuery = true)
    List<Comment> findByEventIdAndStatusWithPagination(long eventId, CommentStatus status, Pageable pageable);

    List<Comment> findByEventIdAndStatus(long eventId, CommentStatus status);
}
