package com.example.homeworkshop5.service;

import com.example.homeworkshop5.model.Person;

import java.util.List;

public interface PersonService {

    Person createPerson(Person person);

    Person getPersonById(Integer id);

    List<Person> getPersons();

    Person updatePersonById(Integer id, Person person);

    void deletePersonById(Integer id);
}
