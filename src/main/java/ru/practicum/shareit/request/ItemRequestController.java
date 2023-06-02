package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    @GetMapping
    public List<ItemRequest> getItemRequests() {
        return null;
    }

    @GetMapping("/{id}")
    public ItemRequest getItemRequest(@PathVariable String id) {
        return null;
    }

    @PostMapping
    public ItemRequest addItemRequest() {
        return null;
    }

    @PatchMapping
    public ItemRequest updateItemRequest() {
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteItemRequest(@PathVariable String id) {

    }
}
