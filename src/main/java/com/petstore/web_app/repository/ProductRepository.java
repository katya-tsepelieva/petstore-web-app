package com.petstore.web_app.repository;

import com.petstore.web_app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
