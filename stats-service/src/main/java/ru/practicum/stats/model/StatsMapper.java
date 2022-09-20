package ru.practicum.stats.model;

/**
 * Класс, описывающий маппинг сущности статистики из dto в модель
 */
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
