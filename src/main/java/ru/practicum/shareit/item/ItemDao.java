/*
package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;


@Repository
public class ItemDao {
    private final List<Item> items = new ArrayList<>();
    private Integer itemId = 1;

    public Item addItem(Item item) {
        item.setId(itemId++);
        items.add(item);
        return item;
    }

    public Item updateItem(Item item) {
        Item itemUpdate = getItem(item.getId());
        if (!item.getOwner().equals(itemUpdate.getOwner())) {
            throw new NotFoundException("Unable to change user");
        }
        if (item.getAvailable() != null) {
            itemUpdate.setAvailable(item.getAvailable());
        }
        if (item.getName() != null) {
            itemUpdate.setName(item.getName());
        }
        if (item.getDescription() != null) {
            itemUpdate.setDescription(item.getDescription());
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

    public Item getItem(Integer itemId) {
        Item item = items.stream().filter(i -> Objects.equals(i.getId(), itemId)).findFirst().orElse(null);
        if (item == null) {
            throw new NotFoundException("Item is not found");
        }
        return item;
    }

    public List<Item> getItems(Integer itemId) {
        return items.stream().filter(item -> item.getOwner().getId().equals(itemId)).collect(Collectors.toList());
    }

    public List<Item> searchItems(String substring) {
        Set<Item> foundElements = items.stream()
                .filter(s -> s.getName().toLowerCase().contains(substring.toLowerCase()))
                .filter(Item::getAvailable)
                .collect(Collectors.toSet());

        foundElements.addAll(items.stream()
                .filter(s -> s.getDescription().toLowerCase().contains(substring.toLowerCase()))
                .filter(Item::getAvailable)
                .collect(Collectors.toSet()));

        return foundElements.stream()
                .sorted(Comparator.comparingInt(Item::getId))
                .collect(Collectors.toList());
    }
}
*/
