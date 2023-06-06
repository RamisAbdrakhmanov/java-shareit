package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.IBooking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UserValidException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemOwner;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImp implements ItemService {

    private ItemRepository itemRepository;
    private BookingRepository bookingRepository;
    private UserServiceImp userServiceImp;


    @Override
    public Item addItem(ItemDto itemDto, Integer userId) {
        User user = userServiceImp.getUser(userId);
        Item item = ItemMapper.toItem(itemDto, userServiceImp.getUser(userId));
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(ItemDto itemDto, Integer userId) {
        Item item = itemRepository.getReferenceById(itemDto.getId());

        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getName() != null) {
            if (!Objects.equals(item.getId(), userId)) {
                throw new UserValidException("User dont have this item");
            }
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            if (!Objects.equals(item.getId(), userId)) {
                throw new UserValidException("User dont have this item");
            }
            item.setDescription(itemDto.getDescription());
        }
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Integer itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public ItemOwner getItem(Integer itemId, Integer userId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new NotFoundException("Item not found");
        }


        LocalDateTime now = LocalDateTime.now();
        if (Objects.equals(item.get().getOwner().getId(), userId)) {
            IBooking last = BookingMapper.toIBooking(bookingRepository.
                    findFirstByItemIdAndEndBeforeOrderByEndDesc(itemId, now));
            IBooking next = BookingMapper.toIBooking(bookingRepository.
                    findFirstByItemIdAndStartAfterOrderByStartAsc(itemId, now));
            return ItemMapper.toItemOwner(item.get(), last, next);
        } else {
            return ItemMapper.toItemOwner(item.get(), null, null);
        }
    }

    @Override
    public List<ItemOwner> getItems(Integer userId) {
        userServiceImp.getUser(userId);
        List<Item> list = itemRepository.findAllByOwnerId(userId);
        return list.stream().map(item -> {
            LocalDateTime now = LocalDateTime.now();
            IBooking last = null;
            IBooking next = null;

            if (Objects.equals(item.getOwner().getId(), userId)) {
                last = BookingMapper.toIBooking(bookingRepository.
                        findFirstByItemIdAndEndBeforeOrderByEndDesc(item.getId(), now));
                next = BookingMapper.toIBooking(bookingRepository.
                        findFirstByItemIdAndStartAfterOrderByStartAsc(item.getId(), now));
            }
            return ItemMapper.toItemOwner(item, last, next);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Item> searchItems(String substring) {
        return itemRepository.findItemByNameAndDescription(substring);
    }

    @Override
    public Item getItemById(Integer itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        return item.get();
    }


}
