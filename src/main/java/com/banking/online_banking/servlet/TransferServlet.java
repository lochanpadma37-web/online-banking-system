package com.banking.online_banking.servlet;

import com.banking.online_banking.dao.AccountDAO;
import com.banking.online_banking.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String fromAccount = request.getParameter("fromAccount");
        String toAccount = request.getParameter("toAccount");
        String amountStr = request.getParameter("amount");

        System.out.println(">>> DEBUG FROM: [" + fromAccount + "]");
        System.out.println(">>> DEBUG TO:   [" + toAccount + "]");
        System.out.println(">>> DEBUG AMT:  [" + amountStr + "]");

        if (fromAccount != null) fromAccount = fromAccount.trim();
        if (toAccount != null) toAccount = toAccount.trim();

        if (fromAccount == null || fromAccount.isEmpty() ||
                toAccount == null || toAccount.isEmpty() ||
                amountStr == null || amountStr.isEmpty()) {
            System.out.println(">>> Transfer validation failed: Missing parameters");
            response.sendRedirect("dashboard.jsp?error=Transfer failed: Missing required fields");
            return;
        }

        if (fromAccount.equalsIgnoreCase(toAccount)) {
            System.out.println(">>> Transfer validation failed: Cannot transfer to self");
            response.sendRedirect("dashboard.jsp?error=Cannot transfer funds to the same account");
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                response.sendRedirect("dashboard.jsp?error=Transfer amount must be greater than zero");
                return;
            }

            boolean success = accountDAO.transferFunds(fromAccount, toAccount, amount);
            if (success) {
                System.out.println(">>> Transfer succeeded between " + fromAccount + " and " + toAccount);
                response.sendRedirect("dashboard.jsp?msg=Transfer successful!");
            } else {
                System.out.println(">>> Transfer failed in AccountDAO");
                response.sendRedirect("dashboard.jsp?error=Transfer failed or insufficient funds");
            }
        } catch (NumberFormatException e) {
            System.out.println(">>> Invalid amount format: " + amountStr);
            response.sendRedirect("dashboard.jsp?error=Invalid amount format");
        }
    }
}