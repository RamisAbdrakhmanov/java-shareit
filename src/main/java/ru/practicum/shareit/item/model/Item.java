package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

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
public class Item {

    Integer id;
    @NotBlank(message = "Item name cannot be empty.")
    String name;
    @NotBlank(message = "Item description cannot be empty.")
    @Size(max = 200, message = "Item description must be less than 200 characters.")
    String description;
    @NotNull(message = "available is null")
    Boolean isAvailable;
    User owner;
    ItemRequest request;

}
