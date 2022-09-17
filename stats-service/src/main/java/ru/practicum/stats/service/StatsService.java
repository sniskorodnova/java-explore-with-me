package ru.practicum.stats.service;

import ru.practicum.stats.model.NewStatsDto;
import ru.practicum.stats.model.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void create(NewStatsDto newStats);

    List<StatsDto> get(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
