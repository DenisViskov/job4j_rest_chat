package ru.job4j_rest_chat.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.job4j_rest_chat.Job4jRestChatApplication;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.domain.Role;
import ru.job4j_rest_chat.domain.Room;
import ru.job4j_rest_chat.service.RepositoryService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jRestChatApplication.class)
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("personService")
    @Autowired
    private RepositoryService personService;
    @MockBean
    @Qualifier("roomService")
    @Autowired
    private RepositoryService roomService;

    @Test
    void getAll() throws Exception {
        when(roomService.findAll()).thenReturn(List.of(new Room(0, "room")));
        mockMvc.perform(get("/room/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":0,\"name\":\"room\",\"persons\":[]}]"))
                .andExpect(status().isOk());
    }

    @Test
    void createRoom() throws Exception {
        when(roomService.add(any())).thenReturn(new Room(1, "room"));
        mockMvc.perform(post("/room/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":0,\"name\":\"room\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"room\",\"persons\":[]}"))
                .andExpect(status().isCreated());
    }

    @Test
    void enterToRoom() throws Exception {
        when(roomService.findById(0)).thenReturn(Optional.of(new Room(0, "room")));
        when(personService.findById(1)).thenReturn(Optional.of(new Person(1,
                "login",
                "password",
                new Role(1, "ROLE_USER"))));
        mockMvc.perform(put("/room/enter/0/1"))
                .andExpect(status().isOk());
        verify(roomService).update(any());
    }

    @Test
    void WhenWeDontHaveRoomEnterToRoom() throws Exception {
        when(roomService.findById(0)).thenReturn(Optional.empty());
        when(personService.findById(1)).thenReturn(Optional.of(new Person(1,
                "login",
                "password",
                new Role(1, "ROLE_USER"))));
        mockMvc.perform(put("/room/enter/0/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRoom() throws Exception {
        when(roomService.findById(0)).thenReturn(Optional.of(new Room(0, "room")));
        mockMvc.perform(delete("/room/0"))
                .andExpect(status().isOk());
        verify(roomService).delete(any());
    }

    @Test
    void WhenDontHaveRoomDeleteRoom() throws Exception {
        when(roomService.findById(0)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/room/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMessage() throws Exception {
        when(personService.findById(1)).thenReturn(Optional.of(new Person(1,
                "login",
                "password",
                new Role(1, "ROLE_USER"))));
        mockMvc.perform(get("/room/message/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":0,\"content\":\"content\"}"))
                .andExpect(content().json("{\"id\":0," +
                        "\"content\":\"content\"," +
                        "\"author\":{\"id\":1," +
                        "\"login\":\"login\"," +
                        "\"password\":\"password\"," +
                        "\"role\":{\"id\":1,\"name\":\"ROLE_USER\"}}}"))
                .andExpect(status().isOk());
    }
}