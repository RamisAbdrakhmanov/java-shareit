package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Pageable> {

    List<ItemRequest> findAllByRequester(User requester);

    Optional<ItemRequest> findById(Integer itemRequestId);

    List<ItemRequest> findAllByRequesterIdIsNot(Integer requesterId, Pageable pageable);
}
