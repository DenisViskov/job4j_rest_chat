package ru.job4j_rest_chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.service.RepositoryService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Class is a rest person controller
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    /**
     * Service
     */
    private final RepositoryService<Person> service;

    @Autowired
    public PersonController(@Qualifier("personService") RepositoryService service) {
        this.service = service;
    }

    /**
     * @return all persons
     */
    @GetMapping("/")
    public List<Person> findAll() {
        final List<Person> all = service.findAll();
        if (all.isEmpty()) {
            throw new NoSuchElementException();
        }
        return all;
    }

    /**
     * @param id
     * @return person by given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") int id) {
        idGreaterThanZero(id);
        return service.findById(id)
            .map(person -> new ResponseEntity<>(person, HttpStatus.OK))
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person is not found. Please, check given id."
            ));
    }

    /**
     * Create new person
     *
     * @param person
     * @return Person
     */
    @PostMapping("/")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        idGreaterThanZero(person.getId());
        return new ResponseEntity<>(
            service.add(person),
            HttpStatus.OK
        );
    }

    /**
     * Update person
     *
     * @param id
     * @return void
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") int id) {
        idGreaterThanZero(id);
        operation(service::update, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete person by given id
     *
     * @param id
     * @return void
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") int id) {
        idGreaterThanZero(id);
        operation(service::delete, id);
        return ResponseEntity.ok().build();
    }

    private void operation(final Consumer<Person> operation, final int id) {
        service.findById(id)
            .ifPresentOrElse(operation, () -> {
                throw new NoSuchElementException();
            });
    }

    private void idGreaterThanZero(final int id) {
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
