package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    @GetMapping
    public List<Booking> getBookings(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                     @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size,
                                     @RequestHeader("X-Sharer-User-Id") Integer userId,
                                     @RequestParam(required = false) String state) {
        int numbInPage = from % size;
        return bookingService.getBookings(from, size, userId, state)
                .stream()
                .skip(numbInPage)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public List<Booking> getBookingsOwner(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                          @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size,
                                          @RequestHeader("X-Sharer-User-Id") Integer userId,
                                          @RequestParam(required = false) String state) {
        int numbInPage = from % size;
        return bookingService.getBookingsOwner(from, size, userId, state).stream()
                .skip(numbInPage)
                .collect(Collectors.toList());
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
}
