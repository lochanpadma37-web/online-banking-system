<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.banking.online_banking.model.User" %>
<%@ page import="com.banking.online_banking.model.Account" %>
<%@ page import="com.banking.online_banking.dao.AccountDAO" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    AccountDAO accountDAO = new AccountDAO();
    Account account = accountDAO.getAccountByUserId(user.getId());
    String error = request.getParameter("error");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Online Banking</title>
    <style>
        body { font-family: Segoe UI, Tahoma, sans-serif; background-color: #f4f6f9; margin: 0; padding: 40px; }
        .container { max-width: 650px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.08); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; }
        .header h2 { margin: 0; color: #222; }
        .logout-btn { background: #dc3545; color: #fff; text-decoration: none; padding: 8px 16px; border-radius: 4px; font-weight: bold; }
        .card { background: #eaf2fd; border-left: 5px solid #0056b3; padding: 18px 20px; border-radius: 4px; margin-bottom: 25px; }
        .card p { margin: 6px 0; font-size: 16px; color: #333; }
        .balance { font-size: 22px; font-weight: bold; color: #0056b3; }
        .form-box { border: 1px solid #e1e4e8; padding: 20px; border-radius: 6px; }
        .form-box h3 { margin-top: 0; margin-bottom: 15px; color: #333; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 6px; font-size: 14px; color: #555; }
        input[type="text"], input[type="number"] { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-size: 15px; }
        .btn-submit { background: #28a745; color: #fff; border: none; padding: 10px 20px; border-radius: 4px; font-size: 15px; font-weight: bold; cursor: pointer; }
        .alert-error { color: #dc3545; margin-bottom: 15px; font-weight: bold; }
        .alert-success { color: #28a745; margin-bottom: 15px; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>Welcome, <%= user.getUsername() %>!</h2>
        <a href="login.jsp" class="logout-btn">Logout</a>
    </div>

    <% if (error != null) { %>
        <div class="alert-error"><%= error %></div>
    <% } %>
    <% if (msg != null) { %>
        <div class="alert-success"><%= msg %></div>
    <% } %>

    <div class="card">
        <p><strong>Account Number:</strong> <%= account != null ? account.getAccountNumber() : "N/A" %></p>
        <p class="balance">Available Balance: $<%= account != null ? account.getBalance() : "0.00" %></p>
    </div>

    <div class="form-box">
        <h3>Send Money (Fund Transfer)</h3>
        <form action="transfer" method="post">
            <input type="hidden" name="fromAccount" value="<%= account != null ? account.getAccountNumber() : "" %>">

            <div class="form-group">
                <label>Recipient Account Number</label>
                <input type="text" name="toAccount" placeholder="e.g. ACC1029384756" required>
            </div>

            <div class="form-group">
                <label>Amount ($)</label>
                <input type="number" step="0.01" name="amount" placeholder="0.00" required>
            </div>

            <button type="submit" class="btn-submit">Transfer Funds</button>
        </form>
    </div>
</div>

</body>
</html>