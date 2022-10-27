package com.example.homeworkshop5.controller;

import com.example.homeworkshop5.dto.CartCreationDto;
import com.example.homeworkshop5.dto.CartDto;
import com.example.homeworkshop5.dto.CartUpdateDto;
import com.example.homeworkshop5.dto.ProductDto;
import com.example.homeworkshop5.mapper.CartMapper;
import com.example.homeworkshop5.mapper.ProductMapper;
import com.example.homeworkshop5.model.Cart;
import com.example.homeworkshop5.model.Product;
import com.example.homeworkshop5.service.CartService;
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
public class CartViewController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @RequestMapping("/getCarts")
    @PreAuthorize("isAuthenticated()")
    public String getAllCarts(Model model) {
        List<Cart> carts = cartService.getCarts();
        List<CartDto> cartDto = carts.stream().map(cartMapper::toCartDTO).collect(Collectors.toList());
        model.addAttribute("cart", cartDto);
        return "getCarts";
    }

    @RequestMapping("/addNewCart")
    @PreAuthorize("isAuthenticated()")
    public String addNewCart(Model model) {
        model.addAttribute("cart", new CartDto());
        return "cartInfo";
    }

    @RequestMapping("/saveCart")
    @PreAuthorize("isAuthenticated()")
    public String saveCart(@Valid @ModelAttribute("cart") CartCreationDto cartCreationDto,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cartInfo";
        } else {
            Cart cart = cartMapper.toCart(cartCreationDto);
            cartService.createCart(cart);
            return "redirect:/getCarts";
        }
    }

    @RequestMapping("/addProductToCart")
    @PreAuthorize("isAuthenticated()")
    public String updateCart(@RequestParam("cartId") int id, Model model) {
        Cart cart = cartService.getCartById(id);
        CartDto cartDto = cartMapper.toCartDTO(cart);
        model.addAttribute("updatedCart", cartDto);
        return "cartUpdate";
    }

    @RequestMapping("/saveProductToCart")
    @PreAuthorize("isAuthenticated()")
    public String saveProductToCart(@Valid @ModelAttribute("updatedCart") CartUpdateDto cartUpdateDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "cartUpdate";
        } else {
            cartService.addProductToCartById(cartUpdateDto.getId(), cartUpdateDto.getProductsId());
            return "redirect:/getCarts";
        }
    }

    @RequestMapping("/listProducts")
    @PreAuthorize("isAuthenticated()")
    public String getAllProductsFromCart(@RequestParam("cartId") int id, Model model) {
        List<Product> productList = cartService.getListProductsByCartId(id);
        List<ProductDto> productDtos = productList.stream().map(productMapper::toProductDTO).collect(Collectors.toList());
        model.addAttribute("listProducts", productDtos);
        return "getListProducts";
    }

    @RequestMapping("/deleteCart")
    @PreAuthorize("isAuthenticated()")
    public String deleteCart(@RequestParam("cartId") int id) {
        cartService.deleteCartById(id);
        return "redirect:/getCarts";
    }
}
