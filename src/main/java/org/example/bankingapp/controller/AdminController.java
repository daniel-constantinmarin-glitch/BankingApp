
package org.example.bankingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.example.bankingapp.model.Account;
import org.example.bankingapp.service.AccountService;
import org.example.bankingapp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AccountService accountService;
    private final UserService userService;


    public AdminController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Operation(summary = "Admin test endpoint")
    @GetMapping
    public String adminOnly() {
        return "Hello Admin!";
    }

    @Operation(summary = "See all accounts (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "List of all accounts")
    @ApiResponse(responseCode = "403", description = "Forbidden")

    @GetMapping("/seeaccounts")
    public List<Account> seeAllAccounts() {
        return accountService.getAllAccounts();
    }


    @Operation(summary = "Delete all users (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "All users deleted")
    @ApiResponse(responseCode = "403", description = "Forbidden")

    @DeleteMapping("/users")
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "All users have been deleted";
    }

}
