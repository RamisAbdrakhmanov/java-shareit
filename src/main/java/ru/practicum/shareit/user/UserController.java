package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@Slf4j
@RestController
@AllArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping(path = "/users")
public class UserController {
    private UserServiceImp userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PatchMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable Integer userId) {
        user.setId(userId);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }
}
