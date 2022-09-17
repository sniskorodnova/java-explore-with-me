package ru.practicum.ewm.model.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewStatsDto {
    private String app;
    private String uri;
    private String ip;
}