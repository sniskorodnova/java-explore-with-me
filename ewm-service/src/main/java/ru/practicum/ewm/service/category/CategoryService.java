package ru.practicum.ewm.service.category;

import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.NewCategoryDto;

import java.util.List;

/**
 * Интерфейс, описывающий логику для работы сервиса категорий
 */
public interface CategoryService {
    CategoryDto create(NewCategoryDto newCategory);

    CategoryDto update(CategoryDto newCategory);

    void deleteById(Long catId);

    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto getById(Long catId);
}
