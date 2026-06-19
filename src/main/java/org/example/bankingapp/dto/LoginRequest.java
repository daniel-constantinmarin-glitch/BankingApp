package org.example.bankingapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public class LoginRequest {


    @Schema(example = "admin")
    private String username;

    @Schema(example = "admin123")
    private String password;


    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}