package ru.job4j_rest_chat.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j_rest_chat.domain.Message;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.domain.Room;
import ru.job4j_rest_chat.dto.RoomDto;
import ru.job4j_rest_chat.dto.RoomDtoMapper;
import ru.job4j_rest_chat.service.RepositoryService;

import java.util.List;
import java.util.Optional;

/**
 * Class is a rest room controller
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@RestController
@RequestMapping("/room")
public class RoomController {
    /**
     * Person service
     */
    private final RepositoryService<Person> personService;
    /**
     * Room service
     */
    private final RepositoryService<Room> roomService;

    private final RoomDtoMapper roomDtoMapper = RoomDtoMapper.INSTANCE;

    @Autowired
    public RoomController(@Qualifier("personService") RepositoryService personService,
                          @Qualifier("roomService") RepositoryService roomService) {
        this.personService = personService;
        this.roomService = roomService;
    }

    /**
     * @return all rooms
     */
    @GetMapping("/")
    public List<Room> getAll() {
        return roomService.findAll();
    }

    /**
     * Create new room
     *
     * @param room
     * @return room
     */
    @PostMapping("/")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room result = (Room) roomService.add(room);
        return new ResponseEntity<>(
                result,
                HttpStatus.CREATED
        );
    }

    /**
     * Execute enter person in room
     *
     * @param roomId
     * @param userId
     * @return void
     */
    @PutMapping("/enter/{room_id}/{user_id}")
    public ResponseEntity<Void> enterToRoom(@PathVariable("room_id") int roomId,
                                            @PathVariable("user_id") int userId) {
        Optional<Room> roomBox = roomService.findById(roomId);
        Optional<Person> personBox = personService.findById(userId);
        if (!roomBox.isPresent() || !personBox.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Room room = roomBox.get();
        room.addPerson(personBox.get());
        roomService.update(room);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete room
     *
     * @param id
     * @return void
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") int id) {
        Optional<Room> roomBox = roomService.findById(id);
        if (roomBox.isPresent()) {
            Room room = roomBox.get();
            roomService.delete(room);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Dont save message on the side server
     * Necessary what would just shows message on the side GUI
     *
     * @param id
     * @param message
     * @return Message
     */
    @PostMapping("/message/{user_id}")
    public ResponseEntity<Message> postMessage(@PathVariable("user_id") int id,
                                               @RequestBody Message message) {
        Person person = (Person) personService.findById(id).get();
        Message result = new Message(message.getId(), message.getContent(), person);
        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    @SneakyThrows
    @PatchMapping("/partOfUpdateRoom")
    public ResponseEntity<Void> partOfUpdateRoom(@RequestBody final RoomDto roomDto){
        final Room room = roomDtoMapper.roomDtoToRoomEntity(roomDto);
        roomService.update(room);
        return ResponseEntity.ok().build();
    }
}
