package com.permisback.permisBack.Test;

import com.permisback.permisBack.Entities.User;
import com.permisback.permisBack.Repositories.UserRepository;
import com.permisback.permisBack.Services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanup() {
        userRepository.deleteAll();
        User user = new User("testuser", "testpass");
        userRepository.save(user);
    }

    @Test
    void testAuthenticationSuccess() {
        AuthService.AuthResponse response = authService.authenticate("testuser", "testpass");

        assertTrue(response.isSuccess());
        assertFalse(response.isLocked());
    }

    @Test
    void testAuthenticationFailure() {
        AuthService.AuthResponse response = authService.authenticate("testuser", "wrongpass");

        assertFalse(response.isSuccess());
        assertFalse(response.isLocked());
    }

    @Test
    void testAccountLockAfterThreeFailures() {
        // Trois tentatives échouées
        authService.authenticate("testuser", "wrongpass1");
        authService.authenticate("testuser", "wrongpass2");
        AuthService.AuthResponse response = authService.authenticate("testuser", "wrongpass3");

        assertTrue(response.isLocked());
    }
}
