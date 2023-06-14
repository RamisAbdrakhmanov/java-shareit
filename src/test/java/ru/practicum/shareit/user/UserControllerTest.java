package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.practicum.shareit.CreatorController.*;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"ru.yandex.practicum.shareit"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getUsersTest() throws Exception {
        Integer id = getUserId(mockMvc, userName, email);

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(userName))
                .andExpect(jsonPath("$[0].email").value(email));

    }

    @Test
    public void addUserTest() throws Exception {
        String userJson = createUserJson(userName, email);

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
        Integer userId = getUserId(mockMvc, userName, email);

        String update = createUserJson(userName2, email2);
        mockMvc.perform(patch("/users/{userId}", userId).contentType(MediaType.APPLICATION_JSON)
                        .content(update))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(userName2))
                .andExpect(jsonPath("$.email").value(email2));
    }

    @Test
    public void getUserTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);

        mockMvc.perform(get("/users/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(userName))
                .andExpect(jsonPath("$.email").value(email));

    }

    @Test
    public void getUserWrongIdTest() throws Exception {
        mockMvc.perform(get("/users/{userId}", 99))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Test
    public void deleteUserTest() throws Exception {

        Integer id = getUserId(mockMvc, userName, email);

        mockMvc.perform(delete("/users/{userId}", id))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/{userId}", id))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
}


