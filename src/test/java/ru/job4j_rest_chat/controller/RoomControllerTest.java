package ru.job4j_rest_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j_rest_chat.Job4jRestChatApplication;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.domain.Role;
import ru.job4j_rest_chat.domain.Room;
import ru.job4j_rest_chat.dto.PersonDto;
import ru.job4j_rest_chat.dto.RoomDto;
import ru.job4j_rest_chat.service.RepositoryService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    private RepositoryService<Person> personService;
    @MockBean
    @Qualifier("roomService")
    @Autowired
    private RepositoryService<Room> roomService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser
    void getAll() throws Exception {
        when(roomService.findAll()).thenReturn(List.of(new Room(0, "room")));
        mockMvc.perform(get("/room/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":0,\"name\":\"room\",\"persons\":[]}]"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void createRoom() throws Exception {
        when(roomService.add(any())).thenReturn(new Room(1, "room"));
        mockMvc.perform(post("/room/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"room\"}"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":1,\"name\":\"room\",\"persons\":[]}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
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
    @WithMockUser
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
    @WithMockUser
    void deleteRoom() throws Exception {
        when(roomService.findById(0)).thenReturn(Optional.of(new Room(0, "room")));
        mockMvc.perform(delete("/room/0"))
                .andExpect(status().isOk());
        verify(roomService).delete(any());
    }

    @Test
    @WithMockUser
    void WhenDontHaveRoomDeleteRoom() throws Exception {
        when(roomService.findById(0)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/room/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void postMessage() throws Exception {
        when(personService.findById(1)).thenReturn(Optional.of(new Person(1,
                "login",
                "password",
                new Role(1, "ROLE_USER"))));
        mockMvc.perform(post("/room/message/1")
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

    @Test
    @SneakyThrows
    @WithMockUser
    void partOfUpdateRoom() {
        doNothing().when(roomService).update(any());

        final RoomDto request = RoomDto.builder()
            .id(1)
            .name("test")
            .persons(Set.of(
                PersonDto.builder()
                .id(1)
                .login("test")
                .password("test")
                .build())
            )
            .build();

        final String jsonBody = mapper.writeValueAsString(request);

        mockMvc.perform(patch("/room/partOfUpdateRoom")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBody))
            .andExpect(status().isOk());
    }
}