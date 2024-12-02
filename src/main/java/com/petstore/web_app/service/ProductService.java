package com.petstore.web_app.service;

import com.petstore.web_app.model.Product;
import com.petstore.web_app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id); // Assuming productRepository returns Optional<Product>
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        // Використовуємо orElseThrow, щоб викинути виняток, якщо продукт не знайдено
        Product existingProduct = getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());
        existingProduct.setCategory(updatedProduct.getCategory());

        return productRepository.save(existingProduct);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
