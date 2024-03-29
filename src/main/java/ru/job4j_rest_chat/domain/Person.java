package ru.job4j_rest_chat.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class is an entity Person
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@Entity
@Table(name = "persons")
public class Person {
    /**
     * Id
     */
    @Id
    @NotNull
    @Min(value = 1, message = "id must be at least 1")
    @Max(value = 10000, message = "id cannot greater than 10000")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Login
     */
    @NotBlank(message = "login is mandatory field")
    @Column(name = "login")
    private String login;
    /**
     * Password
     */
    @NotBlank(message = "password is mandatory")
    @Column(name = "password")
    private String password;
    /**
     * Role
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Person() {
    }

    public Person(int id, String login, String password, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                Objects.equals(login, person.login) &&
                Objects.equals(password, person.password) &&
                Objects.equals(role, person.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role);
    }
}
