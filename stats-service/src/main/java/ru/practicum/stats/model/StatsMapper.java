package ru.practicum.stats.model;

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
