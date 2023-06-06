package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    @GetMapping
    public List<Booking> getBookings(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                     @RequestParam(required = false) String state) {
        return bookingService.getBookings(userId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getBookingsOwner(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                          @RequestParam(required = false) String state) {
        return bookingService.getBookingsOwner(userId, state);
    }

    @GetMapping("/{bookingId}")
    public Booking getBooking(@PathVariable Integer bookingId,
                              @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return bookingService.getBooking(bookingId, userId);
    }

    @PostMapping
    public Booking addBooking(@Valid @RequestBody(required = false) BookingDto bookingDto,
                              @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return bookingService.addBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public Booking approvedBooking(@PathVariable Integer bookingId,
                                   @RequestHeader("X-Sharer-User-Id") Integer userId,
                                   @RequestParam Boolean approved) {
        return bookingService.approvedBooking(bookingId, userId, approved);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable String id) {

    }
}
