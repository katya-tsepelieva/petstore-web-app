package com.petstore.web_app.repository;

import com.petstore.web_app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategories();
}
