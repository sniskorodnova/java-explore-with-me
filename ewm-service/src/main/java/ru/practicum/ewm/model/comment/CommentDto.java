package ru.practicum.ewm.model.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс, описывающий dto комментария к событию для отдачи клиенту по api
 */
@Data
@AllArgsConstructor
public class CommentDto {
    @NotNull
    private Long id;
    @NotEmpty
    @NotBlank
    private String text;
    @NotNull
    private Long userId;
    @NotNull
    private Long eventId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @NotNull
    private LocalDateTime created;
    @NotNull
    private CommentStatus status;
}
