package ru.practicum.ewm.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.model.users.NewUserDto;
import ru.practicum.ewm.model.users.User;
import ru.practicum.ewm.model.users.UserDto;
import ru.practicum.ewm.model.users.UserMapper;
import ru.practicum.ewm.storage.users.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto create(NewUserDto newUser) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.newToUser(newUser)));
    }

    @Override
    public List<UserDto> getAll(Integer from, Integer size) {
        List<UserDto> foundList = new ArrayList<>();
        for (User user : userRepository.findAllByOrderByIdAsc(PageRequest.of(from / size, size))) {
            foundList.add(UserMapper.toUserDto(user));
        }
        return foundList;
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
