package ru.practicum.ewm.service.category;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.ewm.exception.CategoryCantBeDeleted;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.model.category.CategoryDto;
import ru.practicum.ewm.model.category.NewCategoryDto;
import ru.practicum.ewm.model.event.NewEventDto;
import ru.practicum.ewm.model.location.Location;
import ru.practicum.ewm.model.users.NewUserDto;
import ru.practicum.ewm.service.event.EventServiceImpl;
import ru.practicum.ewm.service.users.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryServiceImplTest {
    private final CategoryServiceImpl categoryServiceImpl;
    private final EventServiceImpl eventServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Test
    public void checkCategoryCreatedSuccess() {
        NewCategoryDto newCategoryDto = new NewCategoryDto("Музеи");
        CategoryDto categoryDto = new CategoryDto(1L, "Музеи");
        categoryServiceImpl.create(newCategoryDto);
        assertThat(categoryServiceImpl.getById(1L), is(equalTo(categoryDto)));
    }

    @Test
    public void checkCategoryUpdatedSuccess() {
        NewCategoryDto newCategoryDto = new NewCategoryDto("Музеи");
        categoryServiceImpl.create(newCategoryDto);
        CategoryDto categoryDto = new CategoryDto(1L, "Театр");
        categoryServiceImpl.update(categoryDto);
        assertThat(categoryServiceImpl.getById(1L), is(equalTo(categoryDto)));
    }

    @Test
    public void checkCategoryDeletedSuccess() {
        NewCategoryDto newCategoryDto = new NewCategoryDto("Музеи");
        categoryServiceImpl.create(newCategoryDto);
        CategoryDto categoryDto = new CategoryDto(1L, "Музеи");
        categoryServiceImpl.deleteById(categoryDto.getId());
        Exception exception = assertThrows(CategoryNotFoundException.class, () ->
                categoryServiceImpl.getById(1L));
        String expectedMessage = "There is no category with id = 1";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void checkCategoryGetByIdSuccess() {
        NewCategoryDto newCategoryDto = new NewCategoryDto("Музеи");
        categoryServiceImpl.create(newCategoryDto);
        CategoryDto categoryDto = new CategoryDto(1L, "Музеи");
        assertThat(categoryServiceImpl.getById(1L), is(equalTo(categoryDto)));
    }

    @Test
    public void checkCategoryGetAllSuccess() {
        NewCategoryDto newCategoryDto1 = new NewCategoryDto("Музеи");
        categoryServiceImpl.create(newCategoryDto1);
        NewCategoryDto newCategoryDto2 = new NewCategoryDto("Выставки");
        categoryServiceImpl.create(newCategoryDto2);
        CategoryDto categoryDto1 = new CategoryDto(1L, "Музеи");
        CategoryDto categoryDto2 = new CategoryDto(2L, "Выставки");
        List<CategoryDto> listToCompare = new ArrayList<>();
        listToCompare.add(categoryDto1);
        listToCompare.add(categoryDto2);
        assertThat(categoryServiceImpl.getAll(0, 5), is(equalTo(listToCompare)));
    }

    @Test
    public void checkCategoryWithLinkedEventsCantBeDeleted() {
        NewCategoryDto newCategoryDto1 = new NewCategoryDto("Музеи");
        categoryServiceImpl.create(newCategoryDto1);
        Location location = new Location(1L, 12.34, 34.78);
        NewEventDto newEventDto = new NewEventDto("Title", "Annotation", "Description",
                location, LocalDateTime.now().plusDays(2), 1L, true, 10L,
                true);
        NewUserDto newUserDto = new NewUserDto("Name", "email@gmail.com");
        userServiceImpl.create(newUserDto);
        eventServiceImpl.create(newEventDto, 1L);
        Exception exception = assertThrows(CategoryCantBeDeleted.class, () ->
                categoryServiceImpl.deleteById(1L));
        String expectedMessage = "There are linked events to this category";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}