package com.example.homeworkshop5.mapper;

import com.example.homeworkshop5.dto.CartCreationDto;
import com.example.homeworkshop5.dto.CartDto;
import com.example.homeworkshop5.exception.NotFoundException;
import com.example.homeworkshop5.model.Cart;
import com.example.homeworkshop5.model.Person;
import com.example.homeworkshop5.model.Product;
import com.example.homeworkshop5.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.homeworkshop5.utils.ResponseMessages.PERSON_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final PersonRepository personRepository;

    public Cart toCart(CartCreationDto cartCreationDto) {
        Integer personId = cartCreationDto.getPersonId();
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException(String.format(PERSON_NOT_FOUND, personId)));
        Cart cart = new Cart();
        cart.setPerson(person);
        return cart;
    }

    public CartDto toCartDTO(Cart cart) {
        List<Integer> productsId = cart.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        return new CartDto(cart.getId(), cart.getPerson().getId(), cart.getSum(), productsId);
    }
}
