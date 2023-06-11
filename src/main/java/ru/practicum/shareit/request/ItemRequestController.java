package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class ItemRequestController {

    private ItemRequestService itemRequestService;

    @GetMapping("/all")
    public List<ItemRequestDto> getItemRequestsAll(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                   @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size,
                                                   @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        int numbInPage = from % size;
        return itemRequestService.getItemRequestsAll(from, size, requesterId)
                .stream()
                .skip(numbInPage)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<ItemRequestDto> getItemRequestsForRequester(@RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        return itemRequestService.getItemRequestsForRequester(requesterId);
    }

    @GetMapping("/{id}")
    public ItemRequestDto getItemRequest(@PathVariable(name = "id") Integer itemRequestId,
                                         @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        return itemRequestService.getItemRequest(itemRequestId, requesterId);
    }

    @PostMapping
    public ItemRequestDto addItemRequest(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                         @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        return itemRequestService.addItemRequest(itemRequestDto, requesterId);
    }

}
