package org.example.servlets.session;

import org.example.entity.enums.SessionStatus;
import org.example.service.SessionService;
import org.example.service.impl.SessionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/check-session-status")
public class CheckSessionStatusServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionIdParam = request.getParameter("sessionId");
        if (sessionIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Session ID is required");
            return;
        }

        Long sessionId;
        try {
            sessionId = Long.parseLong(sessionIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid session ID");
            return;
        }

        try {
            SessionStatus status = sessionService.getSessionStatus(sessionId);

            response.setContentType("application/json");
            response.getWriter().write(String.format(
                    "{\"status\":\"%s\", \"sessionId\":%d}",
                    status.name(), sessionId
            ));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}