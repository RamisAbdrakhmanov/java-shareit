package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.common.MyPageRequest;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.shareit.CreatorService.*;

@DataJpaTest
class ItemRequestRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRequestRepository itemRequestRepository;

    private Pageable pageable = MyPageRequest.of(0, 20);


    @Test
    void findAllByRequesterTest() {
        Item itemGet = saveItem();
        ItemRequest itemRequestGet = saveItemRequest();
        itemGet.setItemRequest(itemRequestGet);
        itemRepository.save(itemGet);

        List<ItemRequest> list = itemRequestRepository.findAllByRequester(itemRequestGet.getRequester());

        assertEquals(list.get(0), itemRequestGet);
    }


    @Test
    void findAllByRequesterIdIsNotTest() {
        Item itemGet = saveItem();
        ItemRequest itemRequestGet = saveItemRequest();
        itemGet.setItemRequest(itemRequestGet);
        itemRepository.save(itemGet);

        List<ItemRequest> list = new ArrayList<>();
        list = itemRequestRepository.findAllByRequesterIdIsNot(itemRequestGet.getRequester().getId(),
                pageable);

        assertEquals(list.size(), 0);
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