package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Pageable> {

    Optional<Booking> findById(Integer bookerId);

    List<Booking> findAllByBookerIdOrderByStartDesc(Integer bookerId, Pageable pageable);

    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Integer bookerId, Status status, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Integer bookerId,
                                                                 LocalDateTime start,
                                                                 Pageable pageable);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Integer bookerId, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartAsc(Integer bookerId,
                                                                            LocalDateTime start,
                                                                            LocalDateTime end,
                                                                            Pageable pageable);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Integer ownerId, Pageable pageable);

    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Integer ownerId, Status status, Pageable pageable);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Integer ownerId,
                                                                    LocalDateTime start,
                                                                    Pageable pageable);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Integer ownerId,
                                                                   LocalDateTime end,
                                                                   Pageable pageable);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(Integer ownerId,
                                                                                LocalDateTime start,
                                                                                LocalDateTime end, Pageable pageable);

    Optional<Booking> findFirstByItemIdAndStartBeforeAndStatusOrderByEndDesc(Integer itemId,
                                                                             LocalDateTime now,
                                                                             Status status);

    Optional<Booking> findFirstByItemIdAndStartAfterAndStatusOrderByStartAsc(Integer itemId,
                                                                             LocalDateTime now,
                                                                             Status status);

    Booking findFirstByItemIdAndBookerIdAndEndBeforeOrderByEndDesc(Integer itemId,
                                                                   Integer bookerId,
                                                                   LocalDateTime time);

}
