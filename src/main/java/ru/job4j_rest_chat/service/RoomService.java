package ru.job4j_rest_chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j_rest_chat.domain.Room;
import ru.job4j_rest_chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

/**
 * Class is a room service
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@Service("roomService")
public class RoomService implements RepositoryService<Room> {
    /**
     * Room repository
     */
    private final RoomRepository repository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    /**
     * Method add new room
     *
     * @param some
     * @return room
     */
    @Override
    public Room add(Room some) {
        return repository.save(some);
    }

    /**
     * Method of update room in repo
     *
     * @param some
     */
    @Override
    public void update(Room some) {
        if (repository.findById(some.getId()).isPresent()) {
            repository.save(some);
        }
    }

    /**
     * Method of delete room
     *
     * @param some
     */
    @Override
    public void delete(Room some) {
        if (repository.findById(some.getId()).isPresent()) {
            repository.delete(some);
        }
    }

    /**
     * Method return list of all rooms
     *
     * @return List
     */
    @Override
    public List<Room> findAll() {
        return repository.findAll();
    }

    /**
     * Method find room by given id
     *
     * @param id
     * @return Optional
     */
    @Override
    public Optional<Room> findById(int id) {
        return repository.findById(id);
    }
}
