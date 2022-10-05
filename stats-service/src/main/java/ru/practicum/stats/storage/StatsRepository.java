package ru.practicum.stats.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats.model.Stats;

/**
 * Репозиторий для работы со статистикой
 */
public interface StatsRepository extends JpaRepository<Stats, Long> {

}
