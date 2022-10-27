package com.example.homeworkshop5.controller;

import com.example.homeworkshop5.dto.PersonCreationDto;
import com.example.homeworkshop5.dto.PersonDto;
import com.example.homeworkshop5.mapper.PersonMapper;
import com.example.homeworkshop5.model.Person;
import com.example.homeworkshop5.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Validated
public class PersonViewController {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonMapper mapper;

    @RequestMapping("/getPersons")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAllPersons(Model model) {
        List<Person> persons = personService.getPersons();
        List<PersonDto> personDto = persons.stream().map(mapper::toPersonDTO).collect(Collectors.toList());
        model.addAttribute("person", personDto);
        return "getPersons";
    }

    @RequestMapping("/addNewPerson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addNewPerson(Model model) {
        model.addAttribute("person", new PersonDto());
        return "personInfo";
    }

    @RequestMapping("/savePerson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String savePerson(@Valid @ModelAttribute("person") PersonCreationDto personCreationDto,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "personInfo";
        } else {
            Person person = mapper.toPerson(personCreationDto);
            personService.createPerson(person);
            return "redirect:/getPersons";
        }
    }

    @RequestMapping("/updatePerson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updatePerson(@RequestParam("personId") int id, Model model) {
        Person person = personService.getPersonById(id);
        PersonDto personDto = mapper.toPersonDTO(person);
        model.addAttribute("updatedPerson", personDto);
        return "personUpdate";
    }

    @RequestMapping("/saveUpdatedPerson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveUpdatedPerson(@Valid @ModelAttribute("updatedPerson") PersonDto personDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "personUpdate";
        } else {
            Person person = mapper.toPerson(personDto);
            personService.updatePersonById(person.getId(), person);
            return "redirect:/getPersons";
        }
    }

    @RequestMapping("/deletePerson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletePerson(@RequestParam("personId") int id) {
        personService.deletePersonById(id);
        return "redirect:/getPersons";
    }
}
