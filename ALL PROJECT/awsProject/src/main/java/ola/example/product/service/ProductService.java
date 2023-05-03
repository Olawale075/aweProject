package ola.example.product.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ola.example.product.model.Product;

public interface ProductService {
    Product saveProduct(String title, String description, MultipartFile file);

    byte[] downloadProductImage(Long id);

    List<Product> getAllProducts();}
