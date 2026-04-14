package org.example.servlets.session;

import org.example.dto.request.CreateSessionRequestDto;
import org.example.dto.response.CreateSessionResponseDto;
import org.example.entity.ProblemEntity;
import org.example.service.ProblemService;
import org.example.service.SessionService;
import org.example.service.impl.ProblemServiceImpl;
import org.example.service.impl.SessionServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/create-session")
public class CreateSessionServlet extends HttpServlet {

    private final SessionService sessionService = new SessionServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String lobbyName = request.getParameter("lobbyName");
        String problemIdStr = request.getParameter("problemId");
        String timeLimitStr = request.getParameter("timeLimit");

        if (problemIdStr == null || problemIdStr.isBlank()) {
            request.setAttribute("error", "Выберите задачу");
            request.getRequestDispatcher("/jsp/session/create-session.jsp").forward(request, response);
            return;
        }

        long problemId;
        int minutes;
        try {
            problemId = Long.parseLong(problemIdStr);
            minutes = Integer.parseInt(timeLimitStr);
            if (minutes <= 0)
                minutes = 30;
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Неверные параметры");
            request.getRequestDispatcher("/jsp/session/create-session.jsp").forward(request, response);
            return;
        }

        // конвертация минут в секунды (важно)
        int durationSeconds = minutes * 60;

        // сформировать DTO/модель так, чтобы в сервис шёл taskId и durationSeconds
        CreateSessionRequestDto dto = new CreateSessionRequestDto(
                lobbyName,
                problemId,          // problemId -> taskId внутри сервиса/репозитория
                durationSeconds,    // Убедись, что DTO и SessionService принимают seconds, не minutes
                "on".equals(request.getParameter("soloMode"))
        );

        long ownerId = (Long) request.getSession().getAttribute("user_id");
        CreateSessionResponseDto res = sessionService.create(dto, ownerId);
        response.sendRedirect(request.getContextPath() + "/lobby?sessionId=" + res.getSessionId());


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProblemService problemService = new ProblemServiceImpl();
        List<ProblemEntity> problems =  problemService.findAll();
        request.setAttribute("problems", problems);

        String contextPath = request.getContextPath();
        request.setAttribute("contextPath", contextPath);
        request.getRequestDispatcher("/jsp/session/create-session.jsp").forward(request, response);

    }

}