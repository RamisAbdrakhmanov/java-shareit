package ru.practicum.shareit.request;

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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.CreatorController.createRequestJson;
import static ru.practicum.shareit.CreatorController.requestDescription;
import static ru.practicum.shareit.CreatorService.itemRequestDto;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemRequestControllerTest {

    private final String url = "/requests";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRequestService itemRequestService;

    @Test
    public void getItemRequestsAll() throws Exception {
        when(itemRequestService.getItemRequestsAll(anyInt(), anyInt(), anyInt())).thenReturn(List.of(itemRequestDto));

        mockMvc.perform(get(url + "/all")
                        .header("X-Sharer-User-Id", itemRequestDto.getRequesterId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(itemRequestDto.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestDto.getDescription()))
                .andExpect(jsonPath("$[0].requesterId").value(itemRequestDto.getRequesterId()))
                .andExpect(jsonPath("$[0].created").isNotEmpty());
    }

    @Test
    public void getItemRequestsForRequester() throws Exception {
        when(itemRequestService.getItemRequestsForRequester(anyInt())).thenReturn(List.of(itemRequestDto));

        mockMvc.perform(get(url)
                        .header("X-Sharer-User-Id", itemRequestDto.getRequesterId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(itemRequestDto.getId()))
                .andExpect(jsonPath("$[0].description").value(itemRequestDto.getDescription()))
                .andExpect(jsonPath("$[0].requesterId").value(itemRequestDto.getRequesterId()))
                .andExpect(jsonPath("$[0].created").isNotEmpty());
    }

    @Test
    public void getItemRequest() throws Exception {
        when(itemRequestService.getItemRequest(anyInt(), anyInt())).thenReturn(itemRequestDto);

        mockMvc.perform(get(url + "/{id}", itemRequestDto.getId())
                        .header("X-Sharer-User-Id", itemRequestDto.getRequesterId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestDto.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestDto.getDescription()))
                .andExpect(jsonPath("$.requesterId").value(itemRequestDto.getRequesterId()))
                .andExpect(jsonPath("$.created").isNotEmpty());
    }

    @Test
    public void addItemRequest() throws Exception {
        String requestJson = createRequestJson(requestDescription);

        when(itemRequestService.addItemRequest(any(), anyInt())).thenReturn(itemRequestDto);

        mockMvc.perform(post(url)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", itemRequestDto.getRequesterId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemRequestDto.getId()))
                .andExpect(jsonPath("$.description").value(itemRequestDto.getDescription()))
                .andExpect(jsonPath("$.requesterId").value(itemRequestDto.getRequesterId()))
                .andExpect(jsonPath("$.created").isNotEmpty());
    }

}
