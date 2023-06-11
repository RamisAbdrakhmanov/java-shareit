package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemIQ;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;
    private UserService userService;
    private ItemRepository itemRepository;

    @Override
    public List<RequestDto> getItemRequestsAll(Integer from, Integer size, Integer requesterId) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("created").ascending());
        return requestRepository.findAllByRequesterIdIsNot(requesterId, pageable).stream().map(i -> {
            List<ItemIQ> list = itemRepository.findAllByItemRequestId(i.getId())
                    .stream()
                    .map(ItemMapper::toItemIQ)
                    .collect(Collectors.toList());
            return RequestMapper.toItemRequestDto(i, list);
        }).collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getItemRequestsForRequester(Integer requesterId) {
        User requester = userService.getUser(requesterId);
        return requestRepository.findAllByRequester(requester).stream().map(i -> {
            List<ItemIQ> list = itemRepository.findAllByItemRequestId(i.getId())
                    .stream()
                    .map(ItemMapper::toItemIQ)
                    .collect(Collectors.toList());
            return RequestMapper.toItemRequestDto(i, list);
        }).collect(Collectors.toList());

    }

    @Override
    public RequestDto getItemRequest(Integer itemRequestId, Integer requesterId) {
        userService.getUser(requesterId);
        List<ItemIQ> list = itemRepository.findAllByItemRequestId(itemRequestId)
                .stream()
                .map(ItemMapper::toItemIQ)
                .collect(Collectors.toList());
        return RequestMapper.toItemRequestDto(requestRepository.findById(itemRequestId)
                .orElseThrow(() -> new NotFoundException("ItemRequest not found")), list);
    }

    public Request getItemRequestFofCreateItem(Integer itemRequestId) {
        return requestRepository.findById(itemRequestId)
                .orElseThrow(() -> new NotFoundException("ItemRequest not found"));
    }

    @Override
    public RequestDto addItemRequest(RequestDto requestDto, Integer requesterId) {
        User requester = userService.getUser(requesterId);
        Request request = requestRepository.save(RequestMapper.toItemRequest(requestDto, requester));
        List<ItemIQ> list = itemRepository.findAllByItemRequestId(request.getId())
                .stream()
                .map(ItemMapper::toItemIQ)
                .collect(Collectors.toList());
        return RequestMapper.toItemRequestDto(request, list);
    }
}
