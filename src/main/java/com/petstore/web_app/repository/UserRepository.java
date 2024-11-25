package com.petstore.web_app.repository;

import com.petstore.web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password); // Для входу
    User findByEmail(String email); // Для перевірки email

}
