package ru.practicum.shareit.item.commet;

import ru.practicum.shareit.item.commet.dto.CommentDto;

public interface CommentService {

    Comment addComment(Integer itemId, CommentDto commentDto, Integer authorId);
}
