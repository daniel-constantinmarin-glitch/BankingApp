package org.example.bankingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.example.bankingapp.dto.*;
import org.example.bankingapp.model.Account;
import org.example.bankingapp.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")

@Tag(name = "Account Controller", description = "Bank account operations")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new account (ADMIN only)")
    @ApiResponse(responseCode = "201", description = "Account created")
    @ApiResponse(responseCode = "400", description = "Invalid input")

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        return ResponseEntity.status(201).body(service.createAccount(request));
    }

    @Operation(summary = "Deposit money into account")
    @ApiResponse(responseCode = "200", description = "Deposit successful")
    @ApiResponse(responseCode = "400", description = "Invalid amount")

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id,
                                           @RequestBody AmountRequest request) {
        return ResponseEntity.ok(service.deposit(id, request.getAmount()));
    }

    @Operation(summary = "Withdraw money from account")
    @ApiResponse(responseCode = "200", description = "Withdraw successful")
    @ApiResponse(responseCode = "400", description = "Invalid amount or insufficient balance")

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long id,
                                            @RequestBody AmountRequest request) {
        return ResponseEntity.ok(service.withdraw(id, request.getAmount()));
    }

    @Operation(summary = "Transfer money between accounts")
    @ApiResponse(responseCode = "200", description = "Transfer successful")
    @ApiResponse(responseCode = "400", description = "Invalid request")

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        service.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount()
        );
        return ResponseEntity.ok("Transfer completed");
    }
}