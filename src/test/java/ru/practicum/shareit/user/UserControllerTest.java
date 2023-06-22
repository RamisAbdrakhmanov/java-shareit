package ru.practicum.shareit.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.practicum.shareit.CreatorController.*;
import static ru.practicum.shareit.CreatorService.booker;
import static ru.practicum.shareit.CreatorService.user;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void getUsersTest() throws Exception {
        when(userService.getUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(user.getId()))
                .andExpect(jsonPath("$[0].name").value(userName))
                .andExpect(jsonPath("$[0].email").value(email));

    }

    @Test
    public void addUserTest() throws Exception {
        String userJson = createUserJson(userName, email);

        when(userService.addUser(any())).thenReturn(user);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value(userName))
                .andExpect(jsonPath("$.email").value(email));

    }

    @Test
    public void addUserEmptyNameTest() throws Exception {
        String userName = "";
        String email = "user@user.ru";
        String userJson = createUserJson(userName, email);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void addUserEmptyEmailTest() throws Exception {
        String userName = "user";
        String userJson = createUserJson(userName, null);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void addUserWrongEmailTest() throws Exception {
        String userName = "user";
        String email = "useruser.ru";
        String userJson = createUserJson(userName, email);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void updateUserTest() throws Exception {
        when(userService.updateUser(any())).thenReturn(booker);

        String update = createUserJson(userName2, email2);
        mockMvc.perform(patch("/users/{userId}", booker.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(update))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(booker.getId()))
                .andExpect(jsonPath("$.name").value(userName2))
                .andExpect(jsonPath("$.email").value(email2));
    }

    @Test
    public void getUserTest() throws Exception {
        when(userService.getUser(anyInt())).thenReturn(user);

        mockMvc.perform(get("/users/{userId}", user.getId()))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(userName))
                .andExpect(jsonPath("$.email").value(email));

    }


    @Test
    public void deleteUserTest() throws Exception {

        mockMvc.perform(delete("/users/{userId}", anyInt()))
                .andDo(print())
                .andExpect(status().isOk());

    }
}


