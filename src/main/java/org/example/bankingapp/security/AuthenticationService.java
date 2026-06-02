package org.example.bankingapp.security;

import org.example.bankingapp.dto.LoginRequest;
import org.example.bankingapp.exception.InvalidCredentialsException;
import org.example.bankingapp.exception.UserNotFoundException;
import org.example.bankingapp.model.User;
import org.example.bankingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;


    public String login(LoginRequest request) {

        log.info("Login attempt for user: {}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    log.warn("User not found: {}", request.getUsername());
                    return new UserNotFoundException("User not found");
                });

        if (!user.getPassword().equals(request.getPassword())) {
            log.warn("Invalid password for user: {}", request.getUsername());
            throw new InvalidCredentialsException("Invalid password");
        }

        log.info("User {} successfully authenticated", user.getUsername());

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}