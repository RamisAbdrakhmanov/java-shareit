package ru.practicum.shareit.common;

import org.springframework.core.convert.converter.Converter;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.exception.ElementException;


public class StringToEnumConverter implements Converter<String, State> {
    @Override
    public State convert(String source) {
        try {
            return State.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ElementException("Unknown state: " + source);
        }
    }
}
