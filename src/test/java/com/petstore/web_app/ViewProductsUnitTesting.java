package com.petstore.web_app;

import com.petstore.web_app.controller.ProductViewController;
import com.petstore.web_app.model.Product;
import com.petstore.web_app.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ViewProductsUnitTesting {

    @InjectMocks
    private ProductViewController productController;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Captor
    private ArgumentCaptor<List<Product>> productCaptor;

    @Test
    public void testViewAllProducts() {
        Product product1 = new Product("Testing Product 1", new BigDecimal(100.0));
        Product product2 = new Product("Testing Product 2", new BigDecimal(200.0));
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        String result = productController.viewProducts(model, null, null);

        assertEquals("productList", result);
        verify(model).addAttribute(eq("products"), productCaptor.capture());

        assertEquals(products, productCaptor.getValue());
    }

    @Test
    public void testViewProductsByCategory() {
        String category = "Toys";
        Product product1 = new Product("Testing Product 1", new BigDecimal(100.0), category);
        Product product2 = new Product("Testing Product 2", new BigDecimal(200.0), category);
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findByCategory(category)).thenReturn(products);

        String result = productController.viewProducts(model, category, null);

        assertEquals("productList", result);
        verify(productRepository).findByCategory(category);
        verify(model).addAttribute("products", products);
    }

    @Test
    public void testViewProductsSortedByPrice() {
        Product product1 = new Product("Testing Product 1", new BigDecimal(300.0));
        Product product2 = new Product("Testing Product 2", new BigDecimal(100.0));
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        String result = productController.viewProducts(model, null, "price");

        assertEquals("productList", result);
        verify(productRepository).findAll();
        verify(model).addAttribute("products", Arrays.asList(product2, product1));
    }

    @Test
    public void testViewProductsSortedByName() {
        Product product1 = new Product("B Testing Product", new BigDecimal(300.0));
        Product product2 = new Product("A Testing Product", new BigDecimal(100.0));
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        String result = productController.viewProducts(model, null, "name");

        assertEquals("productList", result);
        verify(productRepository).findAll();
        verify(model).addAttribute("products", Arrays.asList(product2, product1));
    }
}
