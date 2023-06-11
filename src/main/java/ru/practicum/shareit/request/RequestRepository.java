package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

interface RequestRepository extends JpaRepository<Request, Pageable> {

    List<Request> findAllByRequester(User requester);

    Optional<Request> findById(Integer itemRequestId);

    List<Request> findAllByRequesterIdIsNot(Integer requesterId, Pageable pageable);
}
