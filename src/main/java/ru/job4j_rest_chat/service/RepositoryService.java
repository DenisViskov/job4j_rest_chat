package ru.job4j_rest_chat.service;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс реализующий способность
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
public interface RepositoryService<V> {
    V add(V some);
    void update(V some);
    void delete(V some);
    List<V> findAll();
    Optional<V> findById(int id);
}
