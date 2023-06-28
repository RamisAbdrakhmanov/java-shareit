package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
public class ItemRequestDto {

    private Integer id;

    @EqualsAndHashCode.Exclude
    @NotBlank
    private String description;

}
