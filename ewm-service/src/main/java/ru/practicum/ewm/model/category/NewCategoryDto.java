package ru.practicum.ewm.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Класс, описывающий dto категории для получения от клиента по api
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotEmpty
    @NotBlank
    private String name;
}
