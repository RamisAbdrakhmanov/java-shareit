package ru.practicum.shareit.item.comment;

import ru.practicum.shareit.item.comment.dto.CommentDto;

public interface CommentService {

    Comment addComment(Integer itemId, CommentDto commentDto, Integer authorId);
}
