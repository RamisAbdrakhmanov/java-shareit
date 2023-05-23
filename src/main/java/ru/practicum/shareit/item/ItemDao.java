package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class ItemDao {
    private final List<ItemDto> items = new ArrayList<>();
    private Integer itemId = 1;

    public ItemDto addItem(ItemDto itemDto) {
        itemDto.setId(itemId++);
        items.add(itemDto);
        return itemDto;
    }

    public ItemDto updateItem(ItemDto itemDto) {
        ItemDto itemUpdate = getItem(itemDto.getId());
        if (!itemDto.getOwnerId().equals(itemUpdate.getOwnerId())) {
            throw new NotFoundException("Unable to change user");
        }
        if (itemDto.getAvailable() != null) {
            itemUpdate.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            itemUpdate.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            itemUpdate.setDescription(itemDto.getDescription());
        }
        if (itemDto.getOwnerId() != null) {
            itemUpdate.setOwnerId(itemDto.getOwnerId());
        }
        return itemUpdate;
    }

    public void deleteItem(Integer itemId) {
        items.forEach(s -> {
            if (Objects.equals(s.getId(), itemId)) {
                items.remove(s);
            }
        });
    }

    public ItemDto getItem(Integer itemId) {
        ItemDto itemDto = items.stream().filter(i -> Objects.equals(i.getId(), itemId)).findFirst().orElse(null);
        if (itemDto == null) {
            throw new NotFoundException("Item is not found");
        }
        return itemDto;
    }

    public List<ItemDto> getItems(Integer itemId) {
        return items.stream().filter(item -> item.getOwnerId().equals(itemId)).collect(Collectors.toList());
    }

    public List<ItemDto> searchItems(String substring) {
        Set<ItemDto> foundElements = items.stream()
                .filter(s -> s.getName().toLowerCase().contains(substring.toLowerCase()))
                .filter(ItemDto::getAvailable)
                .collect(Collectors.toSet());

        foundElements.addAll(items.stream()
                .filter(s -> s.getDescription().toLowerCase().contains(substring.toLowerCase()))
                .filter(ItemDto::getAvailable)
                .collect(Collectors.toSet()));

        return foundElements.stream()
                .sorted(Comparator.comparingInt(ItemDto::getId))
                .collect(Collectors.toList());
    }
}
