package ru.job4j_rest_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j_rest_chat.Job4jRestChatApplication;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.domain.Room;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jRestChatApplication.class)
@AutoConfigureMockMvc
public class ValidationCheckTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser
    @SneakyThrows
    void checkPersonValidation() {
        final Person person = new Person();
        person.setId(1);
        person.setLogin("test");
        person.setPassword(" ");

        final String jsonBody = mapper.writeValueAsString(person);
        final String expectedResponse = "[{\"password\":\"password is mandatory. Actual value:  \"}]";

        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
            .andExpect(content().string(expectedResponse))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void checkRoomValidation() {
        final Room room = new Room();
        room.setId(1);
        room.setName("testasdasdasd");

        final String jsonBody = mapper.writeValueAsString(room);
        final String expectedResponse = "[{\"name\":\"Name must be between 1 to 10 characters. Actual value: testasdasdasd\"}]";

        mockMvc.perform(post("/room/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
            .andExpect(content().string(expectedResponse))
            .andExpect(status().isBadRequest());
    }


}
