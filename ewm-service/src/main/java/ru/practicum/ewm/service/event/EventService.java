package ru.practicum.ewm.service.event;

import ru.practicum.ewm.model.event.EventDto;
import ru.practicum.ewm.model.event.EventDtoWithViews;
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

    List<EventDtoWithViews> getAllForUser(Long userId, Integer from, Integer size);

    EventDtoWithViews getByIdForUser(Long userId, Long eventId);

    EventDto rejectByUser(Long userId, Long eventId);

    List<EventDtoWithViews> getAllForAdmin(List<Integer> users, List<String> states, List<Integer> categories,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventDto publishByAdmin(Long eventId);

    EventDto rejectByAdmin(Long eventId);

    EventDto updateByAdmin(Long eventId, NewEventDto newEvent);

    List<EventDtoWithViews> getAllPublic(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                         LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                         Integer size, String ip, String uri);

    EventDtoWithViews getByIdPublic(Long eventId, String ip, String uri);
}
