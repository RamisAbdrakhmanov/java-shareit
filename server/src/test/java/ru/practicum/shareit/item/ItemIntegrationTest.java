package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.shareit.CreatorController.itemDescription;
import static ru.practicum.shareit.CreatorController.itemName;
import static ru.practicum.shareit.CreatorService.user;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class ItemIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;

    @Test
    void getItemRequestsAllTest() {
        User userNew = userRepository.save(user);
        Item item = itemRepository.save(new Item(null, itemName, itemDescription, true, userNew, null));
        List<ItemOwner> list = itemService.getItems(userNew.getId(), 0, 20);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getId(), item.getId());
        assertEquals(list.get(0).getName(), item.getName());
        assertEquals(list.get(0).getAvailable(), item.getAvailable());
        assertEquals(list.get(0).getDescription(), item.getDescription());
        assertEquals(list.get(0).getComments().size(), 0);
    }
}
