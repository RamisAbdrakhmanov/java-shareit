package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.CreatorService.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = {"ru.yandex.practicum.shareit"})
class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository itemRequestRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Test
    void getItemRequestsAllTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));

        List<ItemRequestDto> list = itemRequestService.getItemRequestsForRequester(anyInt());

        assertEquals(list, new ArrayList<>());
    }

    @Test
    void getItemRequestsForRequester() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));
        when(itemRepository.findAllByItemRequestId(anyInt())).thenReturn(new ArrayList<>());
        when(itemRequestRepository.findAllByRequester(any())).thenReturn(List.of(itemRequest));

        List<ItemRequestDto> list = itemRequestService.getItemRequestsForRequester(anyInt());

        assertEquals(list, List.of(itemRequestDto));
    }

    @Test
    void getItemRequest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));
        when(itemRequestRepository.findById(anyInt())).thenReturn(Optional.of(itemRequest));

        ItemRequestDto requestDto = itemRequestService.getItemRequest(itemRequest.getId(), 1);

        assertEquals(requestDto, itemRequestDto);
    }

    @Test
    void addItemRequestTest() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(booker));
        when(itemRequestRepository.save(any())).thenReturn(itemRequest);
        when(itemRepository.findAllByItemRequestId(anyInt())).thenReturn(new ArrayList<>());

        ItemRequestDto itemRD = itemRequestService.addItemRequest(itemRequestDto, user.getId());

        assertEquals(itemRD, itemRequestDto);
    }
}