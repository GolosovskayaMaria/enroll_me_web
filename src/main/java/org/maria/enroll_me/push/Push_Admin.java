package org.maria.enroll_me.push;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.security.Key;

public class Push_Admin {
  private static final String Key="xxx";
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
