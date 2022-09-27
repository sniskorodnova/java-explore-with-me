package ru.practicum.ewm.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Класс, описывающий dto пользователя для отдачи клиенту по api
 */
@Data
@AllArgsConstructor
public class UserDto {
    @NotNull
    private Long id;
    @NotEmpty
    @NotBlank
    private String name;
    @Email
    @NotEmpty
    @NotBlank
    private String email;
}
