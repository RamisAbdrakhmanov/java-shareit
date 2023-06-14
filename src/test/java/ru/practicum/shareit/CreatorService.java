package ru.practicum.shareit;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import static ru.practicum.shareit.CreatorController.*;

public class CreatorService {
    public static final UserDto userDto = new UserDto(null, userName, email);
    public static final User user = new User(1, userName, email);
    public static final ItemDto itemDto = new ItemDto(null, itemName, itemDescription, available, null);
    public static final Item item = new Item(1, itemName, itemDescription, available, user, null);

}
