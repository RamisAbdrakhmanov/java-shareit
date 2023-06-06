package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByBookerIdOrderByStartDesc(Integer bookerId);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Integer bookerId, Status status);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Integer bookerId, LocalDateTime start);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Integer bookerId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(Integer bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Integer ownerId);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Integer ownerId, Status status);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Integer ownerId, LocalDateTime start);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Integer ownerId, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Integer ownerId, LocalDateTime start, LocalDateTime end);

    Booking findFirstByItemIdAndEndBeforeOrderByEndDesc(Integer itemId, LocalDateTime end);

    Booking findFirstByItemIdAndStartAfterOrderByStartAsc(Integer itemId, LocalDateTime start);

}
