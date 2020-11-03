package ru.job4j_rest_chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j_rest_chat.domain.Room;
import ru.job4j_rest_chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@Service("roomService")
public class RoomService implements RepositoryService<Room> {
    private final RoomRepository repository;

    @Autowired
    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    @Override
    public Room add(Room some) {
        return repository.save(some);
    }

    @Override
    public void update(Room some) {
        if (repository.findById(some.getId()).isPresent()) {
            repository.save(some);
        }
    }

    @Override
    public void delete(Room some) {
        if (repository.findById(some.getId()).isPresent()) {
            repository.delete(some);
        }
    }

    @Override
    public List<Room> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Room> findById(int id) {
        return repository.findById(id);
    }
}
