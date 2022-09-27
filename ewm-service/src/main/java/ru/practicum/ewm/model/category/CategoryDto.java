package ru.practicum.ewm.model.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Класс, описывающий dto категории для отдачи клиенту по api
 */
@Data
@AllArgsConstructor
public class CategoryDto {
    @NotNull
    private Long id;
    @NotEmpty
    @NotBlank
    private String name;
}
