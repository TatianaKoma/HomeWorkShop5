package com.example.homeworkshop5.service.impl;

import com.example.homeworkshop5.exception.NotFoundException;
import com.example.homeworkshop5.model.Person;
import com.example.homeworkshop5.model.Role;
import com.example.homeworkshop5.repository.PersonRepository;
import com.example.homeworkshop5.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.example.homeworkshop5.ResponseMessages.PERSON_NOT_FOUND;
import static com.example.homeworkshop5.ResponseMessages.PERSON_NOT_FOUND_BY_USERNAME;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public boolean savePerson(Person person) {
        Person personFromDB = personRepository.findPersonByUsername(person.getUsername());
        if (personFromDB != null) {
            return false;
        }
        person.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        personRepository.save(person);
        return true;
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

    @Override
    public Person getPersonByUsername(String username) {
        Person person = personRepository.findPersonByUsername(username);
        if (person == null) {
            throw new NotFoundException(String.format(PERSON_NOT_FOUND_BY_USERNAME, username));
        } else {
            return person;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = getPersonByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException(String.format(PERSON_NOT_FOUND_BY_USERNAME, username));
        }
        return person;
    }
}
