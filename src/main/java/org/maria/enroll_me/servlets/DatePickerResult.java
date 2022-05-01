package org.maria.enroll_me.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.maria.enroll_me.ClientRow;
import org.maria.enroll_me.Meeting;
import org.maria.enroll_me.db.ClientsTable;
import org.maria.enroll_me.db.MeetingsTable;
import org.maria.enroll_me.push.Push_Admin;
import org.maria.enroll_me.push.Push_connect;
import org.maria.enroll_me.push.Send;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;


// https://www.codejava.net/java-ee/jsp/how-to-create-dynamic-drop-down-list-in-jsp-from-database


@WebServlet("/api/enroll/date_picker")
public class DatePickerResult extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        super.doPut(request, httpServletResponse);
     //   String client_id = request.getParameter("client_id");
     //   String app_id = request.getParameter("app_id");
        int invite_id = Integer.parseInt(request.getParameter("invite_id"));
        String time = request.getParameter("time");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
System.out.println(time);
        try {
            Date   date = dateFormat.parse(time);
            MeetingsTable.updateInvite(invite_id, date);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Logger.getLogger(this.getClass().getName()).info("Date " + time + " Time " + time  );

    }

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        String strDate;// = request.getParameter("dob");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = request.getParameter("time");
        System.out.println(request.getParameter("invite_id") +" " + request.getParameter("app_id") +" " + " Time " + time );
        String[] sp = time.split(" ");
        if(sp.length == 3)
       strDate = sp[0] +" " + sp[2];
        else strDate = sp[0] +" " + sp[1];
        System.out.println( strDate  +"               " + time);
       Date date = dateFormat.parse(strDate);

        String client_id = request.getParameter("client_id");
        String app_id = request.getParameter("app_id");
        int invite_id = Integer.parseInt(request.getParameter("invite_id"));

        MeetingsTable.updateInvite(invite_id, date);

        Logger.getLogger(this.getClass().getName()).info("Date " + strDate + " Time " + time + " ClienId " + client_id + " AppId " + app_id);

        // PUSH https://github.com/pusher/push-notifications-server-java
        Gson gson = new GsonBuilder().create();
        Send send= new Send();

        Meeting meeting = MeetingsTable.select(Integer.toString(invite_id));
        Logger.getLogger(this.getClass().getName()).info("meeting " + meeting);
        int userId = meeting.getUserId();
        ClientRow client = ClientsTable.get(userId);
        String clientName = client.getName();
        Logger.getLogger(this.getClass().getName()).info("clientName " + clientName);

        send.to="/topics/my_little_topic";
        send.notification.body = strDate;
        send.notification.title = clientName;
        send.notification.icon = "ic_launcher";
        send.data.ip = "http://" + Push_connect.getIpAddress()+":8888";
        send.data.name = app_id;
        send.data.type = 2;
        send.data.meeting = invite_id;

        String jsonBody = gson.toJson(send);
        Push_Admin.sendPush(jsonBody );
        try {
            writer.println("<p>Date: " + strDate + "</p>");
            writer.println("<p>Time: " + time + "</p>");
        } finally {
            writer.close();
        }
    }
}

