package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User addUser(UserDto userDto);

    User updateUser(UserDto userDto);

    User getUser(Integer userId);

    void deleteUser(Integer userId);
}
