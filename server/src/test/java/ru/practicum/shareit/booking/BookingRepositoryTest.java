package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.common.MyPageRequest;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BookingRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

    private Pageable pageable = MyPageRequest.of(0, 20);

    @Test
    void findAllByBookerIdOrderByStartDescTest() {
        List<Booking> listAll = createBooking();

        List<Booking> listFind = bookingRepository.findAllByBookerIdOrderByStartDesc(
                listAll.get(3).getBooker().getId(), pageable);

        assertEquals(listFind.size(), 3);
        assertEquals(listFind.get(0), listAll.get(5));
        assertEquals(listFind.get(1), listAll.get(4));
        assertEquals(listFind.get(2), listAll.get(3));
    }

    @Test
    void findAllByBookerIdAndEndBeforeOrderByStartDescTest() {
        List<Booking> listAll = createBooking();

        List<Booking> listFind = bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(
                listAll.get(3).getBooker().getId(), LocalDateTime.now().plusDays(7), pageable);

        assertEquals(listFind.size(), 3);
        assertEquals(listFind.get(0), listAll.get(5));
        assertEquals(listFind.get(1), listAll.get(4));
        assertEquals(listFind.get(2), listAll.get(3));

    }

    @Test
    void findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartAscTest() {
        List<Booking> listAll = createBooking();

        List<Booking> listFind = bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartAsc(
                listAll.get(3).getBooker().getId(),
                LocalDateTime.now().plusDays(5).plusMinutes(15),
                LocalDateTime.now().plusDays(5).plusMinutes(15),
                pageable);

        assertEquals(listFind.size(), 1);
        assertEquals(listFind.get(0), listAll.get(4));
    }

    @Test
    void findAllByItemOwnerIdAndStatusApprovedOrderByStartDescTest() {
        List<Booking> listAll = createBooking();

        List<Booking> listFind = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(
                listAll.get(0).getItem().getOwner().getId(),
                Status.APPROVED,
                pageable);

        assertEquals(listFind.size(), 2);
        assertEquals(listFind.get(0), listAll.get(3));
        assertEquals(listFind.get(1), listAll.get(1));
    }

    @Test
    void findAllByItemOwnerIdAndStatusWaitingOrderByStartDescTest() {
        List<Booking> listAll = createBooking();

        List<Booking> listFind = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(
                listAll.get(0).getItem().getOwner().getId(),
                Status.WAITING,
                pageable);

        assertEquals(listFind.size(), 1);
        assertEquals(listFind.get(0), listAll.get(0));
    }

    @Test
    void findAllByItemOwnerIdAndStatusRejectedOrderByStartDescTest() {
        List<Booking> listAll = createBooking();

        List<Booking> listFind = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(
                listAll.get(0).getItem().getOwner().getId(),
                Status.REJECTED,
                pageable);

        assertEquals(listFind.size(), 1);
        assertEquals(listFind.get(0), listAll.get(4));
    }

    @Test
    void findAllByItemOwnerIdAndEndBeforeOrderByStartDescTest() {
        List<Booking> listAll = createBooking();

        List<Booking> listFind = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(
                listAll.get(0).getItem().getOwner().getId(),
                LocalDateTime.now().plusDays(4),
                pageable);

        assertEquals(listFind.size(), 2);
        assertEquals(listFind.get(0), listAll.get(1));
        assertEquals(listFind.get(1), listAll.get(0));
    }

    @Test
    void findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDescTest() {
        List<Booking> listAll = createBooking();

        List<Booking> listFind = bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(
                listAll.get(0).getItem().getOwner().getId(),
                LocalDateTime.now().plusDays(1).plusMinutes(15),
                LocalDateTime.now().plusDays(1).plusMinutes(15),
                pageable);

        assertEquals(listFind.size(), 1);
        assertEquals(listFind.get(0), listAll.get(0));
    }


    private List<Booking> createBooking() {
        User user1 = new User(null, "1", "1@ya.ru");
        User user2 = new User(null, "2", "2@ya.ru");
        User user3 = new User(null, "3", "3@ya.ru");
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);

        Item item1 = new Item(null, "1", "1", true, user1, null);
        Item item2 = new Item(null, "2", "2", true, user1, null);
        Item item3 = new Item(null, "3", "3", true, user2, null);
        item1 = itemRepository.save(item1);
        item2 = itemRepository.save(item2);
        item3 = itemRepository.save(item3);

        Booking booking1 = new Booking(
                null,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                item1,
                user2,
                Status.WAITING
        );
        Booking booking2 = new Booking(
                null,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3),
                item2,
                user2,
                Status.APPROVED
        );
        Booking booking3 = new Booking(
                null,
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(4),
                item3,
                user1,
                Status.WAITING
        );
        Booking booking4 = new Booking(
                null,
                LocalDateTime.now().plusDays(4),
                LocalDateTime.now().plusDays(5),
                item2,
                user3,
                Status.APPROVED
        );
        Booking booking5 = new Booking(
                null,
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(6),
                item1,
                user3,
                Status.REJECTED
        );
        Booking booking6 = new Booking(
                null,
                LocalDateTime.now().plusDays(6),
                LocalDateTime.now().plusDays(7),
                item3,
                user3,
                Status.WAITING
        );

        booking1 = bookingRepository.save(booking1);
        booking2 = bookingRepository.save(booking2);
        booking3 = bookingRepository.save(booking3);
        booking4 = bookingRepository.save(booking4);
        booking5 = bookingRepository.save(booking5);
        booking6 = bookingRepository.save(booking6);

        return List.of(booking1, booking2, booking3, booking4, booking5, booking6);


    }
}