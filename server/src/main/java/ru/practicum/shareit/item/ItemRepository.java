package ru.practicum.shareit.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Pageable> {

    List<Item> findAllByOwnerId(Integer userId, Pageable pageable);

    @Query("select it " +
            "from  Item as it " +
            "where (lower(it.name) like concat('%',lower(?1),'%') " +
            "or lower(it.description) like concat('%',lower(?1),'%')) " +
            "and it.available =true ")
    List<Item> findItemByNameAndDescription(String search, Pageable pageable);

    List<Item> findAllByItemRequestId(Integer itemRequestId);

    Optional<Item> findById(Integer itemId);

    void deleteById(Integer itemId);
}
