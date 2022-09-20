package ru.practicum.ewm.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private RequestState status;
    private LocalDateTime created;
}
