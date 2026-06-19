package org.example.bankingapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public class CreateAccountRequest {


    @Schema(example = "Daniel Marin")
    private String ownerName;

    @Schema(example = "1000")
    private double initialBalance;


    public String getOwnerName() {
        return ownerName;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
