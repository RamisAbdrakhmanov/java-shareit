package ru.practicum.shareit.item.comment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.practicum.shareit.CreatorService.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = {"ru.yandex.practicum.shareit"})
class CommentServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    void addCommentTest() {
        when(bookingRepository.findFirstByItemIdAndBookerIdAndEndBeforeOrderByEndDesc(any(), any(), any()))
                .thenReturn(last);
        when(commentRepository.save(any())).thenReturn(comment);
        Comment com = commentService.addComment(item.getId(), commentDto, booker.getId());

        assertEquals(com, comment);
    }

    @Test
    void addCommentWrongAuthorTest() {
        when(bookingRepository.findFirstByItemIdAndBookerIdAndEndBeforeOrderByEndDesc(any(), any(), any()))
                .thenReturn(null);
        ValidationException validationException = assertThrows(ValidationException.class,
                () -> commentService.addComment(item.getId(), commentDto, booker.getId()));

        assertNotNull(validationException.getMessage());
    }
}