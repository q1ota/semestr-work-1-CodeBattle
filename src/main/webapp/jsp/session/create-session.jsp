<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10/11/2025
  Time: 11:12 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <base target="_self">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Создание лобби - CodeBattle</title>
    <meta name="description" content="Платформа для соревнований по программированию кто быстрее решит задачу">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/head.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/checkbox.css">
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
<!-- Страница создания лобби -->
<div class="min-h-screen p-6">
    <div class="max-w-2xl mx-auto">
        <div class="control mb-8">
            <h1 class="text-3xl text-center">Создание лобби</h1>
        </div>

        <form action="${pageContext.request.contextPath}/create-session" method="POST" class="space-y-6">
            <!-- Название сессии -->
            <div class="control block-cube block-input">
                <input name="lobbyName" placeholder="Название сессии" type="text" required
                       class="w-full px-4 py-3 bg-transparent text-white outline-none">
                <div class="bg-top"><div class="bg-inner"></div></div>
                <div class="bg-right"><div class="bg-inner"></div></div>
                <div class="bg"><div class="bg-inner"></div></div>
            </div>

            <!-- Выбор задачи -->
            <div class="control">
                <h3 class="text-xl mb-4">Выбор задачи</h3>
                <div class="block-cube block-input">
                    <select name="problemId" required
                            class="w-full px-4 py-3 bg-transparent text-white outline-none appearance-none">
                        <option value="">Выберите задачу...</option>
                        <c:forEach var="problem" items="${problems}">
                            <option value="${problem.id}">${problem.title}</option>
                        </c:forEach>
                    </select>
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                </div>
            </div>

            <!-- Время выполнения -->
            <div class="control">
                <h3 class="text-xl mb-4">Время выполнения (минуты)</h3>
                <div class="block-cube block-input">
                    <input name="timeLimit" type="number" min="1" max="120" value="30" required
                           class="w-full px-4 py-3 bg-transparent text-white outline-none hide-arrows">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                </div>
            </div>

            <!-- Чекбокс "Играть в одиночку" -->
            <div class="control">
                <label class="custom-checkbox flex items-center space-x-4 cursor-pointer">
                    <input type="checkbox" name="soloMode" class="hidden">
                    <div class="checkbox-container relative">
                        <div class="checkbox-square block-cube w-6 h-6 flex items-center justify-center">
                            <div class="bg-top"><div class="bg-inner"></div></div>
                            <div class="bg-right"><div class="bg-inner"></div></div>
                            <div class="bg"><div class="bg-inner"></div></div>
                            <div class="text absolute inset-0 flex items-center justify-center">
                                <svg class="checkmark w-4 h-4 text-white opacity-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7"></path>
                                </svg>
                            </div>
                        </div>
                    </div>
                    <span class="text-lg ml-2">Играть в одиночку</span>
                </label>
            </div>

            <!-- Кнопки -->
            <div class="flex gap-4">
                <button type="submit" class="btn block-cube block-cube-hover flex-1">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text py-3">Создать сессию</div>
                </button>
            </div>
        </form>

        <!-- Отдельная форма для кнопки "Назад" -->
        <form action="${pageContext.request.contextPath}/choose-action" method="get" class="mt-4">
            <button type="submit" class="btn block-cube block-cube-hover w-full">
                <div class="bg-top"><div class="bg-inner"></div></div>
                <div class="bg-right"><div class="bg-inner"></div></div>
                <div class="bg"><div class="bg-inner"></div></div>
                <div class="text py-3">Назад</div>
            </button>
        </form>
    </div>
</div>

<script>
    // Обработка чекбокса
    document.addEventListener('DOMContentLoaded', function() {
        const checkboxes = document.querySelectorAll('.custom-checkbox input[type="checkbox"]');

        checkboxes.forEach(checkbox => {
            const container = checkbox.closest('.custom-checkbox');
            const square = container.querySelector('.checkbox-square');
            const checkmark = container.querySelector('.checkmark');

            // Обработчик изменения состояния
            checkbox.addEventListener('change', function() {
                if (this.checked) {
                    square.classList.add('checked');
                    checkmark.classList.remove('opacity-0');
                    checkmark.classList.add('opacity-100');
                } else {
                    square.classList.remove('checked');
                    checkmark.classList.remove('opacity-100');
                    checkmark.classList.add('opacity-0');
                }
            });

            // Обработчик клика по всему контейнеру
            container.addEventListener('click', function(e) {
                e.preventDefault();
                checkbox.checked = !checkbox.checked;
                checkbox.dispatchEvent(new Event('change'));
            });

            // Инициализация состояния
            checkbox.dispatchEvent(new Event('change'));
        });
    });
</script>
</body>
</html>