package ola.example.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import static org.apache.http.entity.ContentType.IMAGE_GIF;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;
import static org.apache.http.entity.ContentType.IMAGE_BMP;
//import static org.apache.http.entity.ContentType.Image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import lombok.AllArgsConstructor;
import ola.example.product.config.BucketName;
import ola.example.product.model.Product;
import ola.example.product.repository.ProductRepository;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final FileStore fileStore;
    private final ProductRepository repository;

    @Override
    public Product saveProduct(String title, String description, MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        //Check if the file is an image
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_JPEG.getMimeType(),
                IMAGE_GIF.getMimeType()
                ).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Product in the database
        String path = String.format("%s/%s", BucketName.Product_IMAGE.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        Product product = Product.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .imageFileName(fileName)
                .build();
        repository.save(product);
        return repository.findByTitle(product.getTitle());
    }

    @Override
    public byte[] downloadProductImage(Long id) {
        Product product = repository.findById(id).get();
        return fileStore.download(product.getImagePath(), product.getImageFileName());
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        repository.findAll().forEach(products::add);
        return products;
    }
}
