package ru.practicum.ewm.storage.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.compilation.Compilation;

import java.util.List;

/**
 * Репозиторий для работы с подборками событий
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findByPinned(Boolean pinned, Pageable pageable);
}
