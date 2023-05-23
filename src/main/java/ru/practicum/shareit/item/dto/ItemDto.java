package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    Integer id;
    @NotBlank(message = "Item name cannot be empty.")
    String name;
    @NotBlank(message = "Item description cannot be empty.")
    @Size(max = 200, message = "Item description must be less than 200 characters.")
    String description;
    @NotNull(message = "Available is null")
    Boolean available;
    Integer ownerId;

    public ItemDto(String name, String description, Boolean isAvailable, Integer integer) {
    }
}
