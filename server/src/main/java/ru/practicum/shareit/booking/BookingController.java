package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOut;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.shareit.common.Argument.HEADER_FOR_USER;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    @GetMapping
    public List<BookingOut> getBookings(@RequestParam(defaultValue = "0") Integer from,
                                        @RequestParam(defaultValue = "20") Integer size,
                                        @RequestHeader(HEADER_FOR_USER) Integer userId,
                                        @RequestParam(required = false) State state) {
        log.info("Вызван метод getBookings");
        return bookingService.getBookings(from, size, userId, state)
                .stream().map(BookingMapper::toBookingOut).collect(Collectors.toList());
    }

    @GetMapping("/owner")
    public List<BookingOut> getBookingsOwner(@RequestParam(defaultValue = "0") Integer from,
                                             @RequestParam(defaultValue = "20") Integer size,
                                             @RequestHeader(HEADER_FOR_USER) Integer userId,
                                             @RequestParam(required = false) State state) {
        log.info("Вызван метод getBookingsOwner");
        return bookingService.getBookingsOwner(from, size, userId, state)
                .stream().map(BookingMapper::toBookingOut).collect(Collectors.toList());
    }

    @GetMapping("/{bookingId}")
    public BookingOut getBooking(@PathVariable Integer bookingId,
                                 @RequestHeader(HEADER_FOR_USER) Integer userId) {
        log.info("Вызван метод getBooking");
        return BookingMapper.toBookingOut(bookingService.getBooking(bookingId, userId));
    }

    @PostMapping
    public BookingOut addBooking(@RequestBody BookingDto bookingDto,
                                 @RequestHeader(HEADER_FOR_USER) Integer userId) {
        log.info("Вызван метод addBooking");
        return BookingMapper.toBookingOut(bookingService.addBooking(bookingDto, userId));
    }

    @PatchMapping("/{bookingId}")
    public BookingOut approvedBooking(@PathVariable Integer bookingId,
                                      @RequestHeader(HEADER_FOR_USER) Integer userId,
                                      @RequestParam Boolean approved) {
        log.info("Вызван метод approvedBooking");
        return BookingMapper.toBookingOut(bookingService.approvedBooking(bookingId, userId, approved));
    }
}
