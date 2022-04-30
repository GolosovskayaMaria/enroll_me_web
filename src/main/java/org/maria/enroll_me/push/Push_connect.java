package org.maria.enroll_me.push;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Logger;

@WebServlet("/api/push/*")
public class Push_connect  extends HttpServlet {

    @Override protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws   IOException {
        String path = httpServletRequest.getPathInfo();
        Logger.getLogger(this.getClass().getName()).info(path);
switch(path){
    case "/address":
        Gson gson = new GsonBuilder().create();
        Send send= new Send();
        send.to="/topics/my_little_topic";
        send.notification.body = "address";
        send.notification.title = "For Master";
        send.notification.icon = "ic_launcher";
        send.data.ip = "http://" + getIpAddress()+":8888";
        send.data.name = httpServletRequest.getParameter("from");
        send.data.type = 1;
      //  send.data.meeting = -1;
        String jsonBody = gson.toJson(send);
      //  System.out.println(jsonBody);
      Push_Admin.sendPush(jsonBody );
      //  Long startTime = System.currentTimeMillis();//t1
      //  httpServletRequest.setAttribute( "startTime", startTime );
        break;

}
    }
    public static String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
   e.printStackTrace();
        }
        return ip;
    }
}
