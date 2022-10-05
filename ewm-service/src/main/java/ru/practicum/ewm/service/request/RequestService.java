package ru.practicum.ewm.service.request;

import ru.practicum.ewm.model.request.RequestDto;

import java.util.List;

/**
 * Интерфейс, описывающий логику для работы сервиса запросов на участие в событиях
 */
public interface RequestService {
    RequestDto create(Long userId, Long eventId);

    RequestDto cancel(Long userId, Long requestId);

    List<RequestDto> getAllForUser(Long userId);

    List<RequestDto> getAllForUserEvents(Long userId, Long eventId);

    RequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    RequestDto rejectRequest(Long userId, Long eventId, Long reqId);
}
