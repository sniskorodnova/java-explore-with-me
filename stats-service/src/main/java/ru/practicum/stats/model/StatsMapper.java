package ru.practicum.stats.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий маппинг сущности статистики из dto в модель
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatsMapper {
    public static Stats newToStats(NewStatsDto newStatsDto) {
        return new Stats(
                null,
                newStatsDto.getApp(),
                newStatsDto.getUri(),
                newStatsDto.getIp(),
                null
        );
    }
}
