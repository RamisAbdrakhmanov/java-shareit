package ru.practicum.shareit.item.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.shareit.CreatorController.itemDescription;
import static ru.practicum.shareit.CreatorController.itemName;
import static ru.practicum.shareit.CreatorService.*;

@Transactional
@SpringBootTest
@AutoConfigureTestDatabase
public class CommentIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CommentService commentService;


    @Test
    public void addCommentTest() {
        User userNew = userRepository.save(user);
        User requester = userRepository.save(booker);

        Item item = itemRepository.save(new Item(null, itemName, itemDescription, true, userNew, null));
        bookingRepository.save(new Booking(null, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1),
                        item, requester, Status.APPROVED));

        Comment comment = commentService.addComment(item.getId(), commentDto, requester.getId());

        assertNotNull(comment);
        assertEquals(comment.getAuthor(), requester);
        assertEquals(comment.getItem(), item);
    }
}
