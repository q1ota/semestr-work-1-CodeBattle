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
  <title>Кодинг Арена - CodeBattle</title>
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
  <div id="coding-arena-page" class="min-h-screen p-6">
    <div class="max-w-6xl mx-auto">
      <div class="flex justify-between items-center mb-6">
        <h1 class="text-2xl">Лобби: <span id="currentLobbyName">Соревнование</span></h1>
        <div id="timer" class="text-2xl font-bold">00:00:00</div>
      </div>

      <!-- Левая колонка - задача -->
      <div class="space-y-4">
        <div class="control">
          <h2 class="text-xl mb-4">Задача</h2>
          <div class="block-cube p-4 bg-gray-300">
            <div class="bg-top"><div class="bg-inner"></div></div>
            <div class="bg-right"><div class="bg-inner"></div></div>
            <div class="bg"><div class="bg-inner"></div></div>
            <div class="text">
              <div id="problemDisplay" class="whitespace-pre-line">
                Напишите программу, которая считывает два числа и выводит их сумму.
              </div>
            </div>
          </div>
        </div>

        <div class="grid grid-cols-2 gap-4">
          <div class="control">
            <h3 class="text-lg mb-4">Входные данные</h3>
            <div class="block-cube p-4 bg-gray-300">
              <div class="bg-top"><div class="bg-inner"></div></div>
              <div class="bg-right"><div class="bg-inner"></div></div>
              <div class="bg"><div class="bg-inner"></div></div>
              <div class="text">
                <div id="inputDisplay" class="whitespace-pre-line">
                  5 3
                </div>
              </div>
            </div>
          </div>

          <div class="control">
            <h3 class="text-lg mb-4">Выходные данные</h3>
            <div class="block-cube p-4 bg-gray-300">
              <div class="bg-top"><div class="bg-inner"></div></div>
              <div class="bg-right"><div class="bg-inner"></div></div>
              <div class="bg"><div class="bg-inner"></div></div>
              <div class="text">
                <div id="outputDisplay" class="whitespace-pre-line">
                  8
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Правая колонка - редактор кода -->
      <div class="space-y-4">
        <div class="control">
          <h2 class="text-xl mb-4 mt-4">Редактор кода</h2>
          <div class="block-cube block-input">
            <textarea id="codeEditor" placeholder="Напишите ваш код здесь..." rows="15" class="w-full px-4 py-3 bg-transparent text-white outline-none resize-none font-mono">
public class Main {
    public static void main(String args[]) {
        // Ваш код здесь...

    }
}
            </textarea>
            <div class="bg-top"><div class="bg-inner"></div></div>
            <div class="bg-right"><div class="bg-inner"></div></div>
            <div class="bg"><div class="bg-inner"></div></div>
          </div>
        </div>

        <div class="flex gap-4">
          <button onclick="runCode()" class="btn block-cube block-cube-hover flex-1">
            <div class="bg-top"><div class="bg-inner"></div></div>
            <div class="bg-right"><div class="bg-inner"></div></div>
            <div class="bg"><div class="bg-inner"></div></div>
            <div class="text py-3">Запустить</div>
          </button>

          <button onclick="showResults()" class="btn block-cube block-cube-hover flex-1">
            <div class="bg-top"><div class="bg-inner"></div></div>
            <div class="bg-right"><div class="bg-inner"></div></div>
            <div class="bg"><div class="bg-inner"></div></div>
            <div class="text py-3">Результаты</div>
          </button>
        </div>

          <!-- Блок для вывода результатов -->
          <div id="results" class="hidden">
            <div class="control">
              <h3 class="text-lg mb-2">Результат выполнения</h3>
              <div class="block-cube p-4">
                <div class="bg-top"><div class="bg-inner"></div></div>
                <div class="bg-right"><div class="bg-inner"></div></div>
                <div class="bg"><div class="bg-inner"></div></div>
                <div class="text">
                  <pre id="outputResult" class="whitespace-pre-wrap"></pre>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>