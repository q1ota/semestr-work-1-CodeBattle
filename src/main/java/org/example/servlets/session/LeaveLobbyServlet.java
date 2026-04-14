package org.example.servlets.session;

import org.example.entity.ParticipantEntity;
import org.example.entity.SessionEntity;
import org.example.service.ParticipantService;
import org.example.service.SessionService;
import org.example.service.impl.ParticipantServiceImpl;
import org.example.service.impl.SessionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/leave-lobby")
public class LeaveLobbyServlet extends HttpServlet {

    private final ParticipantService participantService = new ParticipantServiceImpl();
    private final SessionService sessionService = new SessionServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionIdParam = request.getParameter("sessionId");
        Long userId = (Long) request.getSession().getAttribute("user_id");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (sessionIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        try {
            Long sessionId = Long.parseLong(sessionIdParam);
            boolean left = participantService.leaveSession(sessionId, userId);

            if (left) {
                response.sendRedirect(request.getContextPath() + "/choose-action");
            } else {
                request.setAttribute("error", "Не удалось покинуть лобби");
                request.getRequestDispatcher("/lobby?sessionId=" + sessionId).forward(request, response);
            }

            // После выхода участника проверяем, не был ли это владелец
            SessionEntity session = sessionService.getSessionById(sessionId).orElse(null);
            if (session != null && session.getOwnerId().equals(userId)) {
                // Владелец вышел - закрываем сессию или передаем владение
                List<ParticipantEntity> remainingParticipants = participantService.getParticipants(sessionId);

                if (!remainingParticipants.isEmpty()) {
                    // Передаем владение первому участнику
                    ParticipantEntity newOwner = remainingParticipants.get(0);
                    // Здесь нужен метод для смены владельца в SessionService
                    // sessionService.transferOwnership(sessionId, newOwner.getUserId());
                } else {
                    // Участников не осталось - закрываем сессию
                    sessionService.deleteSession(sessionId);
                }
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/choose-action");
        }
    }
}