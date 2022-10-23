package com.example.homeworkshop5.service.impl;

import com.example.homeworkshop5.exception.NotFoundException;
import com.example.homeworkshop5.model.Person;
import com.example.homeworkshop5.repository.PersonRepository;
import com.example.homeworkshop5.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.homeworkshop5.utils.ResponseMessages.PERSON_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Integer id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(PERSON_NOT_FOUND, id)));
    }

    @Override
    public Person updatePersonById(Integer id, Person person) {
        Person personForUpdate = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(PERSON_NOT_FOUND, id)));
        personForUpdate.setName(person.getName());
        personForUpdate.setSurname(person.getSurname());
        personForUpdate.setEmail(person.getEmail());
        personRepository.save(personForUpdate);
        return personForUpdate;
    }

    @Override
    public void deletePersonById(Integer id) {
        Person personForDelete = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(PERSON_NOT_FOUND, id)));
        personRepository.delete(personForDelete);
    }
}
