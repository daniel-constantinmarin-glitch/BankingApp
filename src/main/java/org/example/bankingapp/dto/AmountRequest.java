package org.example.bankingapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public class AmountRequest {

    @Schema(example = "200")
    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}