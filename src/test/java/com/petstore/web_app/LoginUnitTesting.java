package com.petstore.web_app;

import com.petstore.web_app.controller.AuthController;
import com.petstore.web_app.model.User;
import com.petstore.web_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginUnitTesting {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
    }

    @Test
    void testLoginUserSuccessByUsername() {
        String username = "existingUser";
        String password = "passwordUser";
        User user = new User(username, password, "user@gmail.com");

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(user);

        String result = authController.login(username, password, null, session);

        assertEquals("redirect:/", result);
        verify(userRepository, times(1)).findByUsernameAndPassword(username, password);
    }

    @Test
    void testLoginUserSuccessByEmail() {
        String email = "user@gmail.com";
        String password = "passwordUser";
        User user = new User("existingUser", password, email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        String result = authController.login(null, password, email, session);

        assertEquals("redirect:/", result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testLoginUserFailure() {
        // Arrange
        String username = "nonExistingUser";
        String password = "passwordUser";

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(null);

        String result = authController.login(username, password, null, session);

        assertEquals("login", result);
    }

    @Test
    void testLoginUserFailureByEmail() {
        String email = "errorEmail@gmail.com";
        String password = "passwordUser";

        when(userRepository.findByEmail(email)).thenReturn(null);

        String result = authController.login(null, password, email, session);

        assertEquals("login", result);
    }
}
