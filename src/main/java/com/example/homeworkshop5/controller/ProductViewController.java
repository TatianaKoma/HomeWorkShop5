package com.example.homeworkshop5.controller;

import com.example.homeworkshop5.dto.ProductCreationDto;
import com.example.homeworkshop5.dto.ProductDto;
import com.example.homeworkshop5.mapper.ProductMapper;
import com.example.homeworkshop5.model.Product;
import com.example.homeworkshop5.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductViewController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper mapper;

    @RequestMapping("/getProducts")
    public String getAllProducts(Model model) {
        List<Product> products = productService.getProducts();
        List<ProductDto> productsDTO = products.stream().map(mapper::toProductDTO).collect(Collectors.toList());
        model.addAttribute("product", productsDTO);
        return "getProducts";
    }

    @RequestMapping("/addNewProduct")
    public String addNewProduct(Model model) {
        model.addAttribute("product", new ProductDto());
        return "productInfo";
    }

    @RequestMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute("product") ProductCreationDto productCreationDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "productInfo";
        } else {
            Product product = mapper.toProduct(productCreationDto);
            productService.createProduct(product);
            return "redirect:/getProducts";
        }
    }

    @RequestMapping("/updateProduct")
    public String updateProduct(@RequestParam("productId") int id, Model model) {
        Product product = productService.getProductById(id);
        ProductDto productDto = mapper.toProductDTO(product);
        model.addAttribute("updatedProduct", productDto);
        return "productUpdate";
    }

    @RequestMapping("/saveUpdatedProduct")
    public String saveUpdatedProduct(@Valid @ModelAttribute("updatedProduct") ProductDto productDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "productUpdate";
        } else {
            Product product = mapper.toProduct(productDto);
            productService.updateProductById(product.getId(), product);
            return "redirect:/getProducts";
        }
    }

    @RequestMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") int id) {
        productService.deleteProductById(id);
        return "redirect:/getProducts";
    }
}
