package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByBookerIdOrderByStartDesc(Integer bookerId);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Integer bookerId, Status status);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Integer bookerId, LocalDateTime start);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Integer bookerId, LocalDateTime end);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartAsc(Integer bookerId, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Integer ownerId);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Integer ownerId, Status status);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Integer ownerId, LocalDateTime start);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Integer ownerId, LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Integer ownerId, LocalDateTime start, LocalDateTime end);

    Optional<Booking> findFirstByItemIdAndEndBeforeOrderByEndDesc(Integer itemId, LocalDateTime end);

    Optional<Booking> findFirstByItemIdAndStartAfterOrderByStartAsc(Integer itemId, LocalDateTime start);

    Booking findFirstByItemIdAndBookerIdAndEndBeforeOrderByEndDesc(Integer itemId,Integer bookerId, LocalDateTime time);

}
