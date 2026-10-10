package com.banking.online_banking.dao;

import com.banking.online_banking.model.Account;
import com.banking.online_banking.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccountDAO {

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (id, account_number, balance, account_type, user_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getId() != null ? account.getId() : UUID.randomUUID().toString());
            ps.setString(2, account.getAccountNumber());
            ps.setBigDecimal(3, account.getBalance());
            ps.setString(4, account.getAccountType());
            ps.setString(5, account.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account getAccountByUserId(String userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Account(
                            rs.getString("id"),
                            rs.getString("account_number"),
                            rs.getBigDecimal("balance"),
                            rs.getString("account_type"),
                            rs.getString("user_id")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean transferFunds(String fromAccNumber, String toAccNumber, BigDecimal amount) {
        String getIdSql = "SELECT id FROM accounts WHERE account_number = ?";
        String debitSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        String creditSql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        String txSql = "INSERT INTO transactions (id, amount, type, source_account_id, target_account_id, description) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sourceId = null;
            String targetId = null;

            try (PreparedStatement ps = conn.prepareStatement(getIdSql)) {
                ps.setString(1, fromAccNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) sourceId = rs.getString("id");
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(getIdSql)) {
                ps.setString(1, toAccNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) targetId = rs.getString("id");
                }
            }

            if (sourceId == null || targetId == null) {
                conn.rollback();
                return false;
            }

            // Deduct from sender
            try (PreparedStatement psDebit = conn.prepareStatement(debitSql)) {
                psDebit.setBigDecimal(1, amount);
                psDebit.setString(2, fromAccNumber);
                psDebit.setBigDecimal(3, amount);
                int rows = psDebit.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // Add to recipient
            try (PreparedStatement psCredit = conn.prepareStatement(creditSql)) {
                psCredit.setBigDecimal(1, amount);
                psCredit.setString(2, toAccNumber);
                int rows = psCredit.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // Insert transaction record
            try (PreparedStatement psTx = conn.prepareStatement(txSql)) {
                psTx.setString(1, UUID.randomUUID().toString());
                psTx.setBigDecimal(2, amount);
                psTx.setString(3, "TRANSFER");
                psTx.setString(4, sourceId);
                psTx.setString(5, targetId);
                psTx.setString(6, "Transfer from " + fromAccNumber + " to " + toAccNumber);
                psTx.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}