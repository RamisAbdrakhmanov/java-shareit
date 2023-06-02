package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

public interface BookingService {

    Booking getBooking(Integer bookingId, Integer userId);
    Booking addBooking(BookingDto bookingDto, Integer userId);
    Booking approvedBooking(Integer bookingId, Integer userId, Boolean approved);

}
