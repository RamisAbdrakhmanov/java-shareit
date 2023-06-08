package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.dto.IBooking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UserValidException;
import ru.practicum.shareit.item.commet.Comment;
import ru.practicum.shareit.item.commet.CommentMapper;
import ru.practicum.shareit.item.commet.CommentRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;
    private BookingRepository bookingRepository;
    private UserService userService;
    private CommentRepository commentRepository;

    @Override
    public Item addItem(ItemDto itemDto, Integer userId) {
        User user = userService.getUser(userId);
        Item item = ItemMapper.toItem(itemDto, user);
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(ItemDto itemDto, Integer userId) {
        Item item = itemRepository.getReferenceById(itemDto.getId());

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            checkValidOwnItem(item.getId(), userId);
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            checkValidOwnItem(item.getId(), userId);
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
        IBooking last = null;
        IBooking next = null;
        List<Comment> comments = commentRepository.findAllByItemId(item.getId());
        if (Objects.equals(item.getOwner().getId(), userId)) {
            last = BookingMapper.toIBooking(bookingRepository
                    .findFirstByItemIdAndStartBeforeAndStatusOrderByEndDesc(item.getId(), now, Status.APPROVED));
            next = BookingMapper.toIBooking(bookingRepository
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
    public List<ItemOwner> getItems(Integer userId) {
        userService.getUser(userId);
        List<Item> list = itemRepository.findAllByOwnerId(userId);
        return list.stream().map(item -> getItem(item.getId(), userId)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Item> searchItems(String substring) {
        return itemRepository.findItemByNameAndDescription(substring);
    }

    @Transactional(readOnly = true)
    @Override
    public Item getItemById(Integer itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        return item.get();
    }

    private void checkValidOwnItem(Integer itemId, Integer userId) {
        if (!Objects.equals(itemId, userId)) {
            throw new UserValidException("User dont have this item");
        }
    }


}
