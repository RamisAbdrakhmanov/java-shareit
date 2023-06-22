package ru.practicum.shareit.booking;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
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
public class BookingIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;

    @Test
    void getItemRequestsAllTest() {
        User userNew = userRepository.save(user);
        User requester = userRepository.save(booker);

        Item item = itemRepository.save(new Item(null, itemName, itemDescription, true, userNew, null));
        Booking booking = bookingRepository.save(
                new Booking(null,
                        LocalDateTime.now().minusDays(2),
                        LocalDateTime.now().minusDays(1),
                        item,
                        requester,
                        Status.APPROVED)
        );

        List<Booking> list = bookingService.getBookings(0, 20, requester.getId(), State.ALL);
        assertEquals(list.size(), 1);
        assertEquals(list.get(0).getItem(), item);
        assertEquals(list.get(0).getId(), booking.getId());
        assertEquals(list.get(0).getStatus(), booking.getStatus());
    }

}
