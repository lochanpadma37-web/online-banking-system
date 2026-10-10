package com.banking.online_banking.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private String id;
    private BigDecimal amount;
    private String type;
    private Timestamp timestamp;
    private String sourceAccountId;
    private String targetAccountId;
    private String description;

    public Transaction() {}

    public Transaction(String id, BigDecimal amount, String type, Timestamp timestamp,
                       String sourceAccountId, String targetAccountId, String description) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.description = description;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

    public String getSourceAccountId() { return sourceAccountId; }
    public void setSourceAccountId(String sourceAccountId) { this.sourceAccountId = sourceAccountId; }

    public String getTargetAccountId() { return targetAccountId; }
    public void setTargetAccountId(String targetAccountId) { this.targetAccountId = targetAccountId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}