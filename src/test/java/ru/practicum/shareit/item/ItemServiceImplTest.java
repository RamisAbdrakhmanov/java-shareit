package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.CreatorController.itemDescription;
import static ru.practicum.shareit.CreatorController.itemName;
import static ru.practicum.shareit.CreatorService.*;

@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = {"ru.yandex.practicum.shareit"})
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    private BookingRepository bookingRepository;
    @Mock
    private UserService mockUserService;
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
    }

    @Test
    void deleteItem() {
        assertDoesNotThrow(() -> itemService.deleteItem(1));
    }

    @Test
    void getItem() {
    }

    @Test
    void searchItems() {
    }

    @Test
    void getItemById() {
    }
}