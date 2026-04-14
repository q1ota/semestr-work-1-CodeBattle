<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 26/10/2025
  Time: 12:01 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
  <base target="_self">
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Авторизация - CodeBattle</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/head.css">
  <meta name="description" content="Платформа для соревнований по программированию кто быстрее решит задачу">
  <script src="https://cdn.tailwindcss.com"></script>
  <script>
    tailwind.config = {
      theme: {
        extend: {
          colors: {
            primary: "#212121",
            secondary: "#333333",
            accent: "#6464ff"
          }
        }
      }
    }
  </script>
</head>
<body class="min-h-screen bg-primary text-white font-mono tracking-wider">
<div id="login-page" class="min-h-screen flex items-center justify-center p-4">
  <form method="post" action="${pageContext.request.contextPath}/login" autocomplete="off" class="form w-full max-w-md">
    <div class="control mb-8">
      <h1 class="text-3xl text-center">Вход в аккаунт</h1>
    </div>

    <div class="control block-cube block-input mb-6">
      <input name="username" placeholder="Логин" type="text" required class="w-full px-4 py-3 bg-transparent text-white outline-none">
      <div class="bg-top"><div class="bg-inner"></div></div>
      <div class="bg-right"><div class="bg-inner"></div></div>
      <div class="bg"><div class="bg-inner"></div></div>
    </div>

    <div class="control block-cube block-input mb-6">
      <input name="password" placeholder="Пароль" type="password" required class="w-full px-4 py-3 bg-transparent text-white outline-none">
      <div class="bg-top"><div class="bg-inner"></div></div>
      <div class="bg-right"><div class="bg-inner"></div></div>
      <div class="bg"><div class="bg-inner"></div></div>
    </div>

    <button type="submit" name="action" value="entire" class="btn block-cube block-cube-hover w-full mb-4">
      <div class="bg-top"><div class="bg-inner"></div></div>
      <div class="bg-right"><div class="bg-inner"></div></div>
      <div class="bg"><div class="bg-inner"></div></div>
      <div class="text py-3">Войти</div>
    </button>

    <button type="button" onclick="window.location='${pageContext.request.contextPath}/register'" class="btn block-cube block-cube-hover w-full">
      <div class="bg-top"><div class="bg-inner"></div></div>
      <div class="bg-right"><div class="bg-inner"></div></div>
      <div class="bg"><div class="bg-inner"></div></div>
      <div class="text py-3">Зарегистрироваться</div>
    </button>

    <c:if test="${not empty error}">
      <div class="mt-4 text-red-400">${error}</div>
    </c:if>
  </form>
</div>
</body>
</html>
