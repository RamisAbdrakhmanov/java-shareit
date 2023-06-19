package ru.practicum.shareit.item;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.ItemOwner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.CreatorController.*;
import static ru.practicum.shareit.CreatorService.item;
import static ru.practicum.shareit.CreatorService.itemUpd;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    private final String url = "/items";

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addItemTest() throws Exception {
        String addItem = createItemDtoJson(itemName, itemDescription, available);
        when(itemService.addItem(any(), anyInt())).thenReturn(item);
        mockMvc.perform(post(url)
                        .header("X-Sharer-User-Id", item.getOwner().getId())
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
    public void updateItemTest() throws Exception {
        when(itemService.updateItem(any(), anyInt())).thenReturn(itemUpd);

        String itemUpdS = createItemDtoJson(itemName2, itemDescription2, availableUpd);

        mockMvc.perform(patch(url + "/{itemId}", itemUpd.getId())
                        .header("X-Sharer-User-Id", itemUpd.getOwner().getId())
                        .content(itemUpdS)
                        .contentType(MediaType.APPLICATION_JSON))

                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.name").value(itemName2))
                .andExpect(jsonPath("$.description").value(itemDescription2))
                .andExpect(jsonPath("$.available").value(available))
                .andExpect(jsonPath("$.requestId").isEmpty());

    }

    @Test
    public void deleteItemTest() throws Exception {
        mockMvc.perform(delete(url + "/{itemId}", 1)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getItemsTest() throws Exception {
        when(itemService.getItems(anyInt(), anyInt(), anyInt()))
                .thenReturn(List.of(new ItemOwner(1, itemName, itemDescription,
                        true, null, null, new ArrayList<>())));
        mockMvc.perform(get(url)
                        .header("X-Sharer-User-Id", 1
                        )
                        .param("from", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(itemName))
                .andExpect(jsonPath("$[0].description").value(itemDescription));

    }

    @Test
    public void getSearchItemsTest() throws Exception {
        when(itemService.searchItems(anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(item, itemUpd));
        mockMvc.perform(get(url + "/search")
                        .header("X-Sharer-User-Id", 1)
                        .param("text", "дРель")
                        .param("from", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].name").value(itemName))
                .andExpect(jsonPath("$[0].description").value(itemDescription))
                .andExpect(jsonPath("$[1].id").value(itemUpd.getId()))
                .andExpect(jsonPath("$[1].name").value(itemName2))
                .andExpect(jsonPath("$[1].description").value(itemDescription2));
    }

}
