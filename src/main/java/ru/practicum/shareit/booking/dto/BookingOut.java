package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.dto.ItemB;
import ru.practicum.shareit.user.dto.UserB;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingOut {
    private Integer id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Status status;
    private UserB booker;
    private ItemB item;
}
