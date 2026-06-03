package org.example.bankingapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerName;

    private double balance;

    public Account() {}

    public Account(String ownerName, double balance) {
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    public void setId(Long id) { this.id = id; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public void setBalance(double balance) { this.balance = balance; }
}
