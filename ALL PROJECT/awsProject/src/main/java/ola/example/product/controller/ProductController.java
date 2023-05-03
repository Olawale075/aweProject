package ola.example.product.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import ola.example.product.model.Product;
import ola.example.product.service.ProductService;

@RestController
@RequestMapping("api/v1/product")
@AllArgsConstructor
@CrossOrigin("*")
public class ProductController {
    ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping(
            path = "/ola",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Product> saveProduct(@RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(service.saveProduct(title, description, file), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/image/download")
    public byte[] downloadProductImage(@PathVariable("id") Long id) {
        return service.downloadProductImage(id);
    }
}
