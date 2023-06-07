package ru.practicum.shareit.item.commet;

import ru.practicum.shareit.item.commet.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class CommentMapper {

    public static Comment toComment(Item item, User user, CommentDto commentDto) {
        return new Comment(
                commentDto.getText(),
                item,
                user
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getName(),
                comment.getCreated()
        );
    }
}
