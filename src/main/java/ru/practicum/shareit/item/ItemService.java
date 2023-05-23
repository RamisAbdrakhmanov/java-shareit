package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserDao;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemDao itemDao;
    private UserDao userDao;

    public ItemDto addItem(ItemDto itemDto) {
        userDao.getUser(itemDto.getOwnerId());
        return itemDao.addItem(itemDto);
    }

    public ItemDto updateItem(ItemDto itemDto) {
        userDao.getUser(itemDto.getOwnerId());
        return itemDao.updateItem(itemDto);
    }

    public void deleteItem(Integer itemId) {
        itemDao.deleteItem(itemId);
    }

    public ItemDto getItem(Integer itemId) {
        return itemDao.getItem(itemId);
    }

    public List<ItemDto> getItems(Integer userId) {
        userDao.getUser(userId);
        return itemDao.getItems(userId);
    }

    public List<ItemDto> searchItems(String substring) {
        return itemDao.searchItems(substring);
    }

}
