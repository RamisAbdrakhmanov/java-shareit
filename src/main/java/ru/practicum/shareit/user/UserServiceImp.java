package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserDao userDao;

    @Override
    public List<UserDto> getUsers() {
        return userDao.getUsers().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        return UserMapper.toUserDto(userDao.addUser(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return UserMapper.toUserDto(userDao.updateUser(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto getUser(Integer userId) {
        return UserMapper.toUserDto(userDao.getUser(userId));
    }

    @Override
    public void deleteUser(Integer userId) {
        userDao.deleteUser(userId);
    }
}
