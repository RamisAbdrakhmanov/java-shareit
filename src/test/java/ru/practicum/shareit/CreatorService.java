package ru.practicum.shareit;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingIt;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static ru.practicum.shareit.CreatorController.*;

@UtilityClass
public class CreatorService {
    public final UserDto userDto = new UserDto(null, userName, email);
    public final User user = new User(1, userName, email);
    public final User booker = new User(2, userName2, email2);
    public final ItemDto itemDto = new ItemDto(null, itemName, itemDescription, available, null);
    public final ItemDto itemDtoUpd = new ItemDto(1, itemName2, itemDescription2, available, null);
    public final Item item = new Item(1, itemName, itemDescription, available, user, null);
    public final Item itemUpd = new Item(1, itemName2, itemDescription2, available, user, null);
    public final Comment comment = new Comment(1, text, item, booker, created);
    public final CommentDto commentDto = new CommentDto(1, text, userName2, created);
    public final Booking last = new Booking(1,
            LocalDateTime.now().minusDays(2),
            LocalDateTime.now().minusDays(1),
            item,
            booker,
            Status.APPROVED);
    public final Booking next = new Booking(1,
            start,
            end,
            item,
            booker,
            Status.APPROVED);
    public final BookingIt lastIt = BookingMapper.toBookingIt(Optional.of(last));
    public final BookingIt nextIt = BookingMapper.toBookingIt(Optional.of(next));
    public final BookingDto bookingDto = new BookingDto(start, end, item.getId());
    public final ItemRequest itemRequest = new ItemRequest(1, itemDescription, booker, LocalDateTime.now());
    public final ItemRequestDto itemRequestDto = ItemRequestMapper.toItemRequestDto(itemRequest, new ArrayList<>());
}
