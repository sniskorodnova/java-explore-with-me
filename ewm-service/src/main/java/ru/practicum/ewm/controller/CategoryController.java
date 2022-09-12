package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exception.NoHeaderException;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.NewCategoryDto;
import ru.practicum.ewm.service.category.CategoryService;

import java.util.List;

/**
 * Класс-контроллер для работы с категориями
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Метод для создания категории
     */
    @PostMapping("/admin/categories")
    public CategoryDto create(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                              @RequestBody NewCategoryDto newCategory) {
        log.info("Входящий запрос на создание категории: " + newCategory.toString());
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return categoryService.create(newCategory);
        }
    }

    @PatchMapping("/admin/categories")
    public CategoryDto update(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                              @RequestBody CategoryDto category) {
        log.info("Входящий запрос на редактирование категории: " + category.toString());
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return categoryService.update(category);
        }
    }

    @DeleteMapping("/admin/categories/{catId}")
    public void delete(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                              @PathVariable Long catId) {
        log.info("Входящий запрос на удаление категории c id = " + catId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            categoryService.deleteById(catId);
        }
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAll(@RequestParam Integer from, @RequestParam Integer size) {
        log.info("Входящий запрос на получение списка всех категорий");
        return categoryService.getAll(from, size);
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getById(@PathVariable Long catId) {
        log.info("Входящий запрос на получение категории с id = " + catId);
        return categoryService.getById(catId);
    }
}
