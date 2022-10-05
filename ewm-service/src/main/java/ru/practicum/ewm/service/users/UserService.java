package ru.practicum.ewm.service.users;

import ru.practicum.ewm.model.users.NewUserDto;
import ru.practicum.ewm.model.users.UserDto;

import java.util.List;

/**
 * Интерфейс, описывающий логику для работы сервиса пользователей
 */
public interface UserService {
    UserDto create(NewUserDto newUser);

    List<UserDto> getAll(Integer from, Integer size);

    List<UserDto> getAllByIds(List<Integer> ids);

    void delete(Long userId);
}
