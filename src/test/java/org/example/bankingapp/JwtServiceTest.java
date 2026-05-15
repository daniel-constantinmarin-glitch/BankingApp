package org.example.bankingapp;

import org.example.bankingapp.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldGenerateTokenWithUsernameAndRole() {
        String token = jwtService.generateToken("admin", "ROLE_ADMIN");

        String username = jwtService.extractUsername(token);
        String role = jwtService.extractRole(token);

        assertEquals("admin", username);
        assertEquals("ROLE_ADMIN", role);
    }
}