package ru.job4j_rest_chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j_rest_chat.domain.Room;

/**
 * Интерфейс реализующий способность
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
