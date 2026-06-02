package org.example.bankingapp.controller;

import org.example.bankingapp.dto.LoginRequest;
import org.example.bankingapp.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService service;


    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        log.info("Received login request for {}", request.getUsername());

        String token = service.login(request);

        log.debug("Token generated for {}", request.getUsername());

        return Map.of("token", token);
    }

}
