package ru.practicum.ewm.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.service.category.CategoryService;

import java.util.List;

/**
 * Класс-контроллер для работы с публичными запросами для категорий
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;

    /**
     * Метод для получения списка всех категорий с пагинацией
     */
    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam (defaultValue = "10") Integer size) {
        log.info("Входящий запрос на получение списка всех категорий");
        return categoryService.getAll(from, size);
    }

    /**
     * Метод для получения информации о категории по id
     */
    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable Long catId) {
        log.info("Входящий запрос на получение категории с id = " + catId);
        return categoryService.getById(catId);
    }
}
