package org.example.servlets.session;

import org.example.entity.SessionEntity;
import org.example.entity.enums.ParticipantRole;
import org.example.exception.AlreadyParticipantException;
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
import java.util.Optional;

@WebServlet("/join-session")
public class JoinServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();
    private final ParticipantService participantService = new ParticipantServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contextPath = request.getContextPath();
        request.setAttribute("contextPath", contextPath);
        request.getRequestDispatcher("/jsp/session/join-session.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("sessionToken");
        Long userId = (Long) request.getSession().getAttribute("user_id");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (token == null || token.trim().isEmpty()) {
            request.setAttribute("error", "Токен сессии не может быть пустым");
            request.getRequestDispatcher("/jsp/session/join-session.jsp").forward(request, response);
            return;
        }

        try {
            Optional<SessionEntity> sessionOpt = sessionService.getSessionByToken(token);
            if (sessionOpt.isEmpty()) {
                request.setAttribute("error", "Сессия с таким токеном не найдена");
                request.getRequestDispatcher("/jsp/session/join-session.jsp").forward(request, response);
                return;
            }

            SessionEntity session = sessionOpt.get();
            try {
                participantService.joinSession(session.getId(), userId, ParticipantRole.PARTICIPANT);
                response.sendRedirect(request.getContextPath() + "/lobby?sessionId=" + session.getId());
            } catch (AlreadyParticipantException e) {
                response.sendRedirect(request.getContextPath() + "/lobby?sessionId=" + session.getId());
            }

            } catch (Exception e) {
                request.setAttribute("error", "Ошибка при присоединении к сессии: " + e.getMessage());
                request.getRequestDispatcher("/jsp/session/join-session.jsp").forward(request, response);
        }
    }
}
