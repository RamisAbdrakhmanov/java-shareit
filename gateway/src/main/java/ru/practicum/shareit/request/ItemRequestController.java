package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class ItemRequestController {

    private ItemRequestClient itemRequestClient;

    @GetMapping("/all")
    public ResponseEntity<Object> getItemRequestsAll(@RequestParam(defaultValue = "0") Integer from,
                                                     @RequestParam(defaultValue = "20") Integer size,
                                                     @RequestHeader("X-Sharer-User-Id") long requesterId) {
        log.info("Вызван метод getItemRequestsAll");
        return itemRequestClient.getItemRequestsAll(from, size, requesterId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemRequestsForRequester(@RequestHeader("X-Sharer-User-Id") long requesterId) {
        log.info("Вызван метод getItemRequestsForRequester");
        return itemRequestClient.getItemRequestsForRequester(requesterId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemRequest(@PathVariable(name = "id") long itemRequestId,
                                                 @RequestHeader("X-Sharer-User-Id") long requesterId) {
        log.info("Вызван метод getItemRequest");
        return itemRequestClient.getItemRequest(itemRequestId, requesterId);
    }

    @PostMapping
    public ResponseEntity<Object> addItemRequest(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                                 @RequestHeader("X-Sharer-User-Id") long requesterId) {
        log.info("Вызван метод addItemRequest");
        return itemRequestClient.addItemRequest(itemRequestDto, requesterId);
    }

}
