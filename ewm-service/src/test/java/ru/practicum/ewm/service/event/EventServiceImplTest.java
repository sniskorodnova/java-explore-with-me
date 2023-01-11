package ru.practicum.ewm.service.event;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.client.BaseClient;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.NewCategoryDto;
import ru.practicum.ewm.model.event.EventDto;
import ru.practicum.ewm.model.event.EventDtoWithViews;
import ru.practicum.ewm.model.event.EventState;
import ru.practicum.ewm.model.event.NewEventDto;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.users.NewUserDto;
import ru.practicum.ewm.model.users.User;
import ru.practicum.ewm.service.category.CategoryServiceImpl;
import ru.practicum.ewm.service.users.UserServiceImpl;
import ru.practicum.ewm.storage.category.CategoryRepository;
import ru.practicum.ewm.storage.event.EventRepository;
import ru.practicum.ewm.storage.users.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EventServiceImplTest {
    private final EventServiceImpl eventServiceImpl;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    @MockBean
    EventClient eventClient;

    @Test
    public void checkEventCreatedSuccess() {
        Map<String, Object> map = new HashMap<>();
        map.put("hits", 12L);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        Location location = new Location(1L, 12.28, 45.45);
        User user = new User(1L, "First", "f@gmail.com");
        userRepository.save(user);
        Category category = new Category(1L, "Музеи");
        categoryRepository.save(category);
        CategoryDto categoryDto = new CategoryDto(1L, "Музеи");
        NewEventDto newEventDto = new NewEventDto("Event title", "Event annotation",
                "Event description", location, LocalDateTime.now().plusDays(1), 1L, true,
                5L, true);
        when(eventClient.get(Mockito.any(), Mockito.any(), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(ResponseEntity.accepted().body(list));
        eventServiceImpl.create(newEventDto, 1L);
        EventDtoWithViews eventDto = new EventDtoWithViews(1L, "Event title", "Event annotation",
                "Event description", location, eventRepository.findById(1L).orElseThrow().getEventDate(),
                categoryDto, true, 5L, true, user, EventState.PENDING, 12L,
                0L, eventRepository.findById(1L).orElseThrow().getCreatedOn(), null,
                null);
        assertThat(eventServiceImpl.getByIdForUser(1L, 1L), is(equalTo(eventDto)));
    }
}