package com.example.homeworkshop5.controller;

import com.example.homeworkshop5.dto.PersonRegistrationDto;
import com.example.homeworkshop5.mapper.PersonMapper;
import com.example.homeworkshop5.model.Person;
import com.example.homeworkshop5.service.PersonService;
import com.example.homeworkshop5.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Validated
public class RegistrationController {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonMapper mapper;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("personForm", new Person());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerPerson(@ModelAttribute("personForm") @Valid PersonRegistrationDto personRegistrationDto,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!personRegistrationDto.getPassword().equals(personRegistrationDto.getPasswordConfirm())) {
            model.addAttribute("passwordError", ResponseMessages.PASSWORDS_NOT_MATCH);
            return "registration";
        }
        if (!personService.savePerson(mapper.toPerson(personRegistrationDto))) {
            model.addAttribute("usernameError", ResponseMessages.USER_EXISTS);
            return "registration";
        }
        return "redirect:/";
    }
}
