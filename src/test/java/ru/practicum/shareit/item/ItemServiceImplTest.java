package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.dto.ItemOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.CreatorController.*;
import static ru.practicum.shareit.CreatorService.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = {"ru.yandex.practicum.shareit"})
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ItemServiceImpl itemService;


    @Test
    void addItem() {
        when(mockUserService.getUser(any())).thenReturn(user);
        when(itemRepository.save(any())).thenReturn(item);
        Item item = itemService.addItem(itemDto, 1);

        assertEquals(item.getName(), itemName);
        assertEquals(item.getDescription(), itemDescription);
        assertEquals(item.getOwner().getId(), 1);
    }

    @Test
    void updateItem() {
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));
        when(itemRepository.save(any())).thenReturn(itemUpd);

        Item newItem = itemService.updateItem(itemDtoUpd, 1);

        assertEquals(newItem.getName(), itemName2);
        assertEquals(newItem.getDescription(), itemDescription2);
        assertEquals(newItem.getOwner().getId(), 1);

        item.setName(itemName);
        item.setDescription(itemDescription);
    }

    @Test
    void deleteItem() {
        assertDoesNotThrow(() -> itemService.deleteItem(1));
    }

    @Test
    void getItem() {

        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));
        when(commentRepository.findAllByItemId(anyInt())).thenReturn(List.of(comment));
        when(bookingRepository.findFirstByItemIdAndStartBeforeAndStatusOrderByEndDesc(anyInt(), any(), any()))
                .thenReturn(Optional.of(last));
        when(bookingRepository.findFirstByItemIdAndStartAfterAndStatusOrderByStartAsc(anyInt(), any(), any()))
                .thenReturn(Optional.of(next));

        ItemOwner newItem = itemService.getItem(item.getId(), user.getId());

        System.out.println(item);
        assertEquals(newItem.getId(), item.getId());
        assertEquals(newItem.getNextBooking(), nextIt);
        assertEquals(newItem.getLastBooking(), lastIt);
        assertEquals(newItem.getName(), itemName);
        assertEquals(newItem.getDescription(), itemDescription);
        assertEquals(newItem.getAvailable(), available);
        assertEquals(newItem.getComments(), List.of(commentDto));

    }

    @Test
    void searchItems() {
        when(itemRepository.findItemByNameAndDescription(any(), any())).thenReturn(List.of(item));

        List<Item> list = itemService.searchItems("дрель", 0, 20);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0), item);
    }

    @Test
    void getItems() {
        when(itemRepository.findAllByOwnerId(anyInt(), any())).thenReturn(List.of(item));
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));
        when(commentRepository.findAllByItemId(anyInt())).thenReturn(List.of(comment));
        when(bookingRepository.findFirstByItemIdAndStartBeforeAndStatusOrderByEndDesc(anyInt(), any(), any()))
                .thenReturn(Optional.of(last));
        when(bookingRepository.findFirstByItemIdAndStartAfterAndStatusOrderByStartAsc(anyInt(), any(), any()))
                .thenReturn(Optional.of(next));
        List<ItemOwner> list = itemService.getItems(1, 0, 20);

        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getName(), itemName);
        assertEquals(list.get(0).getDescription(), itemDescription);
        assertEquals(list.get(0).getId(), 1);
    }
}