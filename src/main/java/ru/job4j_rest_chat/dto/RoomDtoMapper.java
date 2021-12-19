package ru.job4j_rest_chat.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.job4j_rest_chat.domain.Room;

@Mapper
public interface RoomDtoMapper {
    RoomDtoMapper INSTANCE = Mappers.getMapper(RoomDtoMapper.class);

    RoomDto roomEntityToRoomDto(final Room room);

    Room roomDtoToRoomEntity(final RoomDto roleDto);
}
