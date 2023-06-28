package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "empty name")
    @EqualsAndHashCode.Exclude
    private String name;

    @NotNull(message = "null email")
    @Email(message = "wrong email")
    @EqualsAndHashCode.Exclude
    private String email;

}
