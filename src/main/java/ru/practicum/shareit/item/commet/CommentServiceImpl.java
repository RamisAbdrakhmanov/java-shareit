package ru.practicum.shareit.item.commet;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.commet.dto.CommentDto;
import ru.practicum.shareit.exception.NotFoundException;

import javax.validation.ValidationException;
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
            throw new ValidationException("You need to order this item");
        }
        Comment comment = CommentMapper.toComment(booking.getItem(),booking.getBooker(),commentDto);
        return commentRepository.save(comment);
    }
}
