package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.CreatorController.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingControllerTest {

    private final String url = "/bookings";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addBookingTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId = getItemId(mockMvc, userId, itemName, itemDescription, available);

        Integer userId2 = getUserId(mockMvc, userName2, email2);

        String bookingJson = createBookingJson(itemId, start, end);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson)
                        .header("X-Sharer-User-Id", userId2))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.item.id").value(itemId))
                .andExpect(jsonPath("$.item.name").value(itemName))
                .andExpect(jsonPath("$.booker.id").value(userId2))
                .andExpect(jsonPath("$.status").value(statusWait));
    }

    @Test
    public void approvedBookingTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId = getItemId(mockMvc, userId, itemName, itemDescription, available);

        Integer userId2 = getUserId(mockMvc, userName2, email2);

        Integer bookingId = getBookingId(mockMvc, itemId, userId2);

        mockMvc.perform(patch(url + "/{bookingId}", bookingId)
                        .header("X-Sharer-User-Id", userId)
                        .param("approved", String.valueOf(true)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(statusApr));
    }

    @Test
    public void getBookingTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId = getItemId(mockMvc, userId, itemName, itemDescription, available);

        Integer userId2 = getUserId(mockMvc, userName2, email2);

        Integer bookingId = getBookingId(mockMvc, itemId, userId2);

        mockMvc.perform(get(url + "/{bookingId}", bookingId)
                        .header("X-Sharer-User-Id", userId))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.item.id").value(itemId))
                .andExpect(jsonPath("$.item.name").value(itemName))
                .andExpect(jsonPath("$.booker.id").value(userId2))
                .andExpect(jsonPath("$.status").value(statusWait));
    }

    @Test
    public void getBookingsTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId = getItemId(mockMvc, userId, itemName, itemDescription, available);

        Integer userId2 = getUserId(mockMvc, userName2, email2);

        Integer bookingId = getBookingId(mockMvc, itemId, userId2);

        mockMvc.perform(get(url)
                        .header("X-Sharer-User-Id", userId2))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].item.id").value(itemId))
                .andExpect(jsonPath("$[0].item.name").value(itemName))
                .andExpect(jsonPath("$[0].booker.id").value(userId2))
                .andExpect(jsonPath("$[0].status").value(statusWait));
    }

    @Test
    public void getBookingsOwnerTest() throws Exception {
        Integer userId = getUserId(mockMvc, userName, email);
        Integer itemId = getItemId(mockMvc, userId, itemName, itemDescription, available);

        Integer userId2 = getUserId(mockMvc, userName2, email2);

        Integer bookingId = getBookingId(mockMvc, itemId, userId2);

        mockMvc.perform(get(url + "/owner", bookingId)
                        .header("X-Sharer-User-Id", userId))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].item.id").value(itemId))
                .andExpect(jsonPath("$[0].item.name").value(itemName))
                .andExpect(jsonPath("$[0].booker.id").value(userId2))
                .andExpect(jsonPath("$[0].status").value(statusWait));
    }
}
