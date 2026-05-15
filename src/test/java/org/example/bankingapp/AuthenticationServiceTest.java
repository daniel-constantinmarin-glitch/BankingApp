package org.example.bankingapp;

import org.example.bankingapp.dto.LoginRequest;
import org.example.bankingapp.security.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnTokenForAdmin() {
        LoginRequest req = new LoginRequest("admin", "admin123");

        String token = service.login(req);

        assertNotNull(token);
    }

    @Test
    void shouldReturnTokenForUser() {
        String token = service.login(new LoginRequest("user", "user123"));

        assertNotNull(token);
    }

    @Test
    void shouldThrowErrorForInvalidUser() {
        LoginRequest req = new LoginRequest("wrong", "wrong");

        assertThrows(RuntimeException.class, () -> service.login(req));
    }

    @Test
    void userShouldNotAccessAdmin() throws Exception {

        String userToken = service.login(
                new LoginRequest("user", "user123")
        );

        mockMvc.perform(get("/api/admin")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }


    @Test
    void adminShouldAccessAdmin() throws Exception {

        String adminToken = service.login(
                new LoginRequest("admin", "admin123")
        );

        mockMvc.perform(get("/api/admin")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorizedWithoutToken() throws Exception {

        mockMvc.perform(get("/api/admin"))
                .andExpect(status().isUnauthorized());
    }


}

