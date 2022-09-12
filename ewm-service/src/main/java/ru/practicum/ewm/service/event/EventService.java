package ru.practicum.ewm.service.event;

import ru.practicum.ewm.model.event.EventDto;
import ru.practicum.ewm.model.event.NewEventDto;

/**
 * Интерфейс, описывающий логику для работы сервиса событий
 */
public interface EventService {
    EventDto create(NewEventDto newEvent);
}
