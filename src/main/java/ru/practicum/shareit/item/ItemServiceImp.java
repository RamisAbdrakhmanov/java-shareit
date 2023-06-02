package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UserValidException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserServiceImp;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceImp implements ItemService {

    private ItemRepository itemRepository;
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
    public Item getItem(Integer itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isEmpty()){
            throw new NotFoundException("Item not found");
        }
        return item.get();
    }

    @Override
    public List<Item> getItems(Integer userId) {
        userServiceImp.getUser(userId);
        return itemRepository.findAllByOwnerId(userId);
    }

    @Override
    public List<Item> searchItems(String substring) {
        return itemRepository.findItemByNameAndDescription(substring);
    }

}
