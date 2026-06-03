package org.example.bankingapp.dto;

public class CreateAccountRequest {

    private String ownerName;
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
