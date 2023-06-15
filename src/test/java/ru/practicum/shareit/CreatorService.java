package ru.practicum.shareit;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingIt;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.practicum.shareit.CreatorController.*;

public class CreatorService {
    public static final UserDto userDto = new UserDto(null, userName, email);
    public static final User user = new User(1, userName, email);
    public static final User booker = new User(1, userName2, email2);
    public static final ItemDto itemDto = new ItemDto(null, itemName, itemDescription, available, null);
    public static final ItemDto itemDtoUpd = new ItemDto(1, itemName2, itemDescription2, available, null);
    public static final Item item = new Item(1, itemName, itemDescription, available, user, null);
    public static final Item itemUpd = new Item(1, itemName2, itemDescription2, available, user, null);
    public static final Comment comment = new Comment(1, text, item, booker, created);
    public static final CommentDto commentDto = new CommentDto(1, text, userName2, created);
    public static final Booking last = new Booking(1,
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now().minusDays(1),
            item,
            booker,
            Status.APPROVED);
    public static final Booking next = new Booking(1,
            LocalDateTime.now().plusDays(2),
            LocalDateTime.now().plusDays(1),
            item,
            booker,
            Status.APPROVED);
    public static final BookingIt lastIt = BookingMapper.toBookingIt(Optional.of(last));
    public static final BookingIt nextIt = BookingMapper.toBookingIt(Optional.of(next));
}
