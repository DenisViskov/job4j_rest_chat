package ru.job4j_rest_chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.repository.PersonRepository;
import ru.job4j_rest_chat.service.RepositoryService;

import java.util.List;

/**
 * Class is a rest user controller
 *
 * @author Денис Висков
 * @version 1.0
 * @since 04.11.2020
 */
@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * Person service
     */
    private final RepositoryService personService;
    /**
     * Encoder
     */
    private final BCryptPasswordEncoder encoder;

    public UserController(@Qualifier("personService") RepositoryService personService,
                          BCryptPasswordEncoder encoder) {
        this.personService = personService;
        this.encoder = encoder;
    }

    /**
     * Register person
     *
     * @param person
     */
    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        personService.add(person);
    }

    /**
     * @return all persons
     */
    @GetMapping("/all")
    public List<Person> findAll() {
        return personService.findAll();
    }
}
