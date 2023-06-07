package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UserValidException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public User addUser(UserDto userDto) {
        return repository.save(UserMapper.toUser(userDto));
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = repository.getReferenceById(userDto.getId());
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            Optional<User> checkUser = repository.findByEmail(userDto.getEmail());
            if (checkUser.isPresent()) {
                if (!Objects.equals(checkUser.get().getId(), userDto.getId())) {
                    throw new UserValidException("email is busy");
                }
            }
            user.setEmail(userDto.getEmail());
        }
        repository.save(user);
        return user;
    }

    @Override
    public User getUser(Integer userId) {
        Optional<User> user = repository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public void deleteUser(Integer userId) {
        repository.deleteById(userId);
    }
}
