package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-controllers.
 */

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        log.info("Вызван метод getUsers");
        return userService.getUsers().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Integer userId) {
        log.info("Вызван метод getUser");
        return UserMapper.toUserDto(userService.getUser(userId));
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Вызван метод addUser");
        return UserMapper.toUserDto(userService.addUser(userDto));
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {
        log.info("Вызван метод updateUser");
        userDto.setId(userId);
        return UserMapper.toUserDto(userService.updateUser(userDto));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        log.info("Вызван метод deleteUser");
        userService.deleteUser(userId);
    }
}
