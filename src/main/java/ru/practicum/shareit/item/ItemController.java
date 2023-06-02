package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;

    @GetMapping
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.getItems(userId).stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable Integer itemId) {
        return ItemMapper.toItemDto(itemService.getItem(itemId));
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                           @Valid @RequestBody ItemDto itemDto) {
        return ItemMapper.toItemDto(itemService.addItem(itemDto, userId));
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable Integer itemId,
                              @RequestHeader("X-Sharer-User-Id") Integer userId,
                              @RequestBody ItemDto itemDto) {
        itemDto.setId(itemId);
        return ItemMapper.toItemDto(itemService.updateItem(itemDto, userId));
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Integer itemId) {
        itemService.deleteItem(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemService.searchItems(text).stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

}
