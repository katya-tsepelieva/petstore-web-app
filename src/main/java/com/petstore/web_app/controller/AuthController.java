package com.petstore.web_app.controller;

import com.petstore.web_app.model.User;
import com.petstore.web_app.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);  // Створюємо логер

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Форма реєстрації
    @GetMapping("/register")
    public String showRegistrationForm() {
        logger.info("Displaying registration form.");
        return "register";
    }

    // Обробка реєстрації
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, HttpSession session) {
        logger.info("Attempting to register user: {}", username);

        // Перевірка чи вже існує користувач з таким email
        if (userRepository.findByEmail(email) != null) {
            logger.warn("Email {} is already taken.", email);
            return "redirect:/register?error=email_taken"; // Повідомлення про те, що email вже використовується
        }

        // Створюємо нового користувача
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Пароль у реальному додатку потрібно хешувати
        newUser.setEmail(email); // Додаємо email
        userRepository.save(newUser);  // Зберігаємо нового користувача

        // Після збереження нового користувача автоматично логінемо його
        User user = userRepository.findByUsernameAndPassword(username, password); // Перевірка логіна та пароля
        if (user != null) {
            logger.info("User {} registered successfully.", username);
            session.setAttribute("loggedInUser", user); // Зберігаємо користувача в сесії
            return "redirect:/"; // Перенаправляємо на головну сторінку після входу
        }

        logger.warn("Registration failed for user: {}", username);
        return "redirect:/login"; // Якщо користувач не знайдений, перенаправляємо на сторінку входу
    }

    // Форма входу
    @GetMapping("/login")
    public String showLoginForm() {
        logger.info("Displaying login form.");
        return "login";
    }

    // Обробка входу
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String email, HttpSession session) {
        logger.info("Attempting to login with username: {}", username);

        // Якщо email передано, спробуємо знайти користувача за email
        User user;
        if (email != null && !email.isEmpty()) {
            user = userRepository.findByEmail(email); // Пошук по email
        } else {
            user = userRepository.findByUsernameAndPassword(username, password); // Пошук по username
        }

        if (user != null && user.getPassword().equals(password)) { // Перевірка пароля
            logger.info("User {} logged in successfully.", username);
            session.setAttribute("loggedInUser", user); // Зберігаємо користувача в сесії
            return "redirect:/"; // Перенаправляємо на головну сторінку
        }

        logger.warn("Invalid login attempt for username: {}", username);
        return "login"; // Якщо не знайдено, показуємо форму знову
    }

    // Вихід з системи
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("Logging out user.");
        session.invalidate(); // Виходимо з системи
        return "redirect:/"; // Перенаправляємо на головну
    }
}