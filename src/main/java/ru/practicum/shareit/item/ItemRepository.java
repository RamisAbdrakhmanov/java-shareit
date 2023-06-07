package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findAllByOwnerId(Integer userId);

    @Query("select it " +
            "from  Item as it " +
            "where (lower(it.name) like concat('%',lower(?1),'%') " +
            "or lower(it.description) like concat('%',lower(?1),'%')) " +
            "and it.available =true ")
    List<Item> findItemByNameAndDescription(String search);

}
