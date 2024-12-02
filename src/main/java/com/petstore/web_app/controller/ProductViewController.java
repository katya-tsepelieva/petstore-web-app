package com.petstore.web_app.controller;

import com.petstore.web_app.model.Product;
import com.petstore.web_app.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private static final Logger logger = LoggerFactory.getLogger(ProductViewController.class);

    private final ProductRepository productRepository;

    public ProductViewController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String viewProducts(Model model,
                               @RequestParam(name = "category", required = false) String category,
                               @RequestParam(name = "sortBy", required = false) String sortBy) {
        logger.info("Viewing products with category: {} and sortBy: {}", category, sortBy);

        // Отримуємо продукти, залежно від категорії
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findAll();
        }

        // Сортуємо продукти
        if ("price".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else if ("name".equalsIgnoreCase(sortBy)) {
            products.sort(Comparator.comparing(Product::getName));
        }

        // Додаємо дані в модель
        model.addAttribute("products", products);
        model.addAttribute("categories", productRepository.findDistinctCategories());  // Перевірте, чи ця функція правильно повертає категорії
        model.addAttribute("selectedCategory", category);
        model.addAttribute("sortBy", sortBy);

        return "products";
    }

}


