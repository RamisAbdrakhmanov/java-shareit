package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.CommentMapper;
import ru.practicum.shareit.item.comment.CommentService;
import ru.practicum.shareit.item.comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwner;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private ItemService itemService;
    private CommentService commentService;

    @GetMapping
    public List<ItemOwner> getItems(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                    @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                    @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size) {
        log.info("Вызван метод getItems");
        return itemService.getItems(userId, from, size);
    }

    @GetMapping("/{itemId}")
    public ItemOwner getItem(@PathVariable Integer itemId,
                             @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Вызван метод getItem");
        return itemService.getItem(itemId, userId);
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                           @NotNull @Valid @RequestBody ItemDto itemDto) {
        log.info("Вызван метод addItem");
        return ItemMapper.toItemDto(itemService.addItem(itemDto, userId));
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable Integer itemId,
                              @RequestHeader("X-Sharer-User-Id") Integer userId,
                              @RequestBody ItemDto itemDto) {
        log.info("Вызван метод updateItem");
        itemDto.setId(itemId);
        return ItemMapper.toItemDto(itemService.updateItem(itemDto, userId));
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Integer itemId) {
        log.info("Вызван метод deleteItem");
        itemService.deleteItem(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text,
                                     @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                     @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size) {
        log.info("Вызван метод searchItems");
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemService.searchItems(text, from, size)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable Integer itemId,
                                 @Valid @RequestBody CommentDto commentDto,
                                 @RequestHeader("X-Sharer-User-Id") Integer authorId) {
        log.info("Вызван метод addComment");
        return CommentMapper.toCommentDto(commentService.addComment(itemId, commentDto, authorId));
    }

}
