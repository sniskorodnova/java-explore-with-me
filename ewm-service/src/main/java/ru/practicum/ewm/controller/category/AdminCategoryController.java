package ru.practicum.ewm.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.NewCategoryDto;
import ru.practicum.ewm.service.category.CategoryService;

/**
 * Класс-контроллер для работы с администраторскими запросами для категорий
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class AdminController {
    private final CategoryService categoryService;

    /**
     * Метод для создания категории. В хедере передается авторизация для админа
     */
    @PostMapping("/admin/categories")
    public CategoryDto create(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                              @RequestBody @Validated NewCategoryDto newCategory) {
        log.info("Входящий запрос на создание категории: " + newCategory.toString());
        return categoryService.create(newCategory);
    }

    /**
     * Метод для редактирования категории. В хедере передается авторизация дла админа
     */
    @PatchMapping("/admin/categories")
    public CategoryDto update(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                              @RequestBody @Validated CategoryDto category) {
        log.info("Входящий запрос на редактирование категории: " + category.toString());
        return categoryService.update(category);
    }

    /**
     * Метод для удаления категории. В хедере передается авторизация дла админа
     */
    @DeleteMapping("/admin/categories/{catId}")
    public void delete(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                       @PathVariable Long catId) {
        log.info("Входящий запрос на удаление категории c id = " + catId);
        categoryService.deleteById(catId);
    }
}
