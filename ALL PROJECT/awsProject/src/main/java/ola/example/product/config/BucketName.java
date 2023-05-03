package ola.example.product.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    Product_IMAGE("olawa-image");
    private final String bucketName;
}
