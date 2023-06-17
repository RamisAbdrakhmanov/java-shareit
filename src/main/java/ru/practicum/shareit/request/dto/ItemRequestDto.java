package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.item.dto.ItemIQ;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@AllArgsConstructor
public class ItemRequestDto {

    private Integer id;

    @EqualsAndHashCode.Exclude
    @NotBlank
    private String description;

    private Integer requesterId;

    private LocalDateTime created;

    @EqualsAndHashCode.Exclude
    private List<ItemIQ> items;
}
