package org.maria.enroll_me;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.*;


@WebServlet("/DatePicker")
public class DatePicker extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dob = request.getParameter("dob");
            Logger.getLogger(DatePicker.class.getName()).info("Your Date of birth "+dob+ " is submited");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString();
            System.out.println(sStackTrace);
            Logger.getLogger(DatePicker.class.getName()).warning(sStackTrace);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }}


