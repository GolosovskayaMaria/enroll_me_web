package org.maria.enroll_me.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.maria.enroll_me.ClientRow;
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

@WebServlet("/api/clients/*") // @WebServlet(name = "ShowHeaders", urlPatterns = {"/ShowHeaders"})
// /api/clients/get - return json all clients list
public class Clients extends HttpServlet {

    private Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyyy HH:mm:ss").create();

    @Override protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {

        String path = httpServletRequest.getPathInfo();
        Logger.getLogger(this.getClass().getName()).info(path);

        switch(path) {
            case "/add":
                try {
                    String appId = httpServletRequest.getParameter("app_id");
                    String name = httpServletRequest.getParameter("name");
                    String phone = httpServletRequest.getParameter("phone");
                    String social = httpServletRequest.getParameter("social");
                    String location = httpServletRequest.getParameter("location");
                    ClientRow newClient = new ClientRow(0, appId, name, phone, social, location);
                    ClientRow record = ClientsTable.insert(newClient);
                    String jsonBody = this.gson.toJson(record);
                    PrintWriter out = httpServletResponse.getWriter();
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    out.print(jsonBody);
                    out.flush();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "/get_clients":
                try {
                    String appId = httpServletRequest.getParameter("app_id");
                    LinkedList<ClientRow> clients = ClientsTable.select(appId);
                    String jsonBody = this.gson.toJson(clients);
                    PrintWriter out = httpServletResponse.getWriter();
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    out.print(jsonBody);
                    out.flush();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "/invite":
                try {
                    String appId = httpServletRequest.getParameter("app_id");
                    String userIdStr = httpServletRequest.getParameter("user_id");
                    int userId = Integer.parseInt(userIdStr);
                    String MeetingGUID = MeetingsTable.createInvite(appId, userId);
                    String jsonBody = this.gson.toJson(MeetingGUID);
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
                Logger.getLogger(this.getClass().getName()).warning("wrong path"+path);
                return;
        }
    }
}

