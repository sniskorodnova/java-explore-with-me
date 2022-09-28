package ru.practicum.ewm.model.comment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий маппинг сущности комментария в dto и обратно
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getUserId(),
                comment.getEventId(),
                comment.getCreated(),
                comment.getStatus()
        );
    }

    public static Comment newToComment(NewCommentDto newCommentDto) {
        return new Comment(
                null,
                newCommentDto.getText(),
                null,
                null,
                null,
                null
        );
    }
}
