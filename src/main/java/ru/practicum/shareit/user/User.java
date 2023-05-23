package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * TODO Sprint add-controllers.
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    @NotBlank(message = "empty name")
    private String name;
    @NotNull(message = "null email")
    @Email(message = "wrong email")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
