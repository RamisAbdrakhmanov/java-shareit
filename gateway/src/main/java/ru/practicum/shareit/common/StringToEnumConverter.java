package ru.practicum.shareit.common;




import org.springframework.core.convert.converter.Converter;
import ru.practicum.shareit.booking.dto.State;

import javax.validation.ValidationException;


public class StringToEnumConverter implements Converter<String, State> {
    @Override
    public State convert(String source) {
        try {
            return State.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Unknown state: " + source);
        }
    }
}
