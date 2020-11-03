package ru.job4j_rest_chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@Service("personService")
public class PersonService implements RepositoryService<Person> {
    private final PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person add(Person some) {
        return repository.save(some);
    }

    @Override
    public void update(Person some) {
        if (repository.findById(some.getId()).isPresent()) {
            repository.save(some);
        }
    }

    @Override
    public void delete(Person some) {
        if (repository.findById(some.getId()).isPresent()) {
            repository.delete(some);
        }
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }
}
