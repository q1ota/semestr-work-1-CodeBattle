package org.example.servlets.auth;

import org.example.exception.ServiceException;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = trimParam(request.getParameter("username"));
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        if (username == null || username.isBlank()) {
            request.setAttribute("error", "Имя пользователя не может быть пустым");
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        if (password == null || password.length() < 8){
            request.setAttribute("error", "Пароль должен быть не короче 8 символов");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        if (!Objects.equals(password, confirm)) {
            request.setAttribute("error", "Пароли не совпадают");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
            return;
        }

        try {
            long userId = userService.register(username, password);

            HttpSession session = request.getSession(true);
            session.setAttribute("user_id", userId);
            session.setAttribute("username", username);

            response.sendRedirect(request.getContextPath() + "/choose-action");
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("username", username);
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Внутренняя ошибка, попробуйте позже");
            request.getRequestDispatcher("/jsp/auth/register.jsp").forward(request, response);
        }
    }

    private static String trimParam(String s) {
        return s == null ? null : s.trim();
    }
}
