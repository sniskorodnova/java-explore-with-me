package ru.practicum.ewm.storage.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.category.Category;

import java.util.List;

/**
 * Репозиторий для работы с категориями
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByIdAsc(Pageable pageable);
}