package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ru.practicum.shareit.CreatorService.user;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getUsersIntegrationTest() {
        User userNew = userRepository.save(user);
        List<User> list = userService.getUsers();

        Assertions.assertEquals(list.size(), 1);
        Assertions.assertEquals(userNew, list.get(0));
    }

}
