<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10/11/2025
  Time: 8:33 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <base target="_self">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Лобби - CodeBattle</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/head.css">
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
    <div class="min-h-screen flex flex-col items-center justify-center p-6">
        <div class="max-w-4xl w-full space-y-8">

            <!-- Заголовок -->
            <div class="text-center mb-8">
                <h1 class="text-4xl font-bold mb-2">Лобби</h1>
                <p class="text-gray-300">Ожидание начала соревнования</p>
            </div>

            <!-- Информация о сессии -->
            <div class="control block-cube p-6 bg-gray-300">
                <div class="bg-top"><div class="bg-inner"></div></div>
                <div class="bg-right"><div class="bg-inner"></div></div>
                <div class="bg"><div class="bg-inner"></div></div>
                <div class="text">
                    <h2 class="text-2xl text-center mb-4">Информация о сессии</h2>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div class="text-center">
                            <div class="text-sm text-gray-400">ID сессии</div>
                            <div class="text-lg font-bold">${session.id}</div>
                        </div>
                        <div class="text-center">
                            <div class="text-sm text-gray-400">Задача</div>
                            <div class="text-lg font-bold">
                                <c:choose>
                                    <c:when test="${not empty problemTitle}">${problemTitle}</c:when>
                                    <c:otherwise>Неизвестная задача (ID: ${session.taskId})</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="text-center">
                            <div class="text-sm text-gray-400">Длительность</div>
                            <div class="text-lg font-bold">
                                <c:choose>
                                    <c:when test="${not empty durationMinutes}">${durationMinutes} мин.</c:when>
                                    <c:otherwise>30 мин. (по умолчанию)</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="text-center">
                            <div class="text-sm text-gray-400">Токен приглашения</div>
                            <div class="text-lg font-bold">${session.inviteToken}</div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Список участников -->
            <div class="control block-cube p-6 bg-gray-300">
                <div class="bg-top"><div class="bg-inner"></div></div>
                <div class="bg-right"><div class="bg-inner"></div></div>
                <div class="bg"><div class="bg-inner"></div></div>
                <div class="text">
                    <h2 class="text-2xl text-center mb-4">Участники</h2>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <c:forEach var="participant" items="${participants}">
                            <div class="flex items-center justify-between p-3 bg-secondary rounded-lg">
                                <div class="flex items-center space-x-3">
                                    <div class="w-8 h-8 bg-accent rounded-full flex items-center justify-center">
                                        <span class="text-sm font-bold">
                                            <c:if test="${not empty participant.username}">
                                                ${participant.username.charAt(0)}
                                            </c:if>
                                        </span>
                                    </div>
                                    <span class="font-medium">${participant.username}</span>
                                </div>
                                <span class="text-sm px-2 py-1 rounded
                                        <c:choose>
                                            <c:when test="${participant.userId == ownerId}">bg-green-600 text-white</c:when>
                                            <c:otherwise>bg-gray-600 text-gray-300</c:otherwise>
                                        </c:choose>">
                                        <c:choose>
                                            <c:when test="${participant.userId == ownerId}">Владелец</c:when>
                                            <c:otherwise>Участник</c:otherwise>
                                        </c:choose>
                                    </span>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!-- Статистика -->
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4 bg-gray-300">
                <div class="control block-cube p-4 text-center">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-sm text-gray-400">Участников</div>
                        <div class="text-xl font-bold">${participants.size()}</div>
                    </div>
                </div>

                <div class="control block-cube p-4 text-center bg-gray-300">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-sm text-gray-400">Время</div>
                        <div class="text-xl font-bold">
                            <c:choose>
                                <c:when test="${not empty durationMinutes}">${durationMinutes} мин</c:when>
                                <c:otherwise>30 мин</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="control block-cube p-4 text-center bg-gray-300">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-sm text-gray-400">Статус</div>
                        <div class="text-xl font-bold">
                            <c:choose>
                                <c:when test="${session.status == 'LOBBY'}">Ожидание</c:when>
                                <c:when test="${session.status == 'RUNNING'}">Запущена</c:when>
                                <c:when test="${session.status == 'FINISHED'}">Завершена</c:when>
                                <c:otherwise>${session.status}</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="control block-cube p-4 text-center bg-gray-300">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-sm text-gray-400">ID Сессии</div>
                        <div class="text-xl font-bold">${session.id}</div>
                    </div>
                </div>
            </div>

            <!-- Кнопки -->
            <div class="flex gap-4 mt-8">
                <c:if test="${userId == ownerId}">
                    <!-- Кнопка для владельца: Начать соревнование -->
                    <form action="${pageContext.request.contextPath}/start-battle" method="post" class="flex-1">
                        <input type="hidden" name="sessionId" value="${session.id}">
                        <button type="submit" class="btn block-cube block-cube-hover w-full">
                            <div class="bg-top"><div class="bg-inner"></div></div>
                            <div class="bg-right"><div class="bg-inner"></div></div>
                            <div class="bg"><div class="bg-inner"></div></div>
                            <div class="text py-3 text-lg">Начать соревнование</div>
                        </button>
                    </form>

                    <!-- Кнопка для владельца: Обновить список участников -->
                    <button type="button" onclick="refreshParticipants()" class="btn block-cube block-cube-hover flex-1">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-3 text-lg">Обновить список участников</div>
                    </button>
                </c:if>

                <c:if test="${userId != ownerId}">
                    <!-- Кнопка для напарника: Подключиться к батлу -->
                    <button type="button" onclick="joinBattle()" class="btn block-cube block-cube-hover flex-1">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-3 text-lg">Подключиться к батлу</div>
                    </button>
                </c:if>

                <!-- Кнопка "Покинуть лобби" для всех -->
                <form action="${pageContext.request.contextPath}/leave-lobby" method="post" class="flex-1">
                    <input type="hidden" name="sessionId" value="${session.id}">
                    <button type="submit" class="btn block-cube block-cube-hover w-full">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-3 text-lg">Покинуть лобби</div>
                    </button>
                </form>
            </div>

            <!-- Дополнительная информация -->
            <div class="text-center text-gray-400 mt-8">
                <p>Ожидайте начала соревнования. Владелец лобби запустит игру, когда все будут готовы.</p>
                <p class="text-sm mt-2">Токен для присоединения: <strong>${session.inviteToken}</strong></p>
            </div>

        </div>
    </div>

    <script>
        const contextPath = '${pageContext.request.contextPath}';
        const sessionId = ${session.id};
        const userId = ${userId};
        const isOwner = ${userId == ownerId};

        // Функция для обновления списка участников (для владельца)
        function refreshParticipants() {
            location.reload();
        }

        // Функция для подключения к батлу (для напарника)
        function joinBattle() {
            const url = `${contextPath}/battle?sessionId=${session.id}`;
            window.location.href = url;
        }
    </script>
</body>
</html>