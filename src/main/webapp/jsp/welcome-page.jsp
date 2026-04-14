<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10/11/2025
  Time: 7:40 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="ru">
<head>
    <base target="_self">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CodeBattle - Соревнуйтесь в программировании</title>
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
    <!-- Главная страница приветствия -->
    <div class="min-h-screen flex flex-col items-center justify-center p-6">
        <div class="max-w-4xl w-full space-y-12">
            <!-- Заголовок -->
            <div class="text-center">
                <h1 class="text-5xl font-bold mb-4">CodeBattle</h1>
                <p class="text-lg text-gray-300">Платформа для соревнований по программированию</p>
            </div>

            <!-- Описание -->
            <div class="control block-cube p-6 bg-gray-300">
                <div class="bg-top"><div class="bg-inner"></div></div>
                <div class="bg-right"><div class="bg-inner"></div></div>
                <div class="bg"><div class="bg-inner"></div></div>
                <div class="text">
                    <h2 class="text-2xl text-center mb-4">Соревнуйтесь в реальном времени</h2>
                    <p class="text-gray-300 text-center">
                        CodeBattle - это платформа для соревнований по программированию,
                        где вы можете бросить вызов друзьям, коллегам или случайным соперникам
                        в решении алгоритмических задач на скорость.
                    </p>
                </div>
            </div>

            <!-- Преимущества -->
            <div class="grid grid-cols-1 md:grid-cols-3 gap-6 bg-gray-300">
                <div class="control block-cube p-4 text-center">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-lg font-bold mb-2">Быстро</div>
                        <div class="text-sm text-gray-300">Создавайте сессии за секунды</div>
                    </div>
                </div>

                <div class="control block-cube p-4 text-center bg-gray-300">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-lg font-bold mb-2">Соревнуйтесь</div>
                        <div class="text-sm text-gray-300">Решайте задачи на скорость</div>
                    </div>
                </div>

                <div class="control block-cube p-4 text-center bg-gray-300">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-lg font-bold mb-2">Развивайтесь</div>
                        <div class="text-sm text-gray-300">Улучшайте свои навыки</div>
                    </div>
                </div>
            </div>

            <!-- Кнопка авторизации -->
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/login" class="btn block-cube block-cube-hover w-full max-w-md mx-auto inline-block">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text py-4 text-xl">Начать</div>
                </a>
            </div>

            <!-- Дополнительная информация -->
            <div class="text-center pt-8 text-gray-400">
                <p>Присоединяйтесь к сообществу программистов</p>
            </div>
        </div>
    </div>
</body>
</html>