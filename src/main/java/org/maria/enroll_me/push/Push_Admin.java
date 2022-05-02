package org.maria.enroll_me.push;

import org.maria.enroll_me.db.DataBaseManager;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.security.Key;
import java.util.logging.Logger;

public class Push_Admin {
  private static final String Key="AAAA3ZtE1Fk:APA91bH-5AbqQoUP9XTn165RinMSymt0xpDuS4Ku_B7cyXaNUkABdvNw181Nun794v70rBBm6o2ahENqMuHxiOBVvSNwME9x9IxddZ_oqsvyZzXBFJxRmd7aBz0A8kJz5Ovo8_SDvNZV";
    public static void sendPush(final String send ){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
                connect.setRequestProperty ("Authorization","key="+ Key);
                connect.addRequestProperty ("Content-type" , "application/json");
                connect.setUseCaches(false);
                connect.setDoOutput(true);
                connect.setDoInput(true);
                connect.setRequestMethod("POST");
                OutputStreamWriter writer = new OutputStreamWriter(connect.getOutputStream());
                writer.write(send);
                writer.flush();
                String out = readMultipleLinesRespone(connect);
                System.out.println(out);
                Logger.getLogger(DataBaseManager.class.getName()).info("push " + send);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
        }).start();
    }
    static String readMultipleLinesRespone(HttpsURLConnection connect) throws IOException {
        InputStream inputStream;
        if (connect != null) {
            inputStream = connect.getInputStream();
        } else {
            throw new IOException("!");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String response ="";
        String line;
        while ((line = reader.readLine()) != null) {
            response+=line;
        }
        reader.close();
        //String aaa = connect.getContentEncoding();
//			String encoding = connect.getContentEncoding() == null ? "UTF-8"
//		          : connect.getContentEncoding();
        return response;
    }
}
