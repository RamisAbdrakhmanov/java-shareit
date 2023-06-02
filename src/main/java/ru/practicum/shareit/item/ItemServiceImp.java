package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImp implements ItemService {

    private ItemDao itemDao;
    private UserDao userDao;

    @Override
    public ItemDto addItem(ItemDto itemDto, Integer userId) {
        Item item = ItemMapper.toItem(itemDto, userDao.getUser(userId));
        return ItemMapper.toItemDto(itemDao.addItem(item));
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Integer userId) {
        Item item = ItemMapper.toItem(itemDto, userDao.getUser(userId));
        return ItemMapper.toItemDto(itemDao.updateItem(item));
    }

    @Override
    public void deleteItem(Integer itemId) {
        itemDao.deleteItem(itemId);
    }

    @Override
    public ItemDto getItem(Integer itemId) {
        return ItemMapper.toItemDto(itemDao.getItem(itemId));
    }

    @Override
    public List<ItemDto> getItems(Integer userId) {
        userDao.getUser(userId);
        return itemDao.getItems(userId).stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItems(String substring) {
        return itemDao.searchItems(substring).stream().map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

}
