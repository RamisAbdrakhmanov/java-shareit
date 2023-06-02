package ru.practicum.shareit.item;


import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto addItem(ItemDto itemDto, Integer userId);

    ItemDto updateItem(ItemDto itemDto, Integer userId);

    void deleteItem(Integer itemId);

    ItemDto getItem(Integer itemId);

    List<ItemDto> getItems(Integer userId);

    List<ItemDto> searchItems(String substring);
}
