package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImp implements BookingService {

    private BookingRepository bookingRepository;
    private UserService userService;
    private ItemService itemService;

    @Override
    public Booking getBooking(Integer bookingId, Integer userId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            if (!(Objects.equals(booking.get().getItem().getOwner().getId(), userId)
                    ||
                    Objects.equals(booking.get().getBooker().getId(), userId))) {
                throw new NotFoundException("User cannot look at this booking");
            }
        } else {
            throw new NotFoundException("Booking doesn't exist");
        }
        return booking.get();
    }

    @Override
    public Booking addBooking(BookingDto bookingDto, Integer userId) {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().equals(bookingDto.getEnd())) {
            throw new ValidationException("Start booking is after end");
        }
        Item item = itemService.getItemById(bookingDto.getItemId());
        if (!item.getAvailable()) {
            throw new ValidationException("Item is available false");
        }
        if (Objects.equals(item.getOwner().getId(), userId)) {
            throw new NotFoundException("User own this item");
        }
        User booker = userService.getUser(userId);
        Booking booking = BookingMapper.toBooking(bookingDto, item, booker);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking approvedBooking(Integer bookingId, Integer userId, Boolean approved) {
        Booking booking = getBooking(bookingId, userId);
        if (!booking.getStatus().equals(Status.WAITING)) {
            throw new ValidationException("Booking approved before");
        }
        if (!Objects.equals(booking.getItem().getOwner().getId(), userId)) {
            throw new NotFoundException("Booking have another owner");
        }
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookings(Integer userId, String state) {
        userService.getUser(userId);

        if (state == null) {
            return bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
        }

        LocalDateTime now;
        switch (state) {
            case "WAITING":
            case "REJECTED":
                Status status = Status.valueOf(state);
                return bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, status);
            case "PAST":
                now = LocalDateTime.now();
                return bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId, now);
            case "FUTURE":
                now = LocalDateTime.now();
                return bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId, now);
            case "CURRENT":
                now = LocalDateTime.now();
                return bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now);
            case "ALL":
                return bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
            default:
                throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    @Override
    public List<Booking> getBookingsOwner(Integer userId, String state) {
        userService.getUser(userId);

        if (state == null) {
            return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId);
        }

        LocalDateTime now;
        switch (state) {
            case "WAITING":
            case "REJECTED":
                Status status = Status.valueOf(state);
                return bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, status);
            case "PAST":
                now = LocalDateTime.now();
                return bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(userId, now);
            case "FUTURE":
                now = LocalDateTime.now();
                return bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(userId, now);
            case "CURRENT":
                now = LocalDateTime.now();
                return bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, now, now);
            case "ALL":
                return bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId);
            default:
                throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
    }
}
