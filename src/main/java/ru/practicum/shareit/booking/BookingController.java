package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

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
    public List<BookingDto> getBookings() {
        return null;
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBooking(@PathVariable Integer bookingId,
                              @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return null;
    }

    @PostMapping
    public BookingDto addBooking(@RequestBody BookingDto bookingDto,
                              @RequestHeader("X-Sharer-User-Id") Integer userId) {
        return BookingMapper.toBookingDto(bookingService.addBooking(bookingDto,userId));
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approvedBooking(@PathVariable Integer bookingId,
                                   @RequestHeader("X-Sharer-User-Id") Integer userId,
                                   @RequestParam Boolean approved) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable String id) {

    }
}
