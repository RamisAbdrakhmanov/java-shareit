package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {

    Booking getBooking(Integer bookingId, Integer userId);

    Booking addBooking(BookingDto bookingDto, Integer userId);

    Booking approvedBooking(Integer bookingId, Integer userId, Boolean approved);

    List<Booking> getBookings(Integer from, Integer size, Integer userId, String state);

    List<Booking> getBookingsOwner(Integer from, Integer size, Integer userId, String state);
}
