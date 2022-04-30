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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
                 httpServletRequest.setCharacterEncoding("UTF-8");

                    String appId = httpServletRequest.getParameter("app_id");
                    String name = httpServletRequest.getParameter("name");
                    byte[] bytes = name.getBytes(StandardCharsets.ISO_8859_1);
                    name = new String(bytes, StandardCharsets.UTF_8);

                    String phone = httpServletRequest.getParameter("phone");
                    String social = httpServletRequest.getParameter("social");
                    String location = httpServletRequest.getParameter("location");
                    ClientRow newClient = new ClientRow(0, appId, name, phone, social, location);
                    ClientRow record = ClientsTable.insert(newClient);
                    String jsonBody = this.gson.toJson(record);
                    OutputStream out = httpServletResponse.getOutputStream();
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    out.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "/del_client":
              int  clientId = Integer.parseInt(httpServletRequest.getParameter("client_id"));
                try {
                    ClientsTable.del_clients(clientId);
                    PrintWriter out = httpServletResponse.getWriter();
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    out.print("OK");
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
                    OutputStream out = httpServletResponse.getOutputStream();
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    out.write(jsonBody.getBytes(StandardCharsets.UTF_8));
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
                    String MeetingGUID = MeetingsTable.createInvite(appId, userId,httpServletRequest.getParameter("data"));
                    String jsonBody = this.gson.toJson(MeetingGUID);
                    OutputStream out = httpServletResponse.getOutputStream();
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    out.write(jsonBody.getBytes(StandardCharsets.UTF_8));
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

