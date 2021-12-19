package ru.job4j_rest_chat.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.job4j_rest_chat.domain.Person;

@Mapper
public interface PersonDtoMapper {
    PersonDtoMapper INSTANCE = Mappers.getMapper(PersonDtoMapper.class);

    PersonDto personEntityToPersonDto(final Person person);

    Person personDtoToPersonEntity(final PersonDto personDto);
}
