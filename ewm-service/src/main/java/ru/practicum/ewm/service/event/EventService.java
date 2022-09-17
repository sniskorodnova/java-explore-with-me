package ru.practicum.ewm.service.event;

import ru.practicum.ewm.model.event.EventDto;
import ru.practicum.ewm.model.event.NewEventDto;
import ru.practicum.ewm.model.event.NewShortEventDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Интерфейс, описывающий логику для работы сервиса событий
 */
public interface EventService {
    EventDto create(NewEventDto newEvent, Long userId);

    EventDto updateByUser(NewShortEventDto newShortEvent, Long userId);

    List<EventDto> getAllForUser(Long userId, Integer from, Integer size);

    EventDto getById(Long userId, Long eventId);

    EventDto rejectByUser(Long userId, Long eventId);

    List<EventDto> getAllForAdmin(List<Integer> users, List<String> states, List<Integer> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventDto publishByAdmin(Long eventId);

    EventDto rejectByAdmin(Long eventId);

    EventDto updateByAdmin(Long eventId, NewEventDto newEvent);

    List<EventDto> getAllPublic(String text, Integer[] categories, Boolean paid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size);

    EventDto getByIdPublic(Long eventId, String ip, String uri);
}
