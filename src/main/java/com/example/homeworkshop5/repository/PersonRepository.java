package com.example.homeworkshop5.repository;

import com.example.homeworkshop5.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findPersonByUsername(String username);
}
