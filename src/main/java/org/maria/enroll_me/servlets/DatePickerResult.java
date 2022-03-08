package org.maria.enroll_me.servlets;

import lombok.SneakyThrows;
import org.maria.enroll_me.db.MeetingsTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;


@WebServlet("/date_picker")
public class DatePickerResult extends HttpServlet {

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        String strDate = request.getParameter("dob");

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String time = request.getParameter("time");
        strDate = strDate + " " + time;
        Date date = dateFormat.parse(strDate);
        System.out.println(strDate);
        System.out.println(date);
        String client_id = request.getParameter("client_id");
        String app_id = request.getParameter("app_id");
        int invite_id = Integer.parseInt(request.getParameter("invite_id"));

        MeetingsTable.updateInvite(invite_id, date);

        Logger.getLogger(this.getClass().getName()).info("Date " + strDate + " Time " + time + " ClienId " + client_id + " AppId " + app_id);

        try {
            writer.println("<p>Date: " + date + "</p>");
            writer.println("<p>Time: " + time + "</p>");
        } finally {
            writer.close();
        }
    }
}

