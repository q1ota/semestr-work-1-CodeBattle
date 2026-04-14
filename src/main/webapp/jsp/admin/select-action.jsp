<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10/11/2025
  Time: 11:03 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="ru">
<head>
  <base target="_self">
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Панель администратора - CodeBattle</title>
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
<!-- Панель администратора -->
  <div class="min-h-screen flex flex-col items-center justify-center p-6">
    <div class="max-w-md w-full space-y-6">
      <!-- Заголовок -->
      <div class="text-center mb-8">
        <h1 class="text-4xl font-bold mb-2">Панель администратора</h1>
        <p class="text-lg text-gray-300">Управление платформой CodeBattle</p>
      </div>

      <!-- Кнопка "Добавить задачу" -->
      <button onclick="addProblem()" class="btn block-cube block-cube-hover w-full">
        <div class="bg-top"><div class="bg-inner"></div></div>
        <div class="bg-right"><div class="bg-inner"></div></div>
        <div class="bg"><div class="bg-inner"></div></div>
        <div class="text py-4 text-xl">Добавить задачу</div>
      </button>

      <!-- Кнопка "Список задач" -->
      <button onclick="showProblems()" class="btn block-cube block-cube-hover w-full">
        <div class="bg-top"><div class="bg-inner"></div></div>
        <div class="bg-right"><div class="bg-inner"></div></div>
        <div class="bg"><div class="bg-inner"></div></div>
        <div class="text py-4 text-xl">Список задач</div>
      </button>

      <!-- Кнопка "Список сессий" -->
      <button onclick="showSessions()" class="btn block-cube block-cube-hover w-full">
        <div class="bg-top"><div class="bg-inner"></div></div>
        <div class="bg-right"><div class="bg-inner"></div></div>
        <div class="bg"><div class="bg-inner"></div></div>
        <div class="text py-4 text-xl">Список сессий</div>
      </button>

      <!-- Кнопка "Список пользователей" -->
      <button onclick="showUsers()" class="btn block-cube block-cube-hover w-full">
        <div class="bg-top"><div class="bg-inner"></div></div>
        <div class="bg-right"><div class="bg-inner"></div></div>
        <div class="bg"><div class="bg-inner"></div></div>
        <div class="text py-4 text-xl">Список пользователей</div>
      </button>

      <!-- Кнопка возврата на главную -->
      <button onclick="goToMain()" class="btn block-cube block-cube-hover w-full mt-8 border border-gray-600">
        <div class="bg-top"><div class="bg-inner"></div></div>
        <div class="bg-right"><div class="bg-inner"></div></div>
        <div class="bg"><div class="bg-inner"></div></div>
        <div class="text py-3 text-lg">На главную</div>
      </button>
    </div>
  </div>
</body>
</html>