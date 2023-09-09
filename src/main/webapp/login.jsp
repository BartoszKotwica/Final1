<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Logowanie</title>
</head>
<body>
<h1>Logowanie</h1>

<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<form action="/login" method="post">
    <label for="username">Nazwa użytkownika:</label>
    <input type="text" id="username" name="username" required><br>
    <label for="password">Hasło:</label>
    <input type="password" id="password" name="password" required><br>
    <button type="submit">Zaloguj</button>
</form>
</body>
</html>
