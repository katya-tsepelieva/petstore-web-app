package com.petstore.web_app;

import com.petstore.web_app.model.Product;
import com.petstore.web_app.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
        productRepository.save(new Product("Cat Food", new BigDecimal("10.99")));
        productRepository.save(new Product("Dog Toy", new BigDecimal("5.48")));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Cat Food"))
                .andExpect(jsonPath("$[0].price").value(10.99))
                .andExpect(jsonPath("$[1].name").value("Dog Toy"))
                .andExpect(jsonPath("$[1].price").value(5.49));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = productRepository.save(new Product("Bird Cage", new BigDecimal("29.99")));

        mockMvc.perform(get("/api/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bird Cage"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").value("error"))
                .andExpect(jsonPath("$.description").value("Product not found"))
                .andExpect(jsonPath("$.price").value(0))
                .andExpect(jsonPath("$.stock").value(0))
                .andExpect(jsonPath("$.imageUrl").value(""));
    }



}
