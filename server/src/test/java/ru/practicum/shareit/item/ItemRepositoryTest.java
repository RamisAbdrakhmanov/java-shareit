package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.common.MyPageRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.shareit.CreatorService.*;

@DataJpaTest
class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRequestRepository itemRequestRepository;

    private final Pageable pageable = MyPageRequest.of(0, 20);

    @Test
    void findAllByOwnerIdTest() {
        Item itemGet = saveItem();
        List<Item> list = itemRepository.findAllByOwnerIdOrderByIdAsc(itemGet.getOwner().getId(), pageable);

        assertEquals(list.get(0), itemGet);
    }

    @Test
    void findItemByNameAndDescriptionTest() {
        Item itemGet = saveItem();

        List<Item> list = itemRepository.findItemByNameAndDescription(itemGet.getName().substring(2, 5).toUpperCase(),
                pageable);

        assertEquals(list.get(0), itemGet);
    }

    @Test
    void findAllByItemRequestIdTest() {
        Item itemGet = saveItem();
        ItemRequest itemRequestGet = saveItemRequest();
        itemGet.setItemRequest(itemRequestGet);
        itemRepository.save(itemGet);

        List<Item> list = itemRepository.findAllByItemRequestId(itemRequestGet.getId());

        assertEquals(list.get(0), itemGet);
    }

    private Item saveItem() {
        User userGet = userRepository.save(user);
        item.setOwner(userGet);
        Item itemNew = itemRepository.save(item);
        item.setOwner(user);
        return itemNew;
    }

    private ItemRequest saveItemRequest() {
        User bookerNew = userRepository.save(booker);
        itemRequest.setRequester(bookerNew);
        ItemRequest itemRequestGet = itemRequestRepository.save(itemRequest);
        itemRequest.setRequester(booker);
        return itemRequestRepository.save(itemRequestGet);
    }


}