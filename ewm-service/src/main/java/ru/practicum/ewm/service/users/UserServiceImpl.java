package ru.practicum.ewm.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.model.users.NewUserDto;
import ru.practicum.ewm.model.users.User;
import ru.practicum.ewm.model.users.UserDto;
import ru.practicum.ewm.model.users.UserMapper;
import ru.practicum.ewm.storage.users.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, имплементирующий интерфейс для работы сервиса пользователей
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод для создания пользователя. Email пользователя должен быть уникальным
     */
    @Transactional
    @Override
    public UserDto create(NewUserDto newUser) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.newToUser(newUser)));
    }

    /**
     * Метод для получения списка всех пользователей с пагинацией
     */
    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAll(Integer from, Integer size) {
        List<UserDto> foundList = new ArrayList<>();
        for (User user : userRepository.findAllByOrderByIdAsc(PageRequest.of(from / size, size))) {
            foundList.add(UserMapper.toUserDto(user));
        }
        return foundList;
    }

    /**
     * Метод для получения списка пользователей по переданным в запросе id
     */
    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllByIds(List<Integer> ids) {
        List<UserDto> foundList = new ArrayList<>();
        for (User user : userRepository.findAllByIds(ids)) {
            foundList.add(UserMapper.toUserDto(user));
        }
        return foundList;
    }

    /**
     * Метод для удаления пользователя по id
     */
    @Transactional
    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
