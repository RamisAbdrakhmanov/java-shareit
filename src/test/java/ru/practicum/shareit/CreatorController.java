package ru.practicum.shareit;

import com.jayway.jsonpath.JsonPath;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.booking.Status;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@UtilityClass
public class CreatorController {
    public final String userName = "user";
    public final String email = "user@user.ru";

    public final String userName2 = "update";
    public final String email2 = "update@user.com";

    public final String itemName = "Дрель";
    public final String itemDescription = "Простая дрель";
    public final boolean available = true;

    public final String itemName2 = "Дрель+";
    public final String itemDescription2 = "Аккумуляторная дрель";
    public final boolean availableUpd = false;

    public final LocalDateTime start = LocalDateTime.now().plusMinutes(2).truncatedTo(ChronoUnit.SECONDS);
    public final LocalDateTime end = LocalDateTime.now().plusMinutes(3).truncatedTo(ChronoUnit.SECONDS);
    public final String statusWait = Status.WAITING.toString();
    public final String statusApr = Status.APPROVED.toString();

    public final String requestDescription = "Хотел бы воспользоваться щёткой для обуви";

    public final String text = "Here is a comment here";
    public final LocalDateTime created = LocalDateTime.now().plusSeconds(4).truncatedTo(ChronoUnit.SECONDS);


    public Integer getItemId(MockMvc mockMvc,
                             Integer userId,
                             String name,
                             String description,
                             boolean available) throws Exception {

        String item = createItemDtoJson(name, description, available);

        MvcResult resultItem = mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", userId)
                        .content(item)
                        .contentType(MediaType.APPLICATION_JSON))

                .andReturn();

        return JsonPath.read(resultItem.getResponse().getContentAsString(), "$.id");
    }

    public String createItemDtoJson(String name, String description, boolean available) {
        return "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"description\": \"" + description + "\",\n" +
                "    \"available\": " + available + "\n" +
                "}";
    }

    public Integer getUserId(MockMvc mockMvc, String name, String email) throws Exception {
        String addUser = createUserJson(name, email);

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addUser))
                .andReturn();

        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    public String createUserJson(String name, String email) {
        return "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"email\": \"" + email + "\"\n" +
                "}";

    }

    public Integer getBookingId(MockMvc mockMvc, Integer itemId, Integer userId2) throws Exception {
        String bookingJson = createBookingJson(itemId, start, end);

        MvcResult result = mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson)
                        .header("X-Sharer-User-Id", userId2))
                .andReturn();

        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    public String createBookingJson(Integer itemId, LocalDateTime start, LocalDateTime end) {


        return "{\n" +
                "    \"itemId\": " + itemId + ",\n" +
                "    \"start\": \"" + start + "\",\n" +
                "    \"end\": \"" + end + "\"\n" +
                "}";
    }

    public Integer getRequestId(MockMvc mockMvc, Integer userId, String description) throws Exception {
        String requestJson = createRequestJson(description);

        MvcResult result = mockMvc.perform(post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .header("X-Sharer-User-Id", userId))
                .andReturn();

        return JsonPath.read(result.getResponse().getContentAsString(), "$.id");
    }

    public String createRequestJson(String text) {
        return "{\n" +
                "    \"description\": \"" + text + "\"\n" +
                "}";
    }

}
