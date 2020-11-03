package ru.job4j_rest_chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.domain.Room;
import ru.job4j_rest_chat.service.RepositoryService;

import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    private final RepositoryService personService;
    private final RepositoryService roomService;

    @Autowired
    public RoomController(@Qualifier("roomService") RepositoryService personService,
                          @Qualifier("personService") RepositoryService roomService) {
        this.personService = personService;
        this.roomService = roomService;
    }

    @GetMapping("/")
    public List<Room> getAll() {
        return roomService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room result = (Room) roomService.add(room);
        return new ResponseEntity<>(
                result,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/enter/{id}?user={user_id}")
    public ResponseEntity<Void> enterToRoom(@PathVariable("id") int roomId,
                                            @RequestParam("user_id") int userId) {

    }
}
