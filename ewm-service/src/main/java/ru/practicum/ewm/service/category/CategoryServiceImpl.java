package ru.practicum.ewm.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.CategoryMapper;
import ru.practicum.ewm.model.category.NewCategoryDto;
import ru.practicum.ewm.storage.category.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, имплементирующий интерфейс для работы сервиса категорий
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto create(NewCategoryDto newCategory) {
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.newToCategory(newCategory)));
    }

    @Override
    public CategoryDto update(CategoryDto category) {
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.toCategory(category)));
    }

    @Override
    public void deleteById(Long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        List<CategoryDto> foundList = new ArrayList<>();
        for (Category category : categoryRepository.findAllByOrderByIdAsc(PageRequest.of(from / size, size))) {
            foundList.add(CategoryMapper.toCategoryDto(category));
        }
        return foundList;
    }

    @Override
    public CategoryDto getById(Long catId) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(catId).orElseThrow());
    }
}
