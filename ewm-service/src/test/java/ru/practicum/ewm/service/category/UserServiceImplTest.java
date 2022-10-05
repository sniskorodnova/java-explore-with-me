package ru.practicum.ewm.service.category;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm.model.users.NewUserDto;
import ru.practicum.ewm.model.users.UserDto;
import ru.practicum.ewm.service.users.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceImplTest {
    private final UserServiceImpl userServiceImpl;

    @Test
    public void checkUserCreatedSuccess() {
        NewUserDto newUserDto = new NewUserDto("Name", "email@gmail.com");
        UserDto userDto = new UserDto(1L, "Name", "email@gmail.com");
        userServiceImpl.create(newUserDto);
        List<UserDto> listToCompare = new ArrayList<>();
        listToCompare.add(userDto);
        assertThat(userServiceImpl.getAllByIds(List.of(1)), is(equalTo(listToCompare)));
    }

    @Test
    public void checkGetAllUsers() {
        NewUserDto newUserDto1 = new NewUserDto("Name1", "email1@gmail.com");
        UserDto userDto1 = new UserDto(1L, "Name1", "email1@gmail.com");
        NewUserDto newUserDto2 = new NewUserDto("Name2", "email2@gmail.com");
        UserDto userDto2 = new UserDto(2L, "Name2", "email2@gmail.com");
        userServiceImpl.create(newUserDto1);
        userServiceImpl.create(newUserDto2);
        List<UserDto> listToCompare = new ArrayList<>();
        listToCompare.add(userDto1);
        listToCompare.add(userDto2);
        assertThat(userServiceImpl.getAll(0, 5), is(equalTo(listToCompare)));
    }

    @Test
    public void checkGetAllByIds() {
        NewUserDto newUserDto1 = new NewUserDto("Name1", "email1@gmail.com");
        UserDto userDto1 = new UserDto(1L, "Name1", "email1@gmail.com");
        NewUserDto newUserDto2 = new NewUserDto("Name2", "email2@gmail.com");
        NewUserDto newUserDto3 = new NewUserDto("Name3", "email3@gmail.com");
        UserDto userDto3 = new UserDto(3L, "Name3", "email3@gmail.com");
        userServiceImpl.create(newUserDto1);
        userServiceImpl.create(newUserDto2);
        userServiceImpl.create(newUserDto3);
        List<UserDto> listToCompare = new ArrayList<>();
        listToCompare.add(userDto1);
        listToCompare.add(userDto3);
        assertThat(userServiceImpl.getAllByIds(List.of(1,3)), is(equalTo(listToCompare)));
    }

    @Test
    public void checkUserIsDeletedSuccess() {
        NewUserDto newUserDto = new NewUserDto("Name1", "email1@gmail.com");
        userServiceImpl.create(newUserDto);
        userServiceImpl.delete(1L);
        assertThat(userServiceImpl.getAllByIds(List.of(1)), is(equalTo(List.of())));
    }
}
