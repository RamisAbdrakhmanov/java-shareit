package ru.practicum.shareit.item.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.comment.dto.CommentDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.practicum.shareit.CreatorService.commentDto;

@JsonTest
public class CommentDtoTest {
    @Autowired
    private JacksonTester<CommentDto> jacksonTester;

    @Test
    public void itemRequestDtoTest() throws IOException {
        JsonContent<CommentDto> content = jacksonTester.write(commentDto);

        assertThat(content).hasJsonPath("$.id");
        assertThat(content).hasJsonPath("$.text");
        assertThat(content).hasJsonPath("$.authorName");
        assertThat(content).hasJsonPath("$.created");
    }
}
