package ru.practicum.shareit.common;

import org.springframework.core.convert.converter.Converter;
import ru.practicum.shareit.booking.State;

public class StringToEnumConverter implements Converter<String, State> {
    @Override
    public State convert(String source) {
        try {
            return State.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: UNSUPPORTED_STATUS");
        }
    }
}