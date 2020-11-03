package ru.job4j_rest_chat.service;

import java.util.List;
import java.util.Optional;

/**
 * Interface of repository service
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
public interface RepositoryService<V> {
    /**
     * Method should add some to store
     *
     * @param some
     * @return V
     */
    V add(V some);

    /**
     * Method should update some in repository
     *
     * @param some
     */
    void update(V some);

    /**
     * Method should delete some from repository
     *
     * @param some
     */
    void delete(V some);

    /**
     * Method should return list all elements from repository
     *
     * @return List
     */
    List<V> findAll();

    /**
     * Method should return some from repository by given id
     *
     * @param id
     * @return Optional
     */
    Optional<V> findById(int id);
}
