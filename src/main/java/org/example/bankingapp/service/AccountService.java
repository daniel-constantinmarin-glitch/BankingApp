package org.example.bankingapp.service;

import org.example.bankingapp.dto.CreateAccountRequest;
import org.example.bankingapp.exception.InvalidAmountException;
import org.example.bankingapp.model.Account;
import org.example.bankingapp.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(CreateAccountRequest request) {

        log.info("Creating account for {}", request.getOwnerName());

        if (request.getInitialBalance() < 0) {
            log.warn("Invalid initial balance: {}", request.getInitialBalance());
            throw new InvalidAmountException("Initial balance cannot be negative");
        }

        Account account = new Account(
                request.getOwnerName(),
                request.getInitialBalance()
        );

        Account saved = accountRepository.save(account);

        log.info("Account created with ID: {}", saved.getId());

        return saved;
    }
}