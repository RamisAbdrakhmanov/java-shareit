package ru.practicum.shareit.item;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.Creator.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"ru.yandex.practicum.shareit"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemControllerTest {

    private final String url = "/items";


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addItemTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);

        String addItem = createItemDtoJson(itemName, itemDescription, available);
        mockMvc.perform(post(url)
                        .header("X-Sharer-User-Id", userId)
                        .content(addItem)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name").value(itemName))
                .andExpect(jsonPath("$.description").value(itemDescription))
                .andExpect(jsonPath("$.available").value(available))
                .andExpect(jsonPath("$.requestId").isEmpty());
    }

    @Test
    public void getItemTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId = getItemId(mockMvc, userId, itemName, itemDescription, available);


        mockMvc.perform(get(url + "/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name").value(itemName))
                .andExpect(jsonPath("$.description").value(itemDescription))
                .andExpect(jsonPath("$.available").value(available))
                .andExpect(jsonPath("$.lastBooking").isEmpty())
                .andExpect(jsonPath("$.nextBooking").isEmpty())
                .andExpect(jsonPath("$.comments", hasSize(0)));
    }

    @Test
    public void updateItemTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId = getItemId(mockMvc, userId, itemName, itemDescription, available);


        String itemUpd = createItemDtoJson(itemName2, itemDescription2, availableUpd);

        mockMvc.perform(patch(url + "/{itemId}", itemId)
                        .header("X-Sharer-User-Id", userId)
                        .content(itemUpd)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name").value(itemName2))
                .andExpect(jsonPath("$.description").value(itemDescription2))
                .andExpect(jsonPath("$.available").value(availableUpd))
                .andExpect(jsonPath("$.requestId").isEmpty());

    }

    @Test
    public void deleteItemTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId = getItemId(mockMvc, userId, itemName, itemDescription, available);

        mockMvc.perform(delete(url + "/{itemId}", itemId)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getItemsTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId1 = getItemId(mockMvc, userId, itemName, itemDescription, available);

        Integer itemId2 = getItemId(mockMvc, userId, itemName2, itemDescription2, availableUpd);

        mockMvc.perform(get(url)
                        .header("X-Sharer-User-Id", userId)
                        .param("from", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id").value(itemId2))
                .andExpect(jsonPath("$[0].id").value(itemId1))
                .andExpect(jsonPath("$[1].name").value(itemName2))
                .andExpect(jsonPath("$[0].name").value(itemName))
                .andExpect(jsonPath("$[1].description").value(itemDescription2))
                .andExpect(jsonPath("$[0].description").value(itemDescription));

    }

    @Test
    public void getSearchItemsTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId1 = getItemId(mockMvc, userId, itemName, itemDescription, available);

        Integer itemId2 = getItemId(mockMvc, userId, itemName2, itemDescription2, available);

        mockMvc.perform(get(url + "/search")
                        .header("X-Sharer-User-Id", userId)
                        .param("text", "дРель")
                        .param("from", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(itemId1))
                .andExpect(jsonPath("$[0].name").value(itemName))
                .andExpect(jsonPath("$[0].description").value(itemDescription))
                .andExpect(jsonPath("$[1].id").value(itemId2))
                .andExpect(jsonPath("$[1].name").value(itemName2))
                .andExpect(jsonPath("$[1].description").value(itemDescription2));
    }


}
