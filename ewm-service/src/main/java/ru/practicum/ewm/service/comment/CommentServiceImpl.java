package ru.practicum.ewm.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.CommentNotFoundException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserCantCreateCommentException;
import ru.practicum.ewm.exception.UserCantModifyCommentException;
import ru.practicum.ewm.model.comment.*;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.EventState;
import ru.practicum.ewm.storage.comment.CommentRepository;
import ru.practicum.ewm.storage.event.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс-сервис комментариев
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Метод для создания комментария. Владелец события может оставить комментарий к своему событию.
     * Оставить комментарий можно только к опубликованному событию. Пользователь может оставлять
     * несколько комментариев к одному событию (например, это будут ответы на вопросы других пользователей
     * о данной событии). Комментарий создается в статусе PENDING
     */
    @Transactional
    public CommentDto create(long userId, long eventId, NewCommentDto newComment) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        Event event = eventOptional.orElseThrow(()
                -> new EventNotFoundException("There is no event with id = " + eventId));

        if (!(event.getEventState().equals(EventState.PUBLISHED))) {
            throw new UserCantCreateCommentException("Event with id = " + eventId + " is not PUBLISHED");
        } else {
            Comment comment = CommentMapper.newToComment(newComment);
            comment.setUserId(userId);
            comment.setEventId(eventId);
            comment.setCreated(LocalDateTime.now());
            comment.setStatus(CommentStatus.PENDING);
            return CommentMapper.toCommentDto(commentRepository.save(comment));
        }
    }

    /**
     * Метод для редактирования комментария. Комментарий может отредактировать только пользователь, который
     * его создал. Редактировать комментарии можно только в статусах PENDING и REJECTED. После редактирования
     * комментарий переходит в статус PENDING
     */
    @Transactional
    public CommentDto update(long userId, long commentId, UpdateCommentDto updateComment) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment commentFound = commentOptional.orElseThrow(()
                -> new CommentNotFoundException("There is no comment with id = " + commentId));

        if (!(commentFound.getUserId().equals(userId))) {
            throw new UserCantModifyCommentException("Comment with id = " + commentId + " doesn't belong to user "
                    + "with id = " + userId);
        } else {
            if (commentFound.getStatus().equals(CommentStatus.PUBLISHED)) {
                throw new UserCantModifyCommentException("Comment with id = " + commentId + " is not PENDING "
                        + "or REJECTED");
            } else {
                Comment comment = CommentMapper.updateToComment(updateComment);
                commentFound.setText(comment.getText());
                commentFound.setStatus(CommentStatus.PENDING);
                return CommentMapper.toCommentDto(commentRepository.save(comment));
            }
        }
    }

    /**
     * Метод для удаления комментария пользователем. Удалить комментарий может только пользователь, который
     * его создал. Удалить можно комментарий из любого статуса
     */
    @Transactional
    public void deleteById(long userId, long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment comment = commentOptional.orElseThrow(()
                -> new CommentNotFoundException("There is no comment with id = " + commentId));

        if (!(comment.getUserId().equals(userId))) {
            throw new UserCantModifyCommentException("Comment with id = " + commentId + " doesn't belong "
                    + "to user with id = " + userId);
        } else {
            commentRepository.deleteById(commentId);
        }
    }

    /**
     * Метод для получения комментария пользователем. Получить комментарий в любом статусе может только пользователь,
     * который его создал
     */
    @Transactional(readOnly = true)
    public CommentDto getById(long userId, long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment comment = commentOptional.orElseThrow(()
                -> new CommentNotFoundException("There is no comment with id = " + commentId));

        if (!(comment.getUserId().equals(userId))) {
            throw new UserCantModifyCommentException("Comment with id = " + commentId + " doesn't belong "
                    + "to user with id = " + userId);
        } else {
            return CommentMapper.toCommentDto(commentRepository.findById(commentId).get());
        }
    }

    /**
     * Метод для получения всех опубликованных комментариев к событию
     */
    @Transactional(readOnly = true)
    public List<CommentDto> getAll(long eventId, int from, int size) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        eventOptional.orElseThrow(()
                -> new EventNotFoundException("There is no event with id = " + eventId));
        return commentRepository.findByEventIdAndStatusWithPagination(eventId, CommentStatus.PUBLISHED,
                PageRequest.of(from / size, size))
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод для публикации комментария админом. Опубликовать комментарий можно в статусе PENDING. После
     * публикации статус комментария меняется на PUBLISHED
     */
    @Transactional
    public CommentDto publishByAdmin(long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment comment = commentOptional.orElseThrow(()
                -> new CommentNotFoundException("There is no comment with id = " + commentId));

        if (!(comment.getStatus().equals(CommentStatus.PENDING))) {
            return CommentMapper.toCommentDto(comment);
        } else {
            comment.setStatus(CommentStatus.PUBLISHED);
            return CommentMapper.toCommentDto(commentRepository.save(comment));
        }
    }

    /**
     * Метод для отклонения комментария админом. Отклонить комментарий можно в статусах PENDING и
     * PUBLISHED. После отклонения статус комментария меняется на REJECTED
     */
    @Transactional
    public CommentDto rejectByAdmin(long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Comment comment = commentOptional.orElseThrow(()
                -> new CommentNotFoundException("There is no comment with id = " + commentId));

        if (comment.getStatus().equals(CommentStatus.REJECTED)) {
            return CommentMapper.toCommentDto(comment);
        } else {
            comment.setStatus(CommentStatus.REJECTED);
            return CommentMapper.toCommentDto(commentRepository.save(comment));
        }
    }
}
