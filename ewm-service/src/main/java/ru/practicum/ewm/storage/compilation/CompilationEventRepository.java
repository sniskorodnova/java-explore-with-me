package ru.practicum.ewm.storage.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.compilationevent.CompilationEvent;

/**
 * Репозиторий для работы со связями подборок и событий
 */
public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Long> {
}
