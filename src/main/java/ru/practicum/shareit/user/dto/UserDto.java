package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDto {

    private Integer id;

    @NotBlank(message = "empty name")
    @EqualsAndHashCode.Exclude
    private String name;

    @NotNull(message = "null email")
    @Email(message = "wrong email")
    @EqualsAndHashCode.Exclude
    private String email;

}
