package ru.practicum.ewm.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String email;
}
