package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    @GetMapping
    public List<Booking> getBookings() {
        return null;
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public Booking addBooking() {
        return null;
    }

    @PatchMapping
    public Booking updateBooking() {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable String id) {

    }
}
