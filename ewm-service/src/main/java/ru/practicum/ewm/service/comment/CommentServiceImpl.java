package ru.practicum.ewm.service.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.CommentNotFoundException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.UserCantCreateCommentException;
import ru.practicum.ewm.exception.UserCantModifyCommentException;
import ru.practicum.ewm.model.comment.*;
import ru.practicum.ewm.model.event.EventState;
import ru.practicum.ewm.storage.comment.CommentRepository;
import ru.practicum.ewm.storage.event.EventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, имплементирующий интерфейс для работы сервиса комментариев
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
     * Метод для создания комментария. Владелец события не может оставить комментарий к своему событию.
     * Оставить комментарий можно только к опубликованному событию, дата которого в прошлом.
     * Комментарий создается в статусе PENDING
     */
    @Override
    public CommentDto create(Long userId, Long eventId, NewCommentDto newComment) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            if (eventRepository.findById(eventId).get().getInitiator().getId().equals(userId)) {
                throw new UserCantCreateCommentException("Event with id = " + eventId + " belongs to user "
                        + "with id = " + userId);
            } else {
                if (eventRepository.findById(eventId).get().getEventDate().isAfter(LocalDateTime.now())
                        && !(eventRepository.findById(eventId).get().getEventState().equals(EventState.PUBLISHED))) {
                    throw new UserCantCreateCommentException("Event with id = " + eventId + " is not PUBLISHED "
                            + "or it's date is in the future");
                } else {
                    Comment comment = CommentMapper.newToComment(newComment);
                    comment.setUserId(userId);
                    comment.setEventId(eventId);
                    comment.setCreated(LocalDateTime.now());
                    comment.setStatus(CommentStatus.PENDING);
                    return CommentMapper.toCommentDto(commentRepository.save(comment));
                }
            }
        }
    }

    /**
     * Метод для редактирования комментария. Комментарий может отредактировать только пользователь, который
     * его создал. Редактировать комментарии можно только в статусах PENDING и REJECTED. После редактирования
     * комментарий переходит в статус PENDING
     */
    @Override
    public CommentDto update(Long userId, Long commentId, NewCommentDto newComment) {
        if (commentRepository.findById(commentId).isEmpty()) {
            throw new CommentNotFoundException("There is no comment with id = " + commentId);
        } else {
            if (!(commentRepository.findById(commentId).get().getUserId().equals(userId))) {
                throw new UserCantModifyCommentException("Comment with id = " + commentId + " doesn't belong to user "
                        + "with id = " + userId);
            } else {
                if (commentRepository.findById(commentId).get().getStatus().equals(CommentStatus.PUBLISHED)) {
                    throw new UserCantModifyCommentException("Comment with id = " + commentId + " is not PENDING "
                            + "or REJECTED");
                } else {
                    Comment comment = commentRepository.findById(commentId).get();
                    comment.setText(newComment.getText());
                    comment.setStatus(CommentStatus.PENDING);
                    return CommentMapper.toCommentDto(commentRepository.save(comment));
                }
            }
        }
    }

    /**
     * Метод для публикации комментария админом. Опубликовать комментарий можно в статусе PENDING. После
     * публикации статус комментария меняется на PUBLISHED
     */
    @Override
    public CommentDto publishByAdmin(Long commentId) {
        if (commentRepository.findById(commentId).isEmpty()) {
            throw new CommentNotFoundException("There is no comment with id = " + commentId);
        } else {
            if (!(commentRepository.findById(commentId).get().getStatus().equals(CommentStatus.PENDING))) {
                throw new UserCantModifyCommentException("Comment with id = " + commentId + " is not PENDING");
            } else {
                Comment comment = commentRepository.findById(commentId).get();
                comment.setStatus(CommentStatus.PUBLISHED);
                return CommentMapper.toCommentDto(commentRepository.save(comment));
            }
        }
    }

    /**
     * Метод для отклонения комментария админом. Отклонить комментарий можно в статусах PENDING и
     * PUBLISHED. После отклонения статус комментария меняется на REJECTED
     */
    @Override
    public CommentDto rejectByAdmin(Long commentId) {
        if (commentRepository.findById(commentId).isEmpty()) {
            throw new CommentNotFoundException("There is no comment with id = " + commentId);
        } else {
            if (!(commentRepository.findById(commentId).get().getStatus().equals(CommentStatus.PENDING)
                    || commentRepository.findById(commentId).get().getStatus().equals(CommentStatus.PUBLISHED))) {
                throw new UserCantModifyCommentException("Comment with id = " + commentId + " is not "
                        + "PENDING or PUBLISHED");
            } else {
                Comment comment = commentRepository.findById(commentId).get();
                comment.setStatus(CommentStatus.REJECTED);
                return CommentMapper.toCommentDto(commentRepository.save(comment));
            }
        }
    }

    /**
     * Метод для удаления комментария пользователем. Удалить комментарий может только пользователь, который
     * его создал. Удалить можно комментарий из любого статуса
     */
    @Override
    public void deleteById(Long userId, Long commentId) {
        if (commentRepository.findById(commentId).isEmpty()) {
            throw new CommentNotFoundException("There is no comment with id = " + commentId);
        } else {
            if (!(commentRepository.findById(commentId).get().getUserId().equals(userId))) {
                throw new UserCantModifyCommentException("Comment with id = " + commentId + " doesn't belong "
                        + "to user with id = " + userId);
            } else {
                commentRepository.deleteById(commentId);
            }
        }
    }

    /**
     * Метод для получения комментария пользователем. Получить комментарий в любом статусе может только пользователь,
     * который его создал
     */
    @Override
    public CommentDto getById(Long userId, Long commentId) {
        if (commentRepository.findById(commentId).isEmpty()) {
            throw new CommentNotFoundException("There is no comment with id = " + commentId);
        } else {
            if (!(commentRepository.findById(commentId).get().getUserId().equals(userId))) {
                throw new UserCantModifyCommentException("Comment with id = " + commentId + " doesn't belong "
                        + "to user with id = " + userId);
            } else {
                return CommentMapper.toCommentDto(commentRepository.findById(commentId).get());
            }
        }
    }

    /**
     * Метод для получения всех опубликованных комментариев к событию
     */
    @Override
    public List<CommentDto> getAll(Long eventId) {
        if (eventRepository.findById(eventId).isEmpty()) {
            throw new EventNotFoundException("There is no event with id = " + eventId);
        } else {
            List<CommentDto> foundList = new ArrayList<>();
            for (Comment comment : commentRepository.findByEventIdAndStatus(eventId, CommentStatus.PUBLISHED)) {
                foundList.add(CommentMapper.toCommentDto(comment));
            }
            return foundList;
        }
    }
}
