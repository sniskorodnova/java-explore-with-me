package ru.practicum.stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.model.NewStatsDto;
import ru.practicum.stats.model.Stats;
import ru.practicum.stats.model.StatsDto;
import ru.practicum.stats.model.StatsMapper;
import ru.practicum.stats.storage.StatsRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс, имплементирующий интерфейс для работы сервиса статистики
 */
@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final EntityManager em;

    @Autowired
    public StatsServiceImpl(StatsRepository statsRepository, EntityManager em) {
        this.statsRepository = statsRepository;
        this.em = em;
    }

    /**
     * Метод для создания записи об обращении к эндпойнту
     */
    @Override
    public void create(NewStatsDto newStats) {
        Stats stats = StatsMapper.newToStats(newStats);
        stats.setTimestamp(LocalDateTime.now());
        statsRepository.save(stats);
    }

    /**
     * Метод для получения информации о количестве просмотров для эндпойнта по заданным параметрам
     */
    @Override
    @Transactional(readOnly = true)
    public List<StatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            final String queryUnique =
                    "SELECT new ru.practicum.stats.model.StatsDto(app, uri, count(distinct ip)) from Stats "
                            + "WHERE timestamp >= :start and timestamp <= :end and "
                            + "uri in (:uris) "
                            + "group by app, uri";
            List<StatsDto> statsFound = em.createQuery(queryUnique, StatsDto.class)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .setParameter("uris", uris)
                    .getResultList();
            return statsFound;
        } else {
            final String queryNonUnique =
                    "SELECT new ru.practicum.stats.model.StatsDto(app, uri, count(ip)) from Stats "
                            + "WHERE timestamp >= :start and timestamp <= :end and "
                            + "uri in (:uris) "
                            + "group by app, uri";
            List<StatsDto> statsFound = em.createQuery(queryNonUnique, StatsDto.class)
                   .setParameter("start", start)
                    .setParameter("end", end)
                    .setParameter("uris", uris)
                    .getResultList();
            return statsFound;
        }
    }
}
