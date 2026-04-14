package org.example.servlets.battle;

import org.example.entity.ParticipantEntity;
import org.example.entity.ProblemEntity;
import org.example.entity.SessionEntity;
import org.example.entity.UserEntity;
import org.example.entity.enums.SessionStatus;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/battle")
public class BattleServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();
    private final ProblemService problemService = new ProblemServiceImpl();
    private final ParticipantService participantService = new ParticipantServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("=== BATTLE SERVLET GET ===");

        String sessionIdParam = request.getParameter("sessionId");
        System.out.println("Raw sessionId parameter: '" + sessionIdParam + "'");

        if (sessionIdParam == null) {
            System.out.println("No sessionId parameter provided");
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        // Проверим, не пустая ли строка
        if (sessionIdParam.trim().isEmpty()) {
            System.out.println("Empty sessionId parameter provided");
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        long sessionId;
        try {
            sessionId = Long.parseLong(sessionIdParam);
        } catch (NumberFormatException e) {
            System.out.println("Invalid sessionId: '" + sessionIdParam + "'");
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        Long userId = (Long) request.getSession().getAttribute("user_id");
        if (userId == null) {
            System.out.println("User not authenticated (userId is null)");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        System.out.println("User ID from session: " + userId);
        System.out.println("Requested session ID: " + sessionId);

        // Получаем сессию
        SessionEntity session = sessionService.getSessionById(sessionId).orElse(null);
        if (session == null) {
            System.out.println("Session not found in database: " + sessionId);
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        System.out.println("Session found: ID=" + session.getId() + ", Owner=" + session.getOwnerId() + ", Status=" + session.getStatus());

        List<ParticipantEntity> participants = participantService.getParticipants(sessionId);
        System.out.println("Participants in session: " + participants.size());

        for (ParticipantEntity p : participants) {
            System.out.println("  - User ID: " + p.getUserId() + ", Role: " + p.getRole());
        }

        boolean isParticipant = participants.stream()
                .anyMatch(p -> p.getUserId().equals(userId));

        if (!isParticipant) {
            System.out.println("User " + userId + " is not a participant of session " + sessionId);
            System.out.println("Available participants: " +
                    participants.stream().map(p -> p.getUserId().toString()).collect(Collectors.joining(", ")));
            response.sendRedirect(request.getContextPath() + "/choose-action");
            return;
        }

        System.out.println("User " + userId + " is a participant, granting access");

        ProblemEntity problem = problemService.findById(session.getTaskId())
                .orElseThrow(() -> new RuntimeException("Задача не найдена"));

        UserEntity user = userService.findById(userId).get();

        request.setAttribute("session", session);
        request.setAttribute("problem", problem);
        request.setAttribute("participants", participants);
        request.setAttribute("user", user);

        System.out.println("Forwarding to battle arena - ACCESS GRANTED for user " + userId);
        request.getRequestDispatcher("/jsp/session/coding-arena.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("=== BATTLE SERVLET POST ===");

        String action = request.getParameter("action");

        if ("start".equals(action)) {
            handleStartBattle(request, response);
        } else {
            handleCodeSubmission(request, response);
        }
    }

    private void handleStartBattle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionIdParam = request.getParameter("sessionId");
        Long sessionId = Long.parseLong(sessionIdParam);
        Long userId = (Long) request.getSession().getAttribute("user_id");

        System.out.println("Starting battle for session: " + sessionId + " by user: " + userId);

        // Проверяем, что пользователь - владелец сессии
        var sessionOpt = sessionService.getSessionById(sessionId);
        if (sessionOpt.isEmpty() || !sessionOpt.get().getOwnerId().equals(userId)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only session owner can start battle");
            return;
        }

        try {
            // Обновляем статус сессии на RUNNING
            sessionService.updateStatus(sessionId, SessionStatus.RUNNING);
            System.out.println("Session status updated to RUNNING");

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Battle started");

        } catch (Exception e) {
            System.out.println("Error starting battle: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    private void handleCodeSubmission(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO: Здесь будет логика отправки кода
        String sessionIdParam = request.getParameter("sessionId");
        String code = request.getParameter("code");

        System.out.println("Code submission for session: " + sessionIdParam);

        response.setContentType("application/json");
        response.getWriter().write("{\"status\":\"received\"}");
    }
}