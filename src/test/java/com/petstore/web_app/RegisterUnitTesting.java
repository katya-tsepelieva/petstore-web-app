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

public class RegisterUnitTesting {
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
    void testRegisterUserSuccess() {
        String username = "newUser";
        String password = "passwordNew";
        String email = "newuser@gmail.com";

        when(userRepository.findByEmail(email)).thenReturn(null);
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(new User(username, password, email));

        String result = authController.register(username, password, email, session);

        assertEquals("redirect:/", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserEmailTaken() {
        String username = "newUser";
        String password = "passwordNew";
        String email = "newuser@gmail.com";

        when(userRepository.findByEmail(email)).thenReturn(new User(username, password, email));

        String result = authController.register(username, password, email, session);

        assertEquals("redirect:/register?error=email_taken", result);
    }

    @Test
    void testRegisterUserFailure() {
        String username = "newUser";
        String password = "passwordNew";
        String email = "newuser@gmail.com";

        when(userRepository.findByEmail(email)).thenReturn(null);
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(null);

        String result = authController.register(username, password, email, session);

        assertEquals("redirect:/login", result);
    }
}
