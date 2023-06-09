package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemIQ {

    private Integer id;
    private String name;
    private Integer ownerId;
    private String description;
    private Boolean available;
    private Integer requestId;
}
