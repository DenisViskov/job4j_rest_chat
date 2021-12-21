package ru.job4j_rest_chat.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class is an entity room
 *
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
@Entity
@Table(name = "rooms")
public class Room {
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
     * Name
     */
    @NotBlank
    @Size(min = 1, max = 10, message = "Name must be between 1 to 10 characters")
    @Column(name = "name")
    private String name;
    /**
     * Persons
     */
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Person> persons;

    public Room() {
        persons = new HashSet<>();
    }

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
        this.persons = new HashSet<>();
    }

    /**
     * Add person to room
     *
     * @param person
     */
    public void addPerson(Person person) {
        persons.add(person);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                Objects.equals(name, room.name) &&
                Objects.equals(persons, room.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, persons);
    }
}
