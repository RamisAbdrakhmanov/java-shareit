package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class ItemDto {

    private Integer id;
    @NotBlank(message = "Item name cannot be empty.")
    private String name;
    @NotBlank(message = "Item description cannot be empty.")
    @Size(max = 200, message = "Item description must be less than 200 characters.")
    private String description;
    @NotNull(message = "Available is null")
    private Boolean available;
    private Integer requestId;

}
