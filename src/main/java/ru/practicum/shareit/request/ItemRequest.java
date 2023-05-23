package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    Integer id;
    String description;
    User requestor;
    LocalDateTime created;
}
