package org.example.bankingapp;

import org.example.bankingapp.model.User;
import org.example.bankingapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankingApp {

    public static void main(String[] args) {
        SpringApplication.run(BankingApp.class, args);
    }



    @Bean
    public CommandLineRunner init(UserRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new User("admin", "admin123", "ROLE_ADMIN"));
                repo.save(new User("user", "user123", "ROLE_USER"));
            }
        };
    }


}
