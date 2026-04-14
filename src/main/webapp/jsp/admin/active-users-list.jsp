<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10/11/2025
  Time: 11:04 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <base target="_self">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список пользователей - CodeBattle</title>
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
<!-- Страница списка пользователей -->
    <div class="min-h-screen p-6">
        <div class="max-w-6xl mx-auto">
            <!-- Заголовок и управление -->
            <div class="flex justify-between items-center mb-8">
                <h1 class="text-3xl">Список пользователей</h1>
                <div class="flex gap-4">
                    <!-- Поиск -->
                    <div class="control block-cube block-input">
                        <input type="text" id="searchInput" placeholder="Поиск пользователей..."
                               class="w-64 px-4 py-2 bg-transparent text-white outline-none">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                    </div>

                    <button onclick="goToAdmin()" class="btn block-cube block-cube-hover">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-2 px-4">Назад</div>
                    </button>
                </div>
            </div>

            <!-- Статистика -->
            <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
                <div class="control block-cube p-4 text-center">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-sm text-gray-400">Всего пользователей</div>
                        <div class="text-2xl font-bold" id="totalUsers">0</div>
                    </div>
                </div>

                <div class="control block-cube p-4 text-center">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-sm text-gray-400">Активных</div>
                        <div class="text-2xl font-bold text-green-400" id="activeUsers">0</div>
                    </div>
                </div>

                <div class="control block-cube p-4 text-center">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-sm text-gray-400">Заблокированных</div>
                        <div class="text-2xl font-bold text-red-400" id="bannedUsers">0</div>
                    </div>
                </div>

                <div class="control block-cube p-4 text-center">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <div class="text-sm text-gray-400">Администраторов</div>
                        <div class="text-2xl font-bold text-blue-400" id="adminUsers">0</div>
                    </div>
                </div>
            </div>

            <!-- Таблица пользователей -->
            <div class="control mb-6">
                <div class="block-cube">
                    <div class="bg-top"><div class="bg-inner"></div></div>
                    <div class="bg-right"><div class="bg-inner"></div></div>
                    <div class="bg"><div class="bg-inner"></div></div>
                    <div class="text">
                        <table class="w-full">
                            <thead>
                            <tr class="border-b border-gray-600">
                                <th class="text-left p-4 cursor-pointer" onclick="sortUsers('id')">
                                    ID <span id="sort-id" class="text-gray-400">↕</span>
                                </th>
                                <th class="text-left p-4 cursor-pointer" onclick="sortUsers('username')">
                                    Имя пользователя <span id="sort-username" class="text-gray-400">↕</span>
                                </th>
                                <th class="text-left p-4 cursor-pointer" onclick="sortUsers('email')">
                                    Email <span id="sort-email" class="text-gray-400">↕</span>
                                </th>
                                <th class="text-left p-4 cursor-pointer" onclick="sortUsers('registrationDate')">
                                    Дата регистрации <span id="sort-registrationDate" class="text-gray-400">↕</span>
                                </th>
                                <th class="text-left p-4 cursor-pointer" onclick="sortUsers('status')">
                                    Статус <span id="sort-status" class="text-gray-400">↕</span>
                                </th>
                                <th class="text-left p-4">Действия</th>
                            </tr>
                            </thead>
                            <tbody id="usersTable">
                            <!-- Данные будут загружены через JavaScript -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Пагинация -->
            <div class="flex justify-between items-center">
                <div class="text-gray-400" id="paginationInfo">
                    Показано 0 из 0 пользователей
                </div>
                <div class="flex gap-2" id="paginationControls">
                    <!-- Кнопки пагинации будут добавлены через JavaScript -->
                </div>
            </div>
        </div>
    </div>

    <!-- Модальное окно для редактирования пользователя -->
    <div id="editUserModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center hidden">
        <div class="bg-secondary p-6 rounded-lg max-w-md w-full mx-4">
            <h3 class="text-xl mb-4">Редактирование пользователя</h3>
            <form id="editUserForm" onsubmit="saveUserChanges(event)">
                <input type="hidden" id="editUserId">

                <div class="space-y-4 mb-6">
                    <div>
                        <label class="block text-sm mb-2">Имя пользователя</label>
                        <input type="text" id="editUsername" required
                               class="w-full px-3 py-2 bg-primary text-white rounded">
                    </div>
                    <div>
                        <label class="block text-sm mb-2">Email</label>
                        <input type="email" id="editEmail" required
                               class="w-full px-3 py-2 bg-primary text-white rounded">
                    </div>
                    <div>
                        <label class="block text-sm mb-2">Роль</label>
                        <select id="editRole" class="w-full px-3 py-2 bg-primary text-white rounded">
                            <option value="USER">Пользователь</option>
                            <option value="ADMIN">Администратор</option>
                        </select>
                    </div>
                    <div>
                        <label class="block text-sm mb-2">Статус</label>
                        <select id="editStatus" class="w-full px-3 py-2 bg-primary text-white rounded">
                            <option value="ACTIVE">Активный</option>
                            <option value="BANNED">Заблокирован</option>
                        </select>
                    </div>
                </div>

                <div class="flex gap-4">
                    <button type="submit" class="btn block-cube block-cube-hover flex-1">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-2">Сохранить</div>
                    </button>
                    <button type="button" onclick="closeEditModal()" class="btn block-cube block-cube-hover flex-1">
                        <div class="bg-top"><div class="bg-inner"></div></div>
                        <div class="bg-right"><div class="bg-inner"></div></div>
                        <div class="bg"><div class="bg-inner"></div></div>
                        <div class="text py-2">Отмена</div>
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>