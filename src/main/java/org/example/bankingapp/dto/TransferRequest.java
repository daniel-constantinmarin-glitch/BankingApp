package org.example.bankingapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public class TransferRequest {

    @Schema(example = "1")
    private Long fromAccountId;

    @Schema(example = "2")
    private Long toAccountId;

    @Schema(example = "200")
    private double amount;


    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}