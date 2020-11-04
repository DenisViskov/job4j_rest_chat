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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.job4j_rest_chat.Job4jRestChatApplication;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.service.RepositoryService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jRestChatApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("personService")
    @Autowired
    private RepositoryService service;

    @Test
    @WithMockUser
    void findAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(new Person(0, "login", "password", null)));
        mockMvc.perform(get("/person/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":0," +
                        "\"login\":\"login\"," +
                        "\"password\":\"password\"," +
                        "\"role\":null}]"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getById() throws Exception {
        when(service.findById(1)).thenReturn(Optional.of(new Person(1, "login", "password", null)));
        mockMvc.perform(get("/person/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1," +
                        "\"login\":\"login\"," +
                        "\"password\":\"password\"," +
                        "\"role\":null}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void createPerson() throws Exception {
        when(service.add(any())).thenReturn(new Person(1, "login", "password", null));
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"login\":\"login\",\"password\":\"password\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"login\":\"login\",\"password\":\"password\"}"))
                .andExpect(status().isOk());
        verify(service).add(any());
    }

    @Test
    @WithMockUser
    void updatePerson() throws Exception {
        when(service.findById(0)).thenReturn(Optional.of(new Person(0, "login", "password", null)));
        mockMvc.perform(put("/person/0"))
                .andExpect(status().isOk());
        verify(service).update(any());
    }

    @Test
    @WithMockUser
    void deletePerson() throws Exception {
        when(service.findById(0)).thenReturn(Optional.of(new Person(0, "login", "password", null)));
        mockMvc.perform(delete("/person/0"))
                .andExpect(status().isOk());
        verify(service).delete(any());
    }
}