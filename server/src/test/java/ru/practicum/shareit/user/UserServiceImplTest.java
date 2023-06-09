package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.practicum.shareit.CreatorController.email;
import static ru.practicum.shareit.CreatorController.userName;
import static ru.practicum.shareit.CreatorService.*;

@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = {"ru.yandex.practicum.shareit"})
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> lists = userService.getUsers();

        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).getName(), userName);
        assertEquals(lists.get(0).getEmail(), email);
    }

    @Test
    void addUserTest() {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User user = userService.addUser(userDto);

        assertEquals(user.getName(), userName);
        assertEquals(user.getEmail(), email);
    }

    @Test
    void updateUserTest() {
        UserDto userDto1 = new UserDto(1, "upd", "upd@mail.ru");
        User user = new User(1, userDto.getName(), userDto.getEmail());

        when(userRepository.getReferenceById(any())).thenReturn(user);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User userUpd = userService.updateUser(userDto1);

        assertEquals(userUpd.getName(), userDto1.getName());
        assertEquals(userUpd.getEmail(), userDto1.getEmail());
    }

    @Test
    void updateUserWrongEmail() {
        UserDto userDto1 = new UserDto(1, "upd", "upd@mail.ru");
        User user = new User(1, userDto.getName(), userDto.getEmail());

        when(userRepository.getReferenceById(any())).thenReturn(user);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(booker));

        ConflictException validationException = assertThrows(ConflictException.class,
                () -> userService.updateUser(userDto1));

        assertNotNull(validationException.getMessage());
    }

    @Test
    void getUserTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        User getUser = userService.getUser(user.getId());

        Assertions.assertEquals(getUser.getName(), userName);
        Assertions.assertEquals(getUser.getEmail(), email);
    }

    @Test
    void deleteUserTest() {
        assertDoesNotThrow(() -> userService.deleteUser(user.getId()));
    }
}