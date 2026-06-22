package org.example.bankingapp.service;

import org.example.bankingapp.repository.AccountRepository;
import org.example.bankingapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;


    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public void deleteAllUsers() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }
}