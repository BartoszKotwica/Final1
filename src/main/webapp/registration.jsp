<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rejestracja</title>
</head>
<body>
<h1>Rejestracja użytkownika</h1>
<form action="/registration" method="post">
    <label for="first_name">Imię:</label>
    <input type="text" id="first_name" name="first_name" required><br>
    <label for="last_name">Nazwisko:</label>
    <input type="text" id="last_name" name="last_name" required><br>
    <label for="user_name">Nazwa użytkownika:</label>
    <input type="text" id="user_name" name="user_name" required><br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>
    <label for="password">Hasło:</label>
    <input type="password" id="password" name="password" required><br>
    <button type="submit">Zarejestruj się</button>
</form>
</body>
</html>
