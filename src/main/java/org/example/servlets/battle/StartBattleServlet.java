package org.example.servlets.battle;

import org.example.entity.enums.SessionStatus;
import org.example.service.SessionService;
import org.example.service.impl.SessionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/start-battle")
public class StartBattleServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionIdParam = request.getParameter("sessionId");
        Long sessionId = Long.parseLong(sessionIdParam);
        Long userId = (Long) request.getSession().getAttribute("user_id");

        // Просто обновляем статус и редиректим
        try {
            sessionService.updateStatus(sessionId, SessionStatus.RUNNING);
            response.sendRedirect(request.getContextPath() + "/battle?sessionId=" + sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/lobby?sessionId=" + sessionId + "&error=Ошибка запуска");
        }
    }
}