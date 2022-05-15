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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

@WebServlet("/api/enroll/*") // @WebServlet(name = "ShowHeaders", urlPatterns = {"/ShowHeaders"})
public class SelfEnroll extends HttpServlet {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String getTimes(String api_id , String data) {
        try {
            LinkedList<Meeting> meetings = MeetingsTable.selectAll(api_id);
          //  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         //   System.out.println(meetings.size());
         //   String  out="";
            ArrayList<Integer> listh = new ArrayList();
            for(int i=10;i<19;i++) listh.add(i) ;
            for(Meeting  meeting : meetings){

                String[] dats = meeting.getMeetupDate().split(" ");
if(dats[0].startsWith(data)){
    for(int i=0;i<listh.size();i++)
        if(dats[1].startsWith("" + listh.get(i)) ){
            listh.remove(i);
    }
}
             //   System.out.println(meeting );
           //     meeting.
            }
            String out="";
            for(int i : listh){
                out +="" +i +":00:00/";
            }
            return out.substring(0 , out.length()-2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "  ";
    }

    @Override protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {

        String path = httpServletRequest.getPathInfo();
        Logger.getLogger(this.getClass().getName()).info("path: " + path);

        switch(path) {
            case "/invite_self":

                return;
            case "/invite":
                try {
                    String inviteId = httpServletRequest.getParameter("invite");
                    Meeting meeting = MeetingsTable.select(inviteId);

                    int clientId = meeting.getUserId();

                    ClientRow client = ClientsTable.get(clientId);

                    httpServletRequest.setAttribute("name", client.getName());
                    httpServletRequest.setAttribute("client_id", Integer.toString(client.getId()));
                    httpServletRequest.setAttribute("invite_id", inviteId);
                    httpServletRequest.setAttribute("app_id", client.getApp_id());
                  long limit = 3600000*24*2;

                  Logger.getLogger(this.getClass().getName()).info("Invite " + inviteId + " Client " + client.getName());
             if(((System.currentTimeMillis() - meeting.getCreateDate().getTime()) < limit)
                     && meeting.getMeetupDate().trim().endsWith("00:00:00")){
                 OutputStream out = httpServletResponse.getOutputStream();
             //    httpServletResponse.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
                 httpServletResponse.setContentType("text/html; charset=UTF-8");
                 httpServletResponse.setCharacterEncoding("UTF-8");

                 String message = getInviteSelf(client, inviteId, getTimes(client.getApp_id() ,
                         meeting.getMeetupDate().split(" ")[0]) ,meeting);
                 byte[] byts = message.getBytes("UTF-8");
                 out.write(byts);

                 out.flush();
             } else if(((System.currentTimeMillis() - meeting.getCreateDate().getTime()) < limit)){
                 OutputStream out = httpServletResponse.getOutputStream();
                 httpServletResponse.setContentType("text/html");
                 httpServletResponse.setCharacterEncoding("UTF-8");
                 String[] sp = meeting.getMeetupDate().split(" ");

                 String s = "<html><body><H1>По этой ссылке была проведена запись.</H1> " +
                         "<p>" + client.getName() + "</p>" +
                         "<p>Date: " + sp[0] + "</p>" +
                         "<p>Time: " + sp[1] + "</p>" +
                     //    "<H3>Попросите у мастера другую ссылку.</H3>" +
                         "</body></html>";
                 out.write(s.getBytes("UTF-8"));
                 out.flush();
             }
             //     httpServletRequest.getRequestDispatcher("/datepicker.jsp?"+"name="+client.getName()).forward(httpServletRequest, httpServletResponse);
              else{
                 OutputStream out = httpServletResponse.getOutputStream();
                 httpServletResponse.setContentType("text/html");
                 httpServletResponse.setCharacterEncoding("UTF-8");
                 String s = "<html><body><H2>Прошло более 2-х дней.</H2> " +
                         "<H3>Вы не можете записаться на это время.</H3>" +
                         "</body></html>";
                 out.write(s.getBytes("UTF-8"));
                 out.flush();
             }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            case "/schedule_day":
                try {
                    String appId = httpServletRequest.getParameter("app_id");
                    int day=  Integer.parseInt( httpServletRequest.getParameter("day"));
                    int mounth = Integer.parseInt(httpServletRequest.getParameter("mounth"));
                    LinkedList<Meeting> meetings = null;
                    try {
                        meetings = MeetingsTable.selectAllDay(appId , mounth, day);
                    } catch (ParseException e) {
                       return;
                    }

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
            case "/schedule":
                try {
                    Logger.getLogger(this.getClass().getName()).info("query: " + httpServletRequest.getQueryString());
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
            case "/del_meeting":
                int  meetingId = Integer.parseInt(httpServletRequest.getParameter("meeting_id"));
                try {
                    MeetingsTable.del_Meeting(meetingId);
                    PrintWriter out = httpServletResponse.getWriter();
                    httpServletResponse.setContentType("application/json");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    out.print("OK");
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

    private String getInviteSelf( ClientRow client , String inviteId , String times , Meeting meeting) {
       String data = meeting.getMeetupDate();
      //  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy  HH:mm:ss");

        String start ="<html><h1>Здравствуйте, " + client.getName() +"</h1><br>\n" +

                "<form action=\"date_picker\" method=\"POST\">\n" +
                "<input hidden value=\"" + client.getId() + "\"  type=\"text\" name=\"client_id\"/>\n" +
                "<input hidden value=\""+client.getApp_id()+"\" name=\"app_id\" id=\"app_id\"/>\n" +
                "<input hidden value=\""+inviteId+"\" name=\"invite_id\" id=\"invite_id\"  />\n" +
                "<input hidden value=\""+client.getName()+"\" name=\"user_name\" id=\"user_name\"  />\n" +
                "<label style=\"color:black;font-size:25px; for=\"datepicker\">Выберете удобное для Вас время для записи " + data.split(" ")[0]+"</label>\n<br><br>" +
              //  "<input type=\"text\" name=\"dob\" id=\"datepicker\">\n<br><br>" +
                "<label style=\"color:black;font-size:25px; for=\"from\">Время:</label>\n<br><br>" +
                "<select name=\"time\" id=\"time\">\n";
        String body = "";
        String[] split = times.split("/");
System.out.println(data.split(" ")[0]);
        System.out.println(times);
for( String s : split ){
    body += "<option value=\""+data.split("  ")[0]+" "+s.trim()+"\">" +s.substring(0 , 2) +"</option>\n";
}
        String end = "</select>\n<br><br>" +
                "<input type=\"submit\" value=\"Записаться\">\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>";
        return start + body + end;
    }
}


