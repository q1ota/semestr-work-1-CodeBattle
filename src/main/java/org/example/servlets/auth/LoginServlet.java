package org.example.servlets.auth;

import org.example.entity.UserEntity;
import org.example.exception.ServiceException;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("register".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/register");
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            request.setAttribute("error", "Заполните логин и пароль");
            request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
            return;
        }

        try {
            Optional<UserEntity> userOptional = userService.authenticate(username, password);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("user_id", user.getId());
                response.sendRedirect(request.getContextPath() + "/choose-action");
            } else {
                request.setAttribute("error", "Неверное имя пользователя или пароль");
                request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
            }
        } catch (ServiceException serviceException) {
            request.setAttribute("error", "Ошибка авторизации");
            request.getRequestDispatcher("/jsp/auth/login.jsp").forward(request, response);
        }
    }
}

