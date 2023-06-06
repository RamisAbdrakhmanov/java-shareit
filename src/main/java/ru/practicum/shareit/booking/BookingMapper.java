package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.IBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class BookingMapper {

   /* public static BookingOut toBookingOut(Booking booking) {
        return new BookingOut(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getItem().getName(),
                booking.getStatus().toString(),
                booking.getBooker().getId()
        );
    }*/

    public static Booking toBooking(BookingDto bookingDto, Item item, User booker) {
        return new Booking(
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                booker,
                Status.WAITING
        );
    }

    public static IBooking toIBooking(Booking booking) {
        if (booking != null) {
            return new IBooking(
                    booking.getId(),
                    booking.getBooker().getId()
            );
        } else {
            return new IBooking();
        }
    }

}
