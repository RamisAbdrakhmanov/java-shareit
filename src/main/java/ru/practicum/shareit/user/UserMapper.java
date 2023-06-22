package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.dto.UserB;
import ru.practicum.shareit.user.dto.UserDto;

@UtilityClass
public class UserMapper {
    public User toUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public UserB toUserB(User user) {
        return new UserB(user.getId());
    }
}
