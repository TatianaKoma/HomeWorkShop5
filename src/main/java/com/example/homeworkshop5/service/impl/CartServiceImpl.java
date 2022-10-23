package com.example.homeworkshop5.service.impl;

import com.example.homeworkshop5.exception.NotFoundException;
import com.example.homeworkshop5.model.Cart;
import com.example.homeworkshop5.model.Person;
import com.example.homeworkshop5.model.Product;
import com.example.homeworkshop5.repository.CartRepository;
import com.example.homeworkshop5.repository.PersonRepository;
import com.example.homeworkshop5.repository.ProductRepository;
import com.example.homeworkshop5.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.example.homeworkshop5.utils.ResponseMessages.CART_NOT_FOUND;
import static com.example.homeworkshop5.utils.ResponseMessages.PERSON_NOT_FOUND;
import static com.example.homeworkshop5.utils.ResponseMessages.PRODUCT_EXISTS;
import static com.example.homeworkshop5.utils.ResponseMessages.PRODUCT_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;

    @Override
    public Cart createCart(Cart cart) {
        Person person = personRepository.findById(cart.getPerson().getId())
                .orElseThrow(() -> new NotFoundException(String.format(PERSON_NOT_FOUND, cart.getPerson().getId())));
        if (Objects.equals(person.getId(), cart.getPerson().getId())) {
            cart.setSum(new BigDecimal(0));
            cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCartById(Integer id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(CART_NOT_FOUND, id)));
    }

    @Override
    public Cart addProductToCartById(Integer id, Integer productId) {
        Cart cartForUpdate = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(CART_NOT_FOUND, id)));

        List<Product> cartProducts = cartForUpdate.getProducts();
        Product newProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format(PRODUCT_NOT_EXISTS)));

        if (cartProducts.contains(newProduct)) {
            throw new IllegalArgumentException(PRODUCT_EXISTS);
        } else {
            cartProducts.add(newProduct);
            BigDecimal sum = cartProducts.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            cartForUpdate.setProducts(cartProducts);
            cartForUpdate.setSum(sum);
            cartRepository.save(cartForUpdate);
        }
        return cartForUpdate;
    }

    @Override
    public List<Product> getListProductsByCartId(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(CART_NOT_FOUND, id)));
        return cart.getProducts();
    }

    @Override
    public void deleteCartById(Integer id) {
        Cart cartForDelete = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(CART_NOT_FOUND, id)));
        cartRepository.delete(cartForDelete);
    }
}
