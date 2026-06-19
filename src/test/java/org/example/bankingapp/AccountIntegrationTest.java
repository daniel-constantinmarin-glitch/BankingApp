package org.example.bankingapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bankingapp.dto.*;
import org.example.bankingapp.security.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService authService;

    private ObjectMapper objectMapper = new ObjectMapper();


    private String adminToken;


    @BeforeEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }


    @BeforeEach
    void setup() {
        adminToken = authService.login(new LoginRequest("admin", "admin123"));
    }

    @Test
    void shouldCreateAccount() throws Exception {

        CreateAccountRequest request = new CreateAccountRequest();
        request.setOwnerName("Daniel Marin");
        request.setInitialBalance(1000);

        mockMvc.perform(post("/api/accounts")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ownerName").value("Daniel Marin"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void shouldFailCreateAccountWithNegativeBalance() throws Exception {

        CreateAccountRequest request = new CreateAccountRequest();
        request.setOwnerName("Daniel");
        request.setInitialBalance(-100);

        mockMvc.perform(post("/api/accounts")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDepositMoney() throws Exception {

        Long accountId = createAccount(1000);

        AmountRequest request = new AmountRequest();
        request.setAmount(200);

        mockMvc.perform(post("/api/accounts/" + accountId + "/deposit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1200));
    }

    @Test
    void shouldFailDepositNegativeAmount() throws Exception {

        Long accountId = createAccount(1000);

        AmountRequest request = new AmountRequest();
        request.setAmount(-50);

        mockMvc.perform(post("/api/accounts/" + accountId + "/deposit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldWithdrawMoney() throws Exception {

        Long accountId = createAccount(1000);

        AmountRequest request = new AmountRequest();
        request.setAmount(300);

        mockMvc.perform(post("/api/accounts/" + accountId + "/withdraw")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(700));
    }

    @Test
    void shouldFailWithdrawWhenInsufficientBalance() throws Exception {

        Long accountId = createAccount(100);

        AmountRequest request = new AmountRequest();
        request.setAmount(500);

        mockMvc.perform(post("/api/accounts/" + accountId + "/withdraw")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldTransferMoney() throws Exception {

        Long fromId = createAccount(1000);
        Long toId = createAccount(500);

        TransferRequest request = new TransferRequest();
        request.setFromAccountId(fromId);
        request.setToAccountId(toId);
        request.setAmount(200);

        mockMvc.perform(post("/api/accounts/transfer")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailTransferWhenInsufficientBalance() throws Exception {

        Long fromId = createAccount(100);
        Long toId = createAccount(500);

        TransferRequest request = new TransferRequest();
        request.setFromAccountId(fromId);
        request.setToAccountId(toId);
        request.setAmount(1000);

        mockMvc.perform(post("/api/accounts/transfer")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    private Long createAccount(double balance) throws Exception {

        CreateAccountRequest request = new CreateAccountRequest();
        request.setOwnerName("Test User");
        request.setInitialBalance(balance);

        String response = mockMvc.perform(post("/api/accounts")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }
}
