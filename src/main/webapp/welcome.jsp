<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Witaj</title>
</head>
<body>
<h1>Witaj, ${user.username}!</h1>
<p>Logowanie zakończone sukcesem.</p>

<!-- Dodaj dowolną treść na stronie powitalnej -->

<form action="/logout" method="post">
  <button type="submit">Wyloguj</button>
</form>
</body>
</html>