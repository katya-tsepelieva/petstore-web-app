package com.petstore.web_app.controller;

import com.petstore.web_app.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private final ProductRepository productRepository;

    public ProductViewController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String showProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }
}

