<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bank Portal - Register</title>
    <style>
        body { font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; background: #f4f6f9; margin: 0; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); width: 320px; }
        input { width: 100%; padding: 10px; margin: 8px 0; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; }
        button { width: 100%; padding: 10px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; margin-top: 10px; }
        .alert { color: red; font-size: 14px; margin-bottom: 10px; }
    </style>
</head>
<body>
<div class="card">
    <h2>Open Account</h2>
    <% if (request.getParameter("error") != null) { %>
        <div class="alert"><%= request.getParameter("error") %></div>
    <% } %>
    <form action="register" method="post">
        <label>Username</label>
        <input type="text" name="username" required>
        <label>Email</label>
        <input type="email" name="email" required>
        <label>Password</label>
        <input type="password" name="password" required>
        <button type="submit">Register</button>
    </form>
    <p style="text-align: center; margin-top: 15px;"><a href="login.jsp">Already registered? Log in</a></p>
</div>
</body>
</html>