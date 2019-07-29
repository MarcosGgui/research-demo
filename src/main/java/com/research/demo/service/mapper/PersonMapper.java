package com.research.demo.service.mapper;

import com.research.demo.domain.Person;
import com.research.demo.service.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity Person and its DTO PersonDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {

    @Mapping(target = "name", source = "entity", qualifiedByName = {"EnglishToGerman"})
    PersonDTO toDto(Person entity);

    default Person fromId(Long id) {
        if (id == null) {
            return null;
        }
        Person person = new Person();
        person.setId(id);
        return person;
    }


    @Named("EnglishToGerman")
    default String translateTitleEG(Person source) {
        return source.toString();
    }
}
