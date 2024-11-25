package com.petstore.web_app;

import com.petstore.web_app.model.Product;
import com.petstore.web_app.repository.ProductRepository;
import com.petstore.web_app.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductServiceIntegrationTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testFindProductByIdMocked() {
        Product mockProduct = new Product("Cat Toy",  new BigDecimal( 19.99));
        mockProduct.setId(1L);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        Optional<Product> result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Cat Toy", result.get().getName());
    }
}

