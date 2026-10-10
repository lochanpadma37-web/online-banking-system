package com.banking.online_banking.model;

import java.math.BigDecimal;

public class Account {
    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private String accountType;
    private String userId;

    public Account() {}

    public Account(String id, String accountNumber, BigDecimal balance, String accountType, String userId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.userId = userId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}