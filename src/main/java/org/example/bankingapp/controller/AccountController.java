package org.example.bankingapp.controller;

import org.example.bankingapp.dto.AmountRequest;
import org.example.bankingapp.dto.CreateAccountRequest;
import org.example.bankingapp.model.Account;
import org.example.bankingapp.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {

        log.info("Received request to create account for {}", request.getOwnerName());

        Account account = accountService.createAccount(request);

        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }


    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(
            @PathVariable Long id,
            @RequestBody AmountRequest request) {

        log.info("Received deposit request for accountId={}", id);

        Account updated = accountService.deposit(id, request.getAmount());

        return ResponseEntity.ok(updated);
    }


    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Account> withdraw(
            @PathVariable Long id,
            @RequestBody AmountRequest request) {

        log.info("Received withdraw request for accountId={}", id);

        Account updated = accountService.withdraw(id, request.getAmount());

        return ResponseEntity.ok(updated);
    }


}