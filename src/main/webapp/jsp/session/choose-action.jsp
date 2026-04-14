<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10/11/2025
  Time: 10:46 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="ru">
<head>
  <base target="_self">
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Главная - CodeBattle</title>
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
<!-- Главная страница с двумя кнопками -->
  <div class="min-h-screen flex flex-col items-center justify-center p-6">
    <div class="max-w-md w-full space-y-8">
      <!-- Заголовок -->
      <div class="text-center mb-12">
        <h1 class="text-4xl font-bold mb-4">CodeBattle</h1>
      </div>

      <form action="choose-action" method="post" class="space-y-6">
        <!-- Кнопка "Создать сессию" (сверху) -->
        <button type="submit" name="action" value="create-session" class="btn block-cube block-cube-hover w-full mb-6">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text py-4 text-xl">Создать сессию</div>
        </button>

        <!-- Кнопка "Присоединиться к сессии" (снизу) -->
        <button type="submit" name="action" value="join-session" class="btn block-cube block-cube-hover w-full">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text py-4 text-xl">Присоединиться к сессии</div>
        </button>
      </form>
      <!-- Дополнительная информация -->
      <div class="text-center mt-12 text-gray-400">
        <p>Создайте соревнование или присоединитесь к существующему</p>
      </div>
    </div>
  </div>
</body>
</html>