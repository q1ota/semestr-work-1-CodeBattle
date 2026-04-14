<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 11/11/2025
  Time: 1:02 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <base target="_self">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Присоединение к сессии - CodeBattle</title>
    <meta name="description" content="Платформа для соревнований по программированию кто быстрее решит задачу">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/head.css">
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
<!-- Страница присоединения к сессии -->
<div class="min-h-screen flex flex-col items-center justify-center p-6">
    <div class="max-w-md w-full space-y-6">
        <!-- Заголовок -->
        <div class="text-center mb-8">
            <h1 class="text-4xl font-bold mb-4">Присоединиться к сессии</h1>
            <p class="text-lg text-gray-300">Введите токен сессии для присоединения</p>
        </div>

        <form action="join-session" method="POST" class="space-y-6">
            <!-- Поле для ввода токена -->
            <div class="control block-cube block-input">
                <input name="sessionToken" placeholder="Введите токен сессии" type="text" required
                       class="w-full px-4 py-3 bg-transparent text-white outline-none text-center tracking-widest uppercase"
                       maxlength="8" pattern="[A-Z0-9]{8}" title="Токен должен содержать 8 символов (буквы и цифры)">
                <div class="bg-top"><div class="bg-inner"></div></div>
                <div class="bg-right"><div class="bg-inner"></div></div>
                <div class="bg"><div class="bg-inner"></div></div>
            </div>

            <!-- Кнопки -->
            <div class="flex gap-4 mt-6">
                <!-- Кнопка присоединения -->
                <button type="submit" name="action" value="join" class="btn block-cube block-cube-hover flex-1">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text py-3">Присоединиться</div>
                </button>
            </div>
        </form>

        <!-- Отдельная форма для кнопки "Назад" -->
        <form action="${pageContext.request.contextPath}/choose-action" method="get" class="flex flex-1 mt-4">
            <button type="submit" class="btn block-cube block-cube-hover w-full">
                <div class="bg-top"><div class="bg-inner"></div></div>
                <div class="bg-right"><div class="bg-inner"></div></div>
                <div class="bg"><div class="bg-inner"></div></div>
                <div class="text py-3">Назад</div>
            </button>
        </form>


        <!-- Дополнительная информация -->
        <div class="text-center mt-8 text-gray-400">
            <p class="text-sm">Токен сессии состоит из 8 символов (буквы и цифры)</p>
            <p class="text-sm mt-2">Пример: A1B2C3D4</p>
        </div>

        <c:if test="${not empty error}">
            <div class="mt-4 text-red-400">${error}</div>
        </c:if>
    </div>
</div>

<script>
    // Автоматическое приведение к верхнему регистру и удаление пробелов
    document.addEventListener('DOMContentLoaded', function() {
        const tokenInput = document.querySelector('input[name="sessionToken"]');

        tokenInput.addEventListener('input', function() {
            // Удаляем все пробелы и приводим к верхнему регистру
            this.value = this.value.replace(/\s/g, '').toUpperCase();

            // Ограничиваем длину 8 символами
            if (this.value.length > 8) {
                this.value = this.value.substring(0, 8);
            }
        });

        // Разрешаем только буквы и цифры
        tokenInput.addEventListener('keypress', function(e) {
            const char = String.fromCharCode(e.keyCode || e.which);
            if (!/^[A-Za-z0-9]$/.test(char)) {
                e.preventDefault();
            }
        });
    });
</script>

<style>
    /* Стили для поля ввода токена */
    input[name="sessionToken"] {
        letter-spacing: 0.2em;
        font-size: 1.1em;
        font-weight: 600;
    }

    /* Убираем стрелки у числовых полей если они появятся */
    input[type="text"]::-webkit-outer-spin-button,
    input[type="text"]::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    /* Плавные переходы для интерактивных элементов */
    .btn {
        transition: all 0.2s ease-in-out;
    }

    .btn:hover {
        transform: translateY(-1px);
    }
</style>
</body>
</html>