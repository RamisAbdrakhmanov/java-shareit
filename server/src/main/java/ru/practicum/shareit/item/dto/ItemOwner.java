package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingIt;
import ru.practicum.shareit.item.comment.dto.CommentDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemOwner {

    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private BookingIt lastBooking;
    private BookingIt nextBooking;
    private List<CommentDto> comments;

}

