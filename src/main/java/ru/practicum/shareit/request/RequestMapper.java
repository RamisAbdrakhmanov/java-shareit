package ru.practicum.shareit.request;

import ru.practicum.shareit.item.dto.ItemIQ;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class RequestMapper {

    public static Request toItemRequest(RequestDto RequestDto, User requester) {
        return new Request(
                RequestDto.getDescription(),
                requester,
                LocalDateTime.now()
        );
    }

    public static RequestDto toItemRequestDto(Request request, List<ItemIQ> items) {
        return new RequestDto(
                request.getId(),
                request.getDescription(),
                request.getRequester().getId(),
                request.getCreated(),
                items
        );
    }
}
