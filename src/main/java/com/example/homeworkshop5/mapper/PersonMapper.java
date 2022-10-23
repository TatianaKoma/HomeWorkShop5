package com.example.homeworkshop5.mapper;

import com.example.homeworkshop5.dto.PersonCreationDto;
import com.example.homeworkshop5.dto.PersonDto;
import com.example.homeworkshop5.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {

    public Person toPerson(PersonCreationDto personCreationDTO) {
        Person person = new Person();
        person.setName(personCreationDTO.getName());
        person.setSurname(personCreationDTO.getSurname());
        person.setEmail(personCreationDTO.getEmail());
        return person;
    }

    public Person toPerson(PersonDto personDTO) {
        Person person = new Person();
        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
        person.setSurname(personDTO.getSurname());
        person.setEmail(personDTO.getEmail());
        return person;
    }

    public PersonDto toPersonDTO(Person person) {
        return new PersonDto(person.getId(), person.getName(), person.getSurname(), person.getEmail());
    }
}
