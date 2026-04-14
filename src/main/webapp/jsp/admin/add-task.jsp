<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10/11/2025
  Time: 11:09 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="ru">
<head>
    <base target="_self">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добавить задачу - CodeBattle</title>
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
<!-- Страница добавления задачи -->
    <div class="min-h-screen p-6">
        <div class="max-w-4xl mx-auto">
            <div class="control mb-8">
                <h1 class="text-3xl text-center">Добавить новую задачу</h1>
            </div>

            <form onsubmit="handleAddProblem(event)" class="space-y-6">
                <!-- Название задачи -->
                <div class="control block-cube block-input">
                    <input id="problemTitle" placeholder="Название задачи" type="text" required
                           class="w-full px-4 py-3 bg-transparent text-white outline-none">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                </div>

                <!-- Описание задачи -->
                <div class="control block-cube block-input">
                        <textarea id="problemDescription" placeholder="Описание задачи" required rows="6"
                                  class="w-full px-4 py-3 bg-transparent text-white outline-none resize-none"></textarea>
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                </div>

                <!-- Описание входных данных -->
                <div class="control block-cube block-input">
                        <textarea id="inputDescription" placeholder="Описание входных данных" required rows="3"
                                  class="w-full px-4 py-3 bg-transparent text-white outline-none resize-none"></textarea>
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                </div>

                <!-- Описание выходных данных -->
                <div class="control block-cube block-input">
                        <textarea id="outputDescription" placeholder="Описание выходных данных" required rows="3"
                                  class="w-full px-4 py-3 bg-transparent text-white outline-none resize-none"></textarea>
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                </div>

                <!-- Примеры тестов (для пользователя) -->
                <div class="control">
                    <h3 class="text-xl mb-4">Примеры тестов (для пользователя)</h3>
                    <div id="examples-container" class="space-y-4 mb-4">
                        <!-- Примеры будут добавляться динамически -->
                    </div>
                    <button type="button" onclick="addExample()" class="btn block-cube block-cube-hover w-full mb-4">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-3">+ Добавить пример</div>
                    </button>
                </div>

                <!-- Тесты (для проверки кода) -->
                <div class="control">
                    <h3 class="text-xl mb-4">Тесты (для проверки кода)</h3>
                    <div id="tests-container" class="space-y-4 mb-4">
                        <!-- Тесты будут добавляться динамически -->
                    </div>
                    <button type="button" onclick="addTest()" class="btn block-cube block-cube-hover w-full mb-4">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-3">+ Добавить тест</div>
                    </button>
                </div>

                <!-- Скрытые поля для JSON данных -->
                <input type="hidden" id="examplesJson" name="examples">
                <input type="hidden" id="testsJson" name="tests">

                <div class="flex gap-4">
                    <button type="submit" class="btn block-cube block-cube-hover flex-1">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-3">Сохранить задачу</div>
                    </button>

                    <button type="button" onclick="goToAdmin()" class="btn block-cube block-cube-hover flex-1">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-3">Отмена</div>
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>