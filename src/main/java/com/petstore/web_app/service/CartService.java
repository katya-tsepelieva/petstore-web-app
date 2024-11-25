package com.petstore.web_app.service;

import com.petstore.web_app.model.Cart;
import com.petstore.web_app.model.Product;
import com.petstore.web_app.model.User;
import com.petstore.web_app.repository.CartRepository;
import com.petstore.web_app.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<Cart> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    @Transactional
    public void addToCart(User user, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Перевіряємо, чи товар вже є в кошику
        Cart existingCart = cartRepository.findByUserAndProduct(user, product);
        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setProduct(product);
            newCart.setQuantity(quantity);
            cartRepository.save(newCart);
        }
    }

    @Transactional
    public void removeFromCart(User user, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        cartRepository.deleteByUserAndProduct(user, product);
    }

    public int getTotalItems(User user) {
        return cartRepository.findByUser(user).stream()
                .mapToInt(Cart::getQuantity)
                .sum();
    }

    public double getTotalPrice(User user) {
        return cartRepository.findByUser(user).stream()
                .mapToDouble(cart -> cart.getProduct().getPrice().doubleValue() * cart.getQuantity())
                .sum();
    }
}