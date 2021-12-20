package ru.job4j_rest_chat.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.job4j_rest_chat.domain.Role;

@Mapper
public interface RoleDtoMapper {
    RoleDtoMapper INSTANCE = Mappers.getMapper(RoleDtoMapper.class);

    RoleDto roleEntityToRoleDto(final Role role);

    Role roleDtoToRoleEntity(final RoleDto roleDto);
}
