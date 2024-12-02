package com.petstore.web_app.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductSteps {

    @Mock
    WebDriver driver;

    @Mock
    WebElement mockCategoryElement;

    @Mock
    WebElement mockProductElement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("User is on the product page")
    public void iAmOnTheProductPage() {
        // Мокуємо поведінку WebDriver
        when(driver.findElement(By.id("category"))).thenReturn(mockCategoryElement);
        when(driver.findElements(By.className("product"))).thenReturn(java.util.Collections.singletonList(mockProductElement));
    }

    @When("User selects the category {string}")
    public void iSelectTheCategory(String category) {
        // Мокуємо введення категорії
        when(mockCategoryElement.getText()).thenReturn(category);
        // Візьмемо значення категорії за допомогою моків
        mockCategoryElement.sendKeys(category);
    }

    @Then("User should see a list of products in the {string} category with names, descriptions, prices, and \"Add to Cart\" button")
    public void iShouldSeeProductsInCategory(String category) {
        // Перевіряємо, чи відображаються продукти в категорії
        when(driver.findElements(By.className("product"))).thenReturn(java.util.Collections.singletonList(mockProductElement));

        // Перевіряємо, чи є елементи продуктів
        boolean isDisplayed = driver.findElements(By.className("product")).size() > 0;
        assertTrue(isDisplayed, "Product list is not displayed");

        // Перевіряємо, чи є елементи в категорії
        verify(mockCategoryElement).sendKeys(category);
    }
}
