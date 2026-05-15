package org.example.bankingapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.bankingapp.dto.LoginRequest;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;

    public String login(LoginRequest request) {

        if ("admin".equals(request.getUsername())
                && "admin123".equals(request.getPassword())) {

            return jwtService.generateToken("admin", "ROLE_ADMIN");
        }

        if ("user".equals(request.getUsername())
                && "user123".equals(request.getPassword())) {

            return jwtService.generateToken("user", "ROLE_USER");
        }

        throw new RuntimeException("Invalid credentials");
    }
}