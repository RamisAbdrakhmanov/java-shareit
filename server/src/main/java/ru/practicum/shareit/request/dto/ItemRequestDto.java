package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemIQ;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ItemRequestDto {

    private Integer id;
    private String description;
    private Integer requesterId;
    private LocalDateTime created;
    private List<ItemIQ> items;
}
