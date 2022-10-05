package ru.practicum.ewm.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Класс, описывающий dto комментария к событию для получения от клиента по api при создании
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCommentDto {
    @NotEmpty
    @NotBlank
    private String text;
}
