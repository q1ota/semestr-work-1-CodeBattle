package org.example.servlets.session;

import org.example.dto.view.ParticipantViewDto;
import org.example.entity.ParticipantEntity;
import org.example.entity.ProblemEntity;
import org.example.entity.SessionEntity;
import org.example.entity.UserEntity;
import org.example.exception.InvalidProblemExcecption;
import org.example.service.ParticipantService;
import org.example.service.ProblemService;
import org.example.service.SessionService;
import org.example.service.UserService;
import org.example.service.impl.ParticipantServiceImpl;
import org.example.service.impl.ProblemServiceImpl;
import org.example.service.impl.SessionServiceImpl;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/lobby")
public class LobbyServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();
    private final ParticipantService participantService = new ParticipantServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final ProblemService problemService = new ProblemServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionIdParam = request.getParameter("sessionId");
        if (sessionIdParam == null) {
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        long sessionId;
        try {
            sessionId = Long.parseLong(sessionIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        Long userId = (Long) request.getSession().getAttribute("user_id");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        SessionEntity session = sessionService.getSessionById(sessionId).orElse(null);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        ProblemEntity problem = problemService.findById(session.getTaskId()).
                orElseThrow(() -> new InvalidProblemExcecption("Не удалось найти задачу"));

        int durationMinutes = 30;
        Integer ds = session.getDurationSeconds();
        if (ds != null && ds > 0) {
            durationMinutes = (ds + 59) / 60;
        }

        List<ParticipantEntity> participants = participantService.getParticipants(sessionId);
        List<ParticipantViewDto> participantViews = new ArrayList<>();

        for (ParticipantEntity participant : participants) {
            Optional<UserEntity> userOpt = userService.findById(participant.getUserId());
            String username = userOpt.map(UserEntity::getUsername).orElse("Неизвестный пользователь");
            participantViews.add(new ParticipantViewDto(
                    participant.getUserId(),
                    username,
                    participant.getRole(),
                    participant.getStatus()
            ));
        }

        request.setAttribute("session", session);
        request.setAttribute("participants", participantViews);
        request.setAttribute("problemTitle", problem.getTitle());
        request.setAttribute("durationMinutes", durationMinutes);
        request.setAttribute("ownerId", session.getOwnerId());
        request.setAttribute("userId", userId);

        request.getRequestDispatcher("/jsp/session/lobby.jsp").forward(request, response);
    }
}