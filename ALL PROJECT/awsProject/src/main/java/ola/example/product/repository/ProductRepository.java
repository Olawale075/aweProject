package ola.example.product.repository;

import org.springframework.data.repository.CrudRepository;

import ola.example.product.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByTitle(String title);
}