package com.example.homeworkshop5.service;

import com.example.homeworkshop5.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getProducts();

    Product getProductById(Integer id);

    Product updateProductById(Integer id, Product product);

    void deleteProductById(Integer id);
}
