package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.exception.NoHeaderException;
import ru.practicum.ewm.model.users.NewUserDto;
import ru.practicum.ewm.model.users.UserDto;
import ru.practicum.ewm.service.users.UserService;

import java.util.List;

/**
 * Класс-контроллер для работы с пользователями
 */
@Validated
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserController {
    private final UserService userService;

    /**
     * Метод для создания пользователя
     */
    @PostMapping()
    public UserDto create(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                          @RequestBody NewUserDto newUser) {
        log.info("Входящий запрос на создание пользователя: " + newUser.toString());
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return userService.create(newUser);
        }
    }

    /**
     * Метод для получения списка всех пользователей
     */
    @GetMapping()
    public List<UserDto> getAll(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                @RequestParam Integer from, @RequestParam Integer size) {
        log.info("Входящий запрос на получение списка всех пользователей");
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            return userService.getAll(from, size);
        }
    }

    /**
     * Метод для удаления пользователя
     */
    @DeleteMapping("/{userId}")
    public void delete(@RequestHeader(value = "X-Sharer-User-Id", required = false) Long userHeader,
                                @PathVariable Long userId) {
        log.info("Входящий запрос на удаление пользователя с id = " + userId);
        if (userHeader == null) {
            throw new NoHeaderException("No header in the request");
        } else {
            userService.delete(userId);
        }
    }
}