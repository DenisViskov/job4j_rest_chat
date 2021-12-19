package ru.job4j_rest_chat.dto;

import lombok.*;

@Data
@Builder
public class RoleDto {
    private int id;

    private String name;
}
