package ru.practicum.ewm.model.users;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий маппинг сущности пользователя в dto и обратно
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static User newToUser(NewUserDto newUserDto) {
        return new User(
                null,
                newUserDto.getName(),
                newUserDto.getEmail()
        );
    }
}
