package ru.job4j_rest_chat.controller.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j_rest_chat.Job4jRestChatApplication;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jRestChatApplication.class)
@AutoConfigureMockMvc
class SpecificExceptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getNpe() throws Exception {
        final String expectedContent = "You've got this message caused by request NullPointerException";
        final MediaType type = new MediaType(MediaType.TEXT_PLAIN, Charset.defaultCharset());
        final String rootPath = "/exceptions/npe";
        mockMvc.perform(get(rootPath))
            .andExpect(content().contentType(type))
            .andExpect(content().string(expectedContent))
            .andExpect(status().isOk());
    }
}