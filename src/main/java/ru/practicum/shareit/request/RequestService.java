package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.RequestDto;

import java.util.List;

public interface RequestService {

    List<RequestDto> getItemRequestsAll(Integer from, Integer size, Integer requesterId);

    List<RequestDto> getItemRequestsForRequester(Integer requesterId);

    RequestDto getItemRequest(Integer itemRequestId, Integer userId);

    RequestDto addItemRequest(RequestDto requestDto, Integer requesterId);
}
