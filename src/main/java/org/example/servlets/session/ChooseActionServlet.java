package org.example.servlets.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/choose-action")
public class ChooseActionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("create-session".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/create-session");
        } else if ("join-session".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/join-session");
        } else {
            response.sendRedirect(request.getContextPath() + "/choose-action");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contextPath = request.getContextPath();
        request.setAttribute("contextPath", contextPath);
        request.getRequestDispatcher("/jsp/session/choose-action.jsp").forward(request, response);

    }

}
