package ru.practicum.shareit.booking;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.IBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.Optional;

@Slf4j
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

    public static IBooking toIBooking(Optional<Booking> booking) {
        return booking.map(value -> new IBooking(
                value.getId(),
                value.getBooker().getId()
        )).orElse(null);

    }

}
