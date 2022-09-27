package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
     * Метод для создания пользователя. В хедере передается авторизация для админа
     */
    @PostMapping()
    public UserDto create(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                          @RequestBody @Validated NewUserDto newUser) {
        log.info("Входящий запрос на создание пользователя: " + newUser.toString());
        return userService.create(newUser);
    }

    /**
     * Метод для получения списка всех пользователей. В хедере передается авторизация для админа
     */
    @GetMapping()
    public List<UserDto> getAll(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader,
                                @RequestParam(required = false) List<Integer> ids,
                                @RequestParam(required = false, defaultValue = "0") Integer from,
                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Входящий запрос на получение списка всех пользователей");
        if (ids == null) {
            return userService.getAll(from, size);
        } else {
            return userService.getAllByIds(ids);
        }
    }

    /**
     * Метод для удаления пользователя. В хедере передается авторизация для админа
     */
    @DeleteMapping("/{userId}")
    public void delete(@RequestHeader(value = "X-Sharer-User-Id") Long userHeader, @PathVariable Long userId) {
        log.info("Входящий запрос на удаление пользователя с id = " + userId);
        userService.delete(userId);
    }
}