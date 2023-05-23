package ru.practicum.shareit.user;


import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UserValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserDao {
    private final List<User> users = new ArrayList<>();
    private Integer countId = 1;

    public List<User> getUsers() {
        return users;
    }

    public User addUser(User user) {
        for (User us : users) {
            if (us.getEmail().equals(user.getEmail())) {
                throw new UserValidException("email is busy");
            }
        }
        user.setId(countId++);
        users.add(user);
        return user;
    }

    public User updateUser(User user) {
        User updateUser = null;
        for (User us : users) {
            if (us.getEmail().equals(user.getEmail()) && !(us.getId().equals(user.getId()))) {
                throw new UserValidException("email is busy");
            }
            if(us.getId().equals(user.getId())){
                updateUser = us;
            }
        }
        assert updateUser != null;
        if(user.getEmail() != null){
            updateUser.setEmail(user.getEmail());
        }
        if(user.getName() != null){
            updateUser.setName(user.getName());
        }
        return updateUser;
    }

    public User getUser(Integer userId) {
        User user = users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);
        if(user == null){
            throw new NotFoundException("User is not found");
        }
        return user;
    }

    public void deleteUser(Integer userId) {
        User user = getUser(userId);
        users.remove(user);
    }

}
