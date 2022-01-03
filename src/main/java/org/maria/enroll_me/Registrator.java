package org.maria.enroll_me;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/enroll") // @WebServlet(name = "ShowHeaders", urlPatterns = {"/ShowHeaders"})
public class Registrator extends HttpServlet {
    @Override protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        httpServletRequest.setAttribute("name", "Devcolibri");
        httpServletRequest.getRequestDispatcher("datepicker.jsp").forward(httpServletRequest, httpServletResponse);
    }
}

