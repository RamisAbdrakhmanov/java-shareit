package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.Comment.CommentClient;
import ru.practicum.shareit.item.Comment.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static ru.practicum.shareit.common.Argument.HEADER_FOR_USER;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;
    private final CommentClient commentClient;

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader(HEADER_FOR_USER) long userId,
                                           @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "20") @Positive Integer size) {
        log.info("Вызван метод getItems");
        return itemClient.getItems(userId, from, size);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable long itemId,
                                          @RequestHeader(HEADER_FOR_USER) long userId) {
        log.info("Вызван метод getItem");
        return itemClient.getItem(itemId, userId);
    }

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader(HEADER_FOR_USER) long userId,
                                          @NotNull @Valid @RequestBody ItemDto itemDto) {
        log.info("Вызван метод addItem");
        return itemClient.addItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@PathVariable long itemId,
                                             @RequestHeader(HEADER_FOR_USER) long userId,
                                             @RequestBody ItemDto itemDto) {
        log.info("Вызван метод updateItem");
        return itemClient.updateItem(itemId, userId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteItem(@PathVariable long itemId) {
        log.info("Вызван метод deleteItem");
        return itemClient.deleteItem(itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestParam String text,
                                              @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                              @RequestParam(defaultValue = "20") @Positive Integer size) {
        log.info("Вызван метод searchItems");

        return itemClient.searchItems(text, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@PathVariable long itemId,
                                             @Valid @RequestBody CommentDto commentDto,
                                             @RequestHeader(HEADER_FOR_USER) long authorId) {
        log.info("Вызван метод addComment");
        return commentClient.addComment(commentDto, itemId, authorId);
    }
}
