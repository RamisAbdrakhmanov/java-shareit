package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    private Integer id;


    @NotBlank(message = "description cannot be null, empty or blank")
    private String description;

}
