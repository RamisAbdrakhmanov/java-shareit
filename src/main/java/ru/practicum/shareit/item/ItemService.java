package ru.practicum.shareit.item;


import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwner;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item addItem(ItemDto itemDto, Integer userId);

    Item updateItem(ItemDto itemDto, Integer userId);

    void deleteItem(Integer itemId);

    ItemOwner getItem(Integer itemId, Integer userId);

    Item getItemById(Integer itemId);

    List<ItemOwner> getItems(Integer userId);

    List<Item> searchItems(String substring);
}
