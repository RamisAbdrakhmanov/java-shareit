package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    List<ItemRequestDto> getItemRequestsAll(Integer from, Integer size, Integer requesterId);

    List<ItemRequestDto> getItemRequestsForRequester(Integer requesterId);

    ItemRequestDto getItemRequest(Integer itemRequestId, Integer userId);

    ItemRequestDto addItemRequest(ItemRequestDto itemRequestDto, Integer requesterId);
}
