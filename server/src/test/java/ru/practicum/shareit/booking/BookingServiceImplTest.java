package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ElementException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.CreatorController.*;
import static ru.practicum.shareit.CreatorService.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = {"ru.yandex.practicum.shareit"})
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void getBookingTest() {
        when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(next));

        Booking booking = bookingService.getBooking(next.getId(), user.getId());
        assertEquals(booking, next);
    }

    @Test
    void addBookingTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));
        when(bookingRepository.save(any())).thenReturn(next);

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());

        assertEquals(booking.getItem(), item);
        assertEquals(booking.getStart(), start);
        assertEquals(booking.getEnd(), end);
        assertEquals(booking.getStatus(), Status.APPROVED);
    }

    @Test
    void approvedBookingWrongStatusTest() {
        when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(next));

        ElementException elementException = assertThrows(ElementException.class,
                () -> bookingService.approvedBooking(next.getId(), user.getId(), available));

        assertNotNull(elementException.getMessage());
    }

    @Test
    void approvedBookingWrongOwnerTest() {
        when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(next));

        next.setStatus(Status.WAITING);
        NotFoundException validationException = assertThrows(NotFoundException.class,
                () -> bookingService.approvedBooking(next.getId(), booker.getId(), available));
        next.setStatus(Status.APPROVED);

        assertNotNull(validationException.getMessage());

    }

    @Test
    void approvedBookingTest() {
        when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(next));
        when(bookingRepository.save(any())).thenReturn(next);

        next.setStatus(Status.WAITING);

        Booking booking = bookingService.approvedBooking(next.getId(), user.getId(), available);

        assertEquals(booking, next);
    }

    @Test
    void getBookingsTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));
        when(bookingRepository.findAllByBookerIdOrderByStartDesc(anyInt(), any())).thenReturn(List.of(last, next));

        List<Booking> list = bookingService.getBookings(0, 20, booker.getId(), State.ALL);

        assertEquals(list, List.of(last, next));

    }

    @Test
    void getBookingsOwnerTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));
        when(bookingRepository.findAllByItemOwnerIdOrderByStartDesc(anyInt(), any())).thenReturn(List.of(last, next));

        List<Booking> list = bookingService.getBookingsOwner(0, 20, user.getId(), State.ALL);

        assertEquals(list, List.of(last, next));
    }
}