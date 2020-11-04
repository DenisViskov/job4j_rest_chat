package ru.job4j_rest_chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j_rest_chat.domain.Person;
import ru.job4j_rest_chat.repository.PersonRepository;

import java.util.Collections;

/**
 * Class is an user detail service
 *
 * @author Денис Висков
 * @version 1.0
 * @since 04.11.2020
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Repository
     */
    private final PersonRepository repository;

    @Autowired
    public UserDetailsServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person user = repository.findByLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new User(user.getLogin(), user.getPassword(), Collections.emptyList());
    }
}
