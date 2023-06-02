package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

interface UserService {

    List<UserDto> getUsers();

    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto getUser(Integer userId);

    void deleteUser(Integer userId);
}
