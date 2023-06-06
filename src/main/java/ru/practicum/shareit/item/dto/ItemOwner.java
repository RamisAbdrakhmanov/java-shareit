package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.IBooking;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemOwner {

    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private IBooking lastBooking;
    private IBooking nextBooking;

}

