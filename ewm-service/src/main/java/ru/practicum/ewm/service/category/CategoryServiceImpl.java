package ru.practicum.ewm.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.CategoryCantBeDeleted;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.CategoryMapper;
import ru.practicum.ewm.model.category.NewCategoryDto;
import ru.practicum.ewm.storage.category.CategoryRepository;
import ru.practicum.ewm.storage.event.EventRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, имплементирующий интерфейс для работы сервиса категорий
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, EventRepository eventRepository) {
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Метод для создания категории. Имя категории должно быть уникальным
     */
    @Override
    public CategoryDto create(NewCategoryDto newCategory) {
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.newToCategory(newCategory)));
    }

    /**
     * Метод для редактирования категории. Id категории, которую надо отредактировать, передается в теле запроса.
     * Имя категории должно быть уникальным
     */
    @Override
    public CategoryDto update(CategoryDto category) {
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(category)));
    }

    /**
     * Метод для удаления категории. Нельзя удалить категорию, если с ней связано хотя бы одно событие
     */
    @Override
    public void deleteById(Long catId) {
        if (eventRepository.findByCategoryId(catId).size() != 0) {
            throw new CategoryCantBeDeleted("There are linked events to this category");
        } else {
            categoryRepository.deleteById(catId);
        }
    }

    /**
     * Метод для получения списка всех категорий с пагинацией
     */
    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        List<CategoryDto> foundList = new ArrayList<>();
        for (Category category : categoryRepository.findAllByOrderByIdAsc(PageRequest.of(from / size, size))) {
            foundList.add(CategoryMapper.toCategoryDto(category));
        }
        return foundList;
    }

    /**
     * Метод для получения информации о категории по ее id
     */
    @Override
    public CategoryDto getById(Long catId) {
        if (categoryRepository.findById(catId).isEmpty()) {
            throw new CategoryNotFoundException("There is no category with id = " + catId);
        } else {
            return CategoryMapper.toCategoryDto(categoryRepository.findById(catId).get());
        }
    }
}
