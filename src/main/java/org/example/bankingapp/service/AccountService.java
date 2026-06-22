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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Transactional
    public void transfer(Long fromId, Long toId, double amount) {

        log.info("Transfer request: from={}, to={}, amount={}", fromId, toId, amount);

        if (amount <= 0) {
            log.warn("Invalid transfer amount: {}", amount);
            throw new InvalidAmountException("Transfer amount must be greater than 0");
        }

        if (fromId.equals(toId)) {
            log.warn("Transfer to same account: {}", fromId);
            throw new InvalidAmountException("Cannot transfer to the same account");
        }

        Account fromAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> {
                    log.warn("Source account not found: {}", fromId);
                    return new AccountNotFoundException("Source account not found");
                });

        Account toAccount = accountRepository.findById(toId)
                .orElseThrow(() -> {
                    log.warn("Destination account not found: {}", toId);
                    return new AccountNotFoundException("Destination account not found");
                });

        if (fromAccount.getBalance() < amount) {
            log.warn("Insufficient balance: fromAccount={}, balance={}, requested={}",
                    fromId, fromAccount.getBalance(), amount);

            throw new InsufficientBalanceException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        log.info("Transfer successful: {} -> {} amount={}", fromId, toId, amount);
    }


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


}