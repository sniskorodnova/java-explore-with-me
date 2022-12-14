package ru.practicum.ewm.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.client.BaseClient;
import ru.practicum.ewm.model.stats.NewStatsDto;


import java.time.LocalDateTime;
import java.util.Map;

/**
 * Клиент для отправки запросов в сервис статистики
 */
@Service
public class EventClient extends BaseClient {
    @Autowired
    public EventClient(@Value("${stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> create(NewStatsDto newStats) {
        return post("/hit", newStats);
    }

    public ResponseEntity<Object> get(LocalDateTime start, LocalDateTime end, String uris, Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        return get("/stats" + "?start=" + start + "&end=" + end + "&uris=" + uris + "&unique=" + unique,
                parameters);
    }
}
