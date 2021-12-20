package ru.job4j_rest_chat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
public class RoleDto {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;
}
