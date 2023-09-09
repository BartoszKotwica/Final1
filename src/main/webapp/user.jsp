<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Lista Użytkowników</title>
</head>
<body>
<h1>Lista Użytkowników</h1>
<table border="1">
  <tr>
    <th>ID</th>
    <th>Imię</th>
    <th>Nazwisko</th>
    <th>Nazwa użytkownika</th>
    <th>Email</th>
  </tr>
  <c:forEach items="${uzytkownicy}" var="uzytkownik">
    <tr>
      <td>${uzytkownik.id}</td>
      <td>${uzytkownik.firstName}</td>
      <td>${uzytkownik.lastName}</td>
      <td>${uzytkownik.userName}</td>
      <td>${uzytkownik.email}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
