package ru.job4j_rest_chat.domain;

import java.util.Objects;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 03.11.2020
 */
public class Message {
    private final int id;
    private final String content;
    private final Person author;

    public Message(int id, String content, Person author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Person getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id &&
                Objects.equals(content, message.content) &&
                Objects.equals(author, message.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, author);
    }
}
