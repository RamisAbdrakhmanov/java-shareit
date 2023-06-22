package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingOut;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.practicum.shareit.CreatorService.last;

@JsonTest
public class BookingDtoTest {
    @Autowired
    private JacksonTester<BookingOut> jacksonTester;

    @Test
    public void itemRequestDtoTest() throws IOException {
        BookingOut bookingOut = BookingMapper.toBookingOut(last);

        JsonContent<BookingOut> content = jacksonTester.write(bookingOut);

        assertThat(content).hasJsonPath("$.id");
        assertThat(content).hasJsonPath("$.start");
        assertThat(content).hasJsonPath("$.end");
        assertThat(content).hasJsonPath("$.status");
        assertThat(content).hasJsonPath("$.booker");
        assertThat(content).hasJsonPath("$.item");

    }
}
