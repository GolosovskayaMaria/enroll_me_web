package org.maria.enroll_me.servlets;

import org.maria.enroll_me.ClientRow;
import org.maria.enroll_me.Meeting;
import org.maria.enroll_me.db.ClientsTable;
import org.maria.enroll_me.db.MeetingsTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Logger;

@WebServlet("/enroll") // @WebServlet(name = "ShowHeaders", urlPatterns = {"/ShowHeaders"})
public class SelfEnroll extends HttpServlet {
    @Override protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        String inviteId = httpServletRequest.getParameter("invite");
        try {
            Meeting meeting = MeetingsTable.select(inviteId);
            System.out.println(meeting);
            int clientId = meeting.getId();
            ClientRow client = ClientsTable.get(clientId);
            System.out.println(client);
            httpServletRequest.setAttribute("name", client.getName());
            httpServletRequest.setAttribute("client_id", Integer.toString (client.getId()));
            httpServletRequest.setAttribute("invite_id", inviteId);
            httpServletRequest.setAttribute("app_id", client.getApp_id());
            Logger.getLogger(this.getClass().getName()).info("Invite " + inviteId + " Client " + client.getName());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        httpServletRequest.getRequestDispatcher("datepicker.jsp").forward(httpServletRequest, httpServletResponse);
    }
}


