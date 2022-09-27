package ru.practicum.ewm.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс, описывающий dto заявки на участие в событии для отдачи клиенту по api
 */
@Data
@AllArgsConstructor
public class RequestDto {
    @NotNull
    private Long id;
    @NotNull
    private Long event;
    @NotNull
    private Long requester;
    @NotNull
    private RequestState status;
    @NotNull
    private LocalDateTime created;
}
