package org.maria.enroll_me.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

@WebServlet("/api/enroll/*") // @WebServlet(name = "ShowHeaders", urlPatterns = {"/ShowHeaders"})
public class SelfEnroll extends HttpServlet {

    private Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyyy HH:mm:ss").create();

    @Override protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {

        String path = httpServletRequest.getPathInfo();
        Logger.getLogger(this.getClass().getName()).info(path);

        switch(path) {
            case "/invite":
                try {
                    String inviteId = httpServletRequest.getParameter("invite");
                    Meeting meeting = MeetingsTable.select(inviteId);
                    System.out.println(meeting);
                    int clientId = meeting.getId();
                    ClientRow client = ClientsTable.get(clientId);
                    System.out.println(client);
                    httpServletRequest.setAttribute("name", client.getName());
                    httpServletRequest.setAttribute("client_id", Integer.toString(client.getId()));
                    httpServletRequest.setAttribute("invite_id", inviteId);
                    httpServletRequest.setAttribute("app_id", client.getApp_id());
                    Logger.getLogger(this.getClass().getName()).info("Invite " + inviteId + " Client " + client.getName());
                    httpServletRequest.getRequestDispatcher("datepicker.jsp").forward(httpServletRequest, httpServletResponse);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "/schedule":
                try {
                    String appId = httpServletRequest.getParameter("app_id");
                    LinkedList<Meeting> meetings = MeetingsTable.selectAll(appId);
                    System.out.println(meetings);
                    String jsonBody = this.gson.toJson(meetings);
                    PrintWriter out = httpServletResponse.getWriter();
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    out.print(jsonBody);
                    out.flush();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            default:
                Logger.getLogger(this.getClass().getName()).warning("wrong path" + path);
                return;
        }
    }
}


