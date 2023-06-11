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
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private ItemRequestRepository itemRequestRepository;
    private UserService userService;
    private ItemRepository itemRepository;

    @Override
    public List<ItemRequestDto> getItemRequestsAll(Integer from, Integer size, Integer requesterId) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("created").ascending());
        return itemRequestRepository.findAllByRequesterIdIsNot(requesterId, pageable).stream().map(i -> {
            List<ItemIQ> list = itemRepository.findAllByItemRequestId(i.getId())
                    .stream()
                    .map(ItemMapper::toItemIQ)
                    .collect(Collectors.toList());
            return ItemRequestMapper.toItemRequestDto(i, list);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getItemRequestsForRequester(Integer requesterId) {
        User requester = userService.getUser(requesterId);
        return itemRequestRepository.findAllByRequester(requester).stream().map(i -> {
            List<ItemIQ> list = itemRepository.findAllByItemRequestId(i.getId())
                    .stream()
                    .map(ItemMapper::toItemIQ)
                    .collect(Collectors.toList());
            return ItemRequestMapper.toItemRequestDto(i, list);
        }).collect(Collectors.toList());

    }

    @Override
    public ItemRequestDto getItemRequest(Integer itemRequestId, Integer requesterId) {
        userService.getUser(requesterId);
        List<ItemIQ> list = itemRepository.findAllByItemRequestId(itemRequestId)
                .stream()
                .map(ItemMapper::toItemIQ)
                .collect(Collectors.toList());
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.findById(itemRequestId)
                .orElseThrow(() -> new NotFoundException("ItemRequest not found")), list);
    }

    public ItemRequest getItemRequestFofCreateItem(Integer itemRequestId) {
        return itemRequestRepository.findById(itemRequestId)
                .orElseThrow(() -> new NotFoundException("ItemRequest not found"));
    }

    @Override
    public ItemRequestDto addItemRequest(ItemRequestDto itemRequestDto, Integer requesterId) {
        User requester = userService.getUser(requesterId);
        ItemRequest itemRequest = itemRequestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto, requester));
        List<ItemIQ> list = itemRepository.findAllByItemRequestId(itemRequest.getId())
                .stream()
                .map(ItemMapper::toItemIQ)
                .collect(Collectors.toList());
        return ItemRequestMapper.toItemRequestDto(itemRequest, list);
    }
}
