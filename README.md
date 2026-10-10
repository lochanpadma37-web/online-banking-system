# Secure Online Banking System

A full-stack Java EE web application engineered with an MVC architectural pattern, robust JDBC persistence, and enterprise-grade security protocols.

## Tech Stack & Runtime Environment
- **Language & Runtime:** Java (JDK 17+)
- **Application Server:** Apache Tomcat 9.0 (Embedded)
- **Database:** MySQL 8.0+
- **Build System:** Apache Maven
- **Presentation Layer:** JSP, CSS3, HTML5
- **Security:** BCrypt Hashing, Session Management, Parameterized SQL Queries

## Architecture & Design (MVC Pattern)
- **Model:** `User.java`, `Account.java`, `Transaction.java` (Domain data transfer objects).
- **View:** `register.jsp`, `login.jsp`, `dashboard.jsp` (Dynamic rendering layer).
- **Controller:** `RegisterServlet`, `LoginServlet`, `TransferServlet`, `LogoutServlet` (HTTP request orchestration).
- **Data Access:** `UserDAO`, `AccountDAO`, `DBUtil` (ACID transaction management and JDBC abstraction).

## Database Schema (`schema.sql`)
```sql
CREATE DATABASE IF NOT EXISTS banking_db;
USE banking_db;

CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'ROLE_CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
    id VARCHAR(36) PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 1000.00,
    account_type VARCHAR(20) DEFAULT 'SAVINGS',
    user_id VARCHAR(36) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
    id VARCHAR(36) PRIMARY KEY,
    amount DECIMAL(15,2) NOT NULL,
    type VARCHAR(20) NOT NULL,
    source_account_id VARCHAR(36),
    target_account_id VARCHAR(36),
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (source_account_id) REFERENCES accounts(id),
    FOREIGN KEY (target_account_id) REFERENCES accounts(id)
);