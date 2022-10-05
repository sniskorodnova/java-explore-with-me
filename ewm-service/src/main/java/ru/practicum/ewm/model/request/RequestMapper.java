package ru.practicum.ewm.model.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий маппинг сущности заявки на участие в событии в dto
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {
    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getEventId(),
                request.getUserId(),
                request.getRequestState(),
                request.getCreated()
        );
    }
}
