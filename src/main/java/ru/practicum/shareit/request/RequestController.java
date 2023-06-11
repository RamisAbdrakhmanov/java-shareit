package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;

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
public class RequestController {

    private RequestService requestService;

    @GetMapping("/all")
    public List<RequestDto> getItemRequestsAll(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                               @RequestParam(defaultValue = "20") @Min(1) @Max(100) Integer size,
                                               @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        int numbInPage = from % size;
        return requestService.getItemRequestsAll(from, size, requesterId)
                .stream()
                .skip(numbInPage)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<RequestDto> getItemRequestsForRequester(@RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        return requestService.getItemRequestsForRequester(requesterId);
    }

    @GetMapping("/{id}")
    public RequestDto getItemRequest(@PathVariable(name = "id") Integer itemRequestId,
                                     @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        return requestService.getItemRequest(itemRequestId, requesterId);
    }

    @PostMapping
    public RequestDto addItemRequest(@Valid @RequestBody RequestDto requestDto,
                                     @RequestHeader("X-Sharer-User-Id") Integer requesterId) {
        return requestService.addItemRequest(requestDto, requesterId);
    }

}
