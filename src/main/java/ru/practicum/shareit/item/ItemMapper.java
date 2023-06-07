package ru.practicum.shareit.item;

import ru.practicum.shareit.booking.dto.IBooking;
import ru.practicum.shareit.item.commet.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    public static Item toItem(ItemDto itemDto, User user) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                user
        );
    }

    public static ItemOwner toItemOwner(Item item, IBooking last, IBooking next, List<CommentDto> comments) {
        if (last != null) {
            if (last.getId() != null && next.getId() != null) {
                return new ItemOwner(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getAvailable(),
                        last,
                        next,
                        comments
                );
            } else if (last.getId() != null) {
                return new ItemOwner(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getAvailable(),
                        last,
                        null,
                        comments
                );
            } else if (next.getId() != null) {
                return new ItemOwner(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getAvailable(),
                        null,
                        next,
                        comments
                );
            } else {
                return new ItemOwner(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getAvailable(),
                        null,
                        null,
                        comments
                );
            }
        } else {
            return new ItemOwner(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getAvailable(),
                    null,
                    null,
                    comments
            );
        }
    }

}
