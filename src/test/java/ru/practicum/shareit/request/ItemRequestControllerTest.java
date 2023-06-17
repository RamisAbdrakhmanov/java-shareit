package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.CreatorController.*;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestControllerTest {

    private final String url = "/requests";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getItemRequestsAll() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer requestId = getRequestId(mockMvc, userId, requestDescription);

        mockMvc.perform(get(url)
                        .header("X-Sharer-User-Id", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(requestId))
                .andExpect(jsonPath("$[0].description").value(requestDescription))
                .andExpect(jsonPath("$[0].requesterId").value(userId))
                .andExpect(jsonPath("$[0].created").isNotEmpty());
    }

    @Test
    public void getItemRequestsForRequester() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer requestId = getRequestId(mockMvc, userId, requestDescription);

        mockMvc.perform(get(url)
                        .header("X-Sharer-User-Id", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(requestId))
                .andExpect(jsonPath("$[0].description").value(requestDescription))
                .andExpect(jsonPath("$[0].requesterId").value(userId))
                .andExpect(jsonPath("$[0].created").isNotEmpty());
    }

    @Test
    public void getItemRequest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer requestId = getRequestId(mockMvc, userId, requestDescription);

        mockMvc.perform(get(url + "/{requestId}", requestId)
                        .header("X-Sharer-User-Id", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(requestId))
                .andExpect(jsonPath("$.description").value(requestDescription))
                .andExpect(jsonPath("$.requesterId").value(userId))
                .andExpect(jsonPath("$.created").isNotEmpty());
    }

    @Test
    public void addItemRequest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        String requestJson = createRequestJson(requestDescription);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header("X-Sharer-User-Id", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(requestDescription))
                .andExpect(jsonPath("$.requesterId").value(userId))
                .andExpect(jsonPath("$.created").isNotEmpty());
    }
}
