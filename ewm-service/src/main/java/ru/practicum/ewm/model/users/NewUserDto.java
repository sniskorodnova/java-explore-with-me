package ru.practicum.ewm.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * Класс, описывающий dto пользователя для получения от клиента по api
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    @NotEmpty
    @NotBlank
    private String name;
    @NotEmpty
    @NotBlank
    @Email
    private String email;
}
