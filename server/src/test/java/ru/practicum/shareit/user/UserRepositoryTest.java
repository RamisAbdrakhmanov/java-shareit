package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.shareit.CreatorController.email;
import static ru.practicum.shareit.CreatorController.userName;
import static ru.practicum.shareit.CreatorService.user;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmailTest() {
        userRepository.save(user);
        Optional<User> check = userRepository.findByEmail(email);
        assertNotNull(check);
        assertEquals(check.get().getEmail(), email);
        assertEquals(check.get().getName(), userName);
    }
}