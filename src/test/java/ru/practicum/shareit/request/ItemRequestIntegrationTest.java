package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.shareit.CreatorController.itemDescription;
import static ru.practicum.shareit.CreatorController.itemName;
import static ru.practicum.shareit.CreatorService.booker;
import static ru.practicum.shareit.CreatorService.user;


@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class ItemRequestIntegrationTest {

    @Autowired
    private ItemRequestRepository itemRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemRequestService itemRequestService;

    @Test
    void getItemRequestsAllTest() {
        User userNew = userRepository.save(user);
        User requester = userRepository.save(booker);
        ItemRequest itemRequest = itemRequestRepository
                .save(new ItemRequest(null, "описание", requester, LocalDateTime.now()));

        itemRepository.save(new Item(null, itemName, itemDescription, true, userNew, itemRequest));

        List<ItemRequestDto> list = itemRequestService.getItemRequestsAll(0, 20, userNew.getId());

        assertEquals(list.size(), 1);
    }
}
