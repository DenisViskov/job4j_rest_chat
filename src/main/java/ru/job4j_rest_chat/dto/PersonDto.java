package ru.job4j_rest_chat.dto;

import lombok.*;

@Data
@Builder
public class PersonDto {
    private int id;

    private String login;

    private String password;

    private RoleDto role;
}
