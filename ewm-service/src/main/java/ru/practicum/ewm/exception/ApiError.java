package ru.practicum.ewm.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String reason;
    private String errors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(String status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.reason = ex.getLocalizedMessage();
    }
}
