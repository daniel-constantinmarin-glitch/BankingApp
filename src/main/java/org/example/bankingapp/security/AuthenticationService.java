package org.example.bankingapp.security;

import org.example.bankingapp.model.User;
import org.example.bankingapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.bankingapp.dto.LoginRequest;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;


    @Autowired
    private UserRepository userRepository;

    public String login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}
