package org.example.bankingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Operation(summary = "Admin-only endpoint")
    @ApiResponse(responseCode = "200", description = "Access granted")
    @ApiResponse(responseCode = "403", description = "Forbidden")

    @GetMapping
    public String adminOnly() {
        return "Hello Admin!";
    }
}