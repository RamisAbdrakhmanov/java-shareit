package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingIt;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.Optional;

public class BookingMapper {

    public static Booking toBooking(BookingDto bookingDto, Item item, User booker) {
        return new Booking(
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                booker,
                Status.WAITING
        );
    }

    public static BookingIt toBookingIt(Optional<Booking> booking) {
        return booking.map(value -> new BookingIt(
                value.getId(),
                value.getBooker().getId()
        )).orElse(null);
    }

}
