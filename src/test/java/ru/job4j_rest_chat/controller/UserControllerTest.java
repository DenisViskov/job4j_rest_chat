package ru.job4j_rest_chat.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j_rest_chat.Job4jRestChatApplication;
import ru.job4j_rest_chat.service.RepositoryService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jRestChatApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("personService")
    @Autowired
    private RepositoryService personService;

    @Test
    @WithMockUser
    void signUp() throws Exception {
        mockMvc.perform(post("/users/sign-up")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\n" +
                "  \"login\": \"admin\",\n" +
                "  \"password\": \"password\"\n" +
                "}"))
                .andExpect(status().isOk());
        verify(personService).add(any());
    }

    @Test
    @WithMockUser
    void findAll() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"))
                .andExpect(status().isOk());
        verify(personService).findAll();
    }
}