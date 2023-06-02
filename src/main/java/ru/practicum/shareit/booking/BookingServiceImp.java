package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

@Service
@AllArgsConstructor
public class BookingServiceImp implements BookingService {

    private BookingRepository bookingRepository;
    private UserService userService;
    private ItemService itemService;

    @Override
    public Booking getBooking(Integer bookingId, Integer userId) {
        return null;
    }

    @Override
    public Booking addBooking(BookingDto bookingDto, Integer userId) {
        Item item = itemService.getItem(bookingDto.getItemId());
        User booker = userService.getUser(userId);
        Booking booking = BookingMapper.toBooking(bookingDto, item, booker);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking approvedBooking(Integer bookingId, Integer userId, Boolean approved) {
        return null;
    }
}
