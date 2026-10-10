package com.banking.online_banking.servlet;

import com.banking.online_banking.dao.AccountDAO;
import com.banking.online_banking.dao.UserDAO;
import com.banking.online_banking.model.Account;
import com.banking.online_banking.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String userId = UUID.randomUUID().toString();
        User user = new User(userId, username, email, password, "ROLE_CUSTOMER");

        boolean userCreated = userDAO.registerUser(user);
        if (userCreated) {
            String accNum = "ACC" + (1000000000L + new Random().nextInt(900000000));
            Account account = new Account(
                    UUID.randomUUID().toString(),
                    accNum,
                    new BigDecimal("1000.00"),
                    "SAVINGS",
                    userId
            );
            accountDAO.createAccount(account);
            response.sendRedirect("login.jsp?msg=Registration successful! Please login.");
        } else {
            response.sendRedirect("register.jsp?error=Registration failed. Username or email taken.");
        }
    }
}