package ru.practicum.shareit.user;

import java.util.List;

interface UserService {

    List<User> getUsers();

    User addUser(User user);

    User updateUser(User user);

    User getUser(Integer userId);

    void deleteUser(Integer userId);
}
