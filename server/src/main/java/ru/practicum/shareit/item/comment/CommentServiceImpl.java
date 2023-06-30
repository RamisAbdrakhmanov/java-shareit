package ru.practicum.shareit.item.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.ElementException;
import ru.practicum.shareit.item.comment.dto.CommentDto;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private BookingRepository bookingRepository;
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(Integer itemId, CommentDto commentDto, Integer authorId) {
        Booking booking = bookingRepository.findFirstByItemIdAndBookerIdAndEndBeforeOrderByEndDesc(
                itemId,
                authorId,
                LocalDateTime.now()
        );

        if (booking == null) {
            throw new ElementException("You need to order this item");
        }
        Comment comment = CommentMapper.toComment(booking.getItem(), booking.getBooker(), commentDto);
        return commentRepository.save(comment);
    }
}
