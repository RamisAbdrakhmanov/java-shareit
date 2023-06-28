package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class ItemRequestController {

    private ItemRequestService itemRequestService;

    @GetMapping("/all")
    public List<ItemRequestDto> getItemRequestsAll(@RequestParam(defaultValue = "0") Integer from,
                                                   @RequestParam(defaultValue = "20") Integer size,
                                                   @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        log.info("Вызван метод getItemRequestsAll");
        return itemRequestService.getItemRequestsAll(from, size, requesterId);
    }

    @GetMapping
    public List<ItemRequestDto> getItemRequestsForRequester(@RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        log.info("Вызван метод getItemRequestsForRequester");
        return itemRequestService.getItemRequestsForRequester(requesterId);
    }

    @GetMapping("/{id}")
    public ItemRequestDto getItemRequest(@PathVariable(name = "id") Integer itemRequestId,
                                         @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        log.info("Вызван метод getItemRequest");
        return itemRequestService.getItemRequest(itemRequestId, requesterId);
    }

    @PostMapping
    public ItemRequestDto addItemRequest(@RequestBody ItemRequestDto itemRequestDto,
                                         @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        log.info("Вызван метод addItemRequest");
        return itemRequestService.addItemRequest(itemRequestDto, requesterId);
    }

}
