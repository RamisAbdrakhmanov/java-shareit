package ru.practicum.shareit.booking;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingIt;
import ru.practicum.shareit.booking.dto.BookingOut;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;

import java.util.Optional;

@UtilityClass
public class BookingMapper {

    public Booking toBooking(BookingDto bookingDto, Item item, User booker) {
        return new Booking(
                null,
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                booker,
                Status.WAITING
        );
    }

    public BookingIt toBookingIt(Optional<Booking> booking) {
        return booking.map(value -> new BookingIt(
                value.getId(),
                value.getBooker().getId()
        )).orElse(null);
    }

    public BookingOut toBookingOut(Booking booking) {
        return new BookingOut(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getStatus(),
                UserMapper.toUserB(booking.getBooker()),
                ItemMapper.toItemB(booking.getItem())
        );
    }

}
