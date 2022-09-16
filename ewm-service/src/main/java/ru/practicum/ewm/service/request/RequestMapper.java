package ru.practicum.ewm.service.request;

import ru.practicum.ewm.model.request.Request;
import ru.practicum.ewm.model.request.RequestDto;

public class RequestMapper {
    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getEventId(),
                request.getUserId(),
                request.getState(),
                request.getCreated()
        );
    }
}
