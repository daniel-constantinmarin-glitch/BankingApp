package org.example.bankingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import org.example.bankingapp.dto.LoginRequest;
import org.example.bankingapp.security.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService service;

    public AuthController(AuthenticationService service) {
        this.service = service;
    }

    @Operation(summary = "Login and receive JWT token")

    @ApiResponse(
            responseCode = "200",
            description = "Successful login",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{\"token\": \"eyJhbGciOiJIUzI1NiJ9...\"}"
                    )
            )
    )

    @ApiResponse(responseCode = "401", description = "Invalid credentials")

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String token = service.login(request);
        return Map.of("token", token);
    }
}
