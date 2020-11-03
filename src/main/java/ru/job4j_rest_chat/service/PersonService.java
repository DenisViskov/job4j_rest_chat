package ru.job4j_rest_chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.domain.Role;
import ru.job4j_rest_chat.repository.PersonRepository;
import ru.job4j_rest_chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Class is a person service
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@Service("personService")
public class PersonService implements RepositoryService<Person> {
    /**
     * Person repository
     */
    private final PersonRepository repository;
    /**
     * Role repository
     */
    private final RoleRepository roleRepository;

    @Autowired
    public PersonService(PersonRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    /**
     * Add new person to store
     *
     * @param some
     * @return Person
     */
    @Override
    public Person add(Person some) {
        Role role_user = roleRepository.save(new Role(1, "ROLE_USER"));
        some.setRole(role_user);
        return repository.save(some);
    }

    /**
     * Update person
     *
     * @param some
     */
    @Override
    public void update(Person some) {
        if (repository.findById(some.getId()).isPresent()) {
            repository.save(some);
        }
    }

    /**
     * Delete person
     *
     * @param some
     */
    @Override
    public void delete(Person some) {
        if (repository.findById(some.getId()).isPresent()) {
            repository.delete(some);
        }
    }

    /**
     * Return all persons
     *
     * @return List
     */
    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    /**
     * Return person by given id
     *
     * @param id
     * @return Optional
     */
    @Override
    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }
}
