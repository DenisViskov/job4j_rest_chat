package ru.job4j_rest_chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.service.RepositoryService;

import java.util.List;
import java.util.Optional;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    private final RepositoryService service;

    @Autowired
    public PersonController(@Qualifier("personService") RepositoryService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") int id) {
        Optional<Person> box = service.findById(id);
        if (box.isPresent()) {
            return new ResponseEntity<>(
                    box.get(),
                    HttpStatus.OK
            );
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return new ResponseEntity<>(
                (Person) service.add(person),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") int id) {
        Optional<Person> box = service.findById(id);
        if (box.isPresent()) {
            service.update(box.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") int id) {
        Optional<Person> box = service.findById(id);
        if (box.isPresent()) {
            service.delete(box.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
