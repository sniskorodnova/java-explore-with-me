package ru.practicum.ewm.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.ewm.model.event.State;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestDto {
    private Long id;
    private Long eventId;
    private Long userId;
    private State state;
    private LocalDateTime created;
}
