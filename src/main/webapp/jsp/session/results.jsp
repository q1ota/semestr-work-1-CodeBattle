<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10/11/2025
  Time: 8:34 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="ru">
<head>
  <base target="_self">
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Результаты - CodeBattle</title>
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
  <div id="results-page" class="min-h-screen p-6">
    <div class="max-w-4xl mx-auto">
      <div class="control mb-8">
        <h1 class="text-3xl text-center">Результаты соревнования</h1>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
        <div class="control block-cube p-6">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text">
            <h3 class="text-xl mb-4 text-center">Победитель</h3>
            <div class="text-2xl font-bold text-center text-green-400 mb-2" id="winnerName">Иван Иванов</div>
            <div class="text-center text-lg" id="winnerTime">00:15:23</div>
          </div>
        </div>

        <div class="control block-cube p-6">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text">
            <h3 class="text-xl mb-4 text-center">Ваш результат</h3>
            <div class="text-2xl font-bold text-center mb-2" id="yourTime">00:18:45</div>
            <div class="text-center text-lg" id="yourPosition">2 место</div>
          </div>
        </div>
      </div>

      <div class="control mb-6">
        <h2 class="text-2xl mb-4">Таблица результатов</h2>
        <div class="block-cube">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text">
            <table class="w-full">
              <thead>
              <tr class="border-b border-gray-600">
                <th class="text-left p-4">Место</th>
                <th class="text-left p-4">Участник</th>
                <th class="text-left p-4">Время</th>
                <th class="text-left p-4">Статус</th>
              </tr>
              </thead>
              <tbody id="resultsTable">
              <tr class="border-b border-gray-700">
                <td class="p-4">1</td>
                <td class="p-4">Иван Иванов</td>
                <td class="p-4">00:15:23</td>
                <td class="p-4 text-green-400">✓ Завершено</td>
              </tr>
              <tr class="border-b border-gray-700 bg-secondary">
                <td class="p-4 font-bold">2</td>
                <td class="p-4 font-bold">Вы</td>
                <td class="p-4 font-bold">00:18:45</td>
                <td class="p-4 font-bold text-green-400">✓ Завершено</td>
              </tr>
              <tr class="border-b border-gray-700">
                <td class="p-4">3</td>
                <td class="p-4">Петр Петров</td>
                <td class="p-4">00:22:10</td>
                <td class="p-4 text-green-400">✓ Завершено</td>
              </tr>
              <tr class="border-b border-gray-700">
                <td class="p-4">4</td>
                <td class="p-4">Анна Сидорова</td>
                <td class="p-4">00:25:33</td>
                <td class="p-4 text-green-400">✓ Завершено</td>
              </tr>
              <tr>
                <td class="p-4">-</td>
                <td class="p-4">Сергей Козлов</td>
                <td class="p-4">-</td>
                <td class="p-4 text-red-400">✗ Не завершено</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <!-- Дополнительная статистика -->
      <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
        <div class="control block-cube p-4 text-center">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text">
            <div class="text-sm text-gray-400">Всего участников</div>
            <div class="text-2xl font-bold">5</div>
          </div>
        </div>

        <div class="control block-cube p-4 text-center">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text">
            <div class="text-sm text-gray-400">Завершили</div>
            <div class="text-2xl font-bold text-green-400">4</div>
          </div>
        </div>

        <div class="control block-cube p-4 text-center">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text">
            <div class="text-sm text-gray-400">Среднее время</div>
            <div class="text-2xl font-bold">00:20:28</div>
          </div>
        </div>
      </div>

      <div class="flex gap-4">
        <button onclick="createNewLobby()" class="btn block-cube block-cube-hover flex-1">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text py-3">Новое лобби</div>
        </button>

        <button onclick="goToMain()" class="btn block-cube block-cube-hover flex-1">
          <div class="bg-top"><div class="bg-inner"></div></div>
          <div class="bg-right"><div class="bg-inner"></div></div>
          <div class="bg"><div class="bg-inner"></div></div>
          <div class="text py-3">Главная</div>
        </button>
      </div>
    </div>
  </div>
</body>
</html>