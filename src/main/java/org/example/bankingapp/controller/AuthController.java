package org.example.bankingapp.controller;

import org.example.bankingapp.dto.LoginRequest;
import org.example.bankingapp.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        return Map.of("token", service.login(request));
    }
}
