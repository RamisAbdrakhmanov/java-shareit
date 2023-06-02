package ru.practicum.shareit.item;


import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item addItem(ItemDto itemDto, Integer userId);

    Item updateItem(ItemDto itemDto, Integer userId);

    void deleteItem(Integer itemId);

    Item getItem(Integer itemId);

    List<Item> getItems(Integer userId);

    List<Item> searchItems(String substring);
}
