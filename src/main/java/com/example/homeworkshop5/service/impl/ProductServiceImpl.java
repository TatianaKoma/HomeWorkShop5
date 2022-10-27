package com.example.homeworkshop5.service.impl;

import com.example.homeworkshop5.exception.NotFoundException;
import com.example.homeworkshop5.model.Product;
import com.example.homeworkshop5.repository.ProductRepository;
import com.example.homeworkshop5.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.homeworkshop5.ResponseMessages.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(PRODUCT_NOT_FOUND, id)));
    }

    @Override
    public Product updateProductById(Integer id, Product product) {
        Product productForUpdate = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(PRODUCT_NOT_FOUND, id)));
        productForUpdate.setName(product.getName());
        productForUpdate.setPrice(product.getPrice());
        productRepository.save(productForUpdate);
        return productForUpdate;
    }

    @Override
    public void deleteProductById(Integer id) {
        Product productForDelete = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(PRODUCT_NOT_FOUND, id)));
        productRepository.delete(productForDelete);
    }
}
