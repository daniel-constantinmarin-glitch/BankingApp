package org.example.bankingapp.service;

import org.example.bankingapp.dto.CreateAccountRequest;
import org.example.bankingapp.exception.AccountNotFoundException;
import org.example.bankingapp.exception.InsufficientBalanceException;
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


    public Account deposit(Long accountId, double amount) {

        log.info("Deposit request: accountId={}, amount={}", accountId, amount);

        if (amount <= 0) {
            log.warn("Invalid deposit amount: {}", amount);
            throw new InvalidAmountException("Deposit amount must be greater than 0");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.warn("Account not found with id {}", accountId);
                    return new AccountNotFoundException("Account not found");
                });

        account.setBalance(account.getBalance() + amount);

        Account updated = accountRepository.save(account);

        log.info("Deposit successful for accountId={}, new balance={}",
                accountId, updated.getBalance());

        return updated;
    }


    public Account withdraw(Long accountId, double amount) {

        log.info("Withdraw request: accountId={}, amount={}", accountId, amount);

        if (amount <= 0) {
            log.warn("Invalid withdraw amount: {}", amount);
            throw new InvalidAmountException("Withdraw amount must be greater than 0");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.warn("Account not found with id {}", accountId);
                    return new AccountNotFoundException("Account not found");
                });

        if (account.getBalance() < amount) {
            log.warn("Insufficient balance for accountId={}, currentBalance={}, requested={}",
                    accountId, account.getBalance(), amount);

            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        Account updated = accountRepository.save(account);

        log.info("Withdraw successful for accountId={}, new balance={}",
                accountId, updated.getBalance());

        return updated;
    }

}