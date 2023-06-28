package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.BookingIt;
import ru.practicum.shareit.common.MyPageRequest;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentMapper;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private ItemRequestRepository itemRequestRepository;

    @Override
    public Item addItem(ItemDto itemDto, Integer userId) {
        User user = getUser(userId);
        ItemRequest itemRequest =
                itemDto.getRequestId() != null ? itemRequestRepository
                        .findById(itemDto.getRequestId())
                        .orElseThrow(() -> new NotFoundException("ItemRequest not found")) : null;
        Item item = ItemMapper.toItem(itemDto, user, itemRequest);
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(ItemDto itemDto, Integer userId) {
        Item item = getItemById(itemDto.getId());

        if (!Objects.equals(item.getOwner().getId(), userId)) {
            throw new NotFoundException("User dont have this item");
        }

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
    }


    @Transactional(readOnly = true)
    @Override
    public ItemOwner getItem(Integer itemId, Integer userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));

        LocalDateTime now = LocalDateTime.now();
        BookingIt last = null;
        BookingIt next = null;
        List<Comment> comments = commentRepository.findAllByItemId(item.getId());
        if (Objects.equals(item.getOwner().getId(), userId)) {
            last = BookingMapper.toBookingIt(bookingRepository
                    .findFirstByItemIdAndStartBeforeAndStatusOrderByEndDesc(item.getId(), now, Status.APPROVED));
            next = BookingMapper.toBookingIt(bookingRepository
                    .findFirstByItemIdAndStartAfterAndStatusOrderByStartAsc(item.getId(), now, Status.APPROVED));
        }

        return ItemMapper.toItemOwner(
                item,
                last,
                next,
                comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList())
        );
    }


    @Transactional(readOnly = true)
    @Override
    public List<ItemOwner> getItems(Integer userId, Integer from, Integer size) {
        getUser(userId);
        Pageable pageable = MyPageRequest.of(from, size);
        List<Item> list = itemRepository.findAllByOwnerIdOrderByIdAsc(userId, pageable);
        return list.stream().map(item -> getItem(item.getId(), userId)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Item> searchItems(String substring, Integer from, Integer size) {
        Pageable pageable = MyPageRequest.of(from, size);
        return itemRepository.findItemByNameAndDescription(substring, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Item getItemById(Integer itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
    }

    private User getUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
