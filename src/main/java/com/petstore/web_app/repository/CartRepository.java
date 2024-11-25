package com.petstore.web_app.repository;

import com.petstore.web_app.model.Cart;
import com.petstore.web_app.model.Product;
import com.petstore.web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Cart findByUserAndProduct(User user, Product product);
    void deleteByUserAndProduct(User user, Product product);
}
