package ru.practicum.shareit.item.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.shareit.CreatorService.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByItemIdTest() {
        Item itemGet = saveItem();
        Comment commentGet = saveComment(itemGet);
        List<Comment> comments = commentRepository.findAllByItemId(itemGet.getId());


        assertEquals(comments.get(0), commentGet);
        assertEquals(comments.get(0).getItem(), itemGet);
    }

    private Item saveItem() {
        User userGet = userRepository.save(user);
        item.setOwner(userGet);
        Item itemNew = itemRepository.save(item);
        item.setOwner(user);
        return itemNew;
    }

    private Comment saveComment(Item itemGet) {
        User bookerNew = userRepository.save(booker);
        comment.setAuthor(bookerNew);
        comment.setItem(itemGet);
        Comment commentGet = commentRepository.save(comment);
        comment.setAuthor(booker);
        comment.setItem(item);
        return commentGet;
    }


}