package org.maria.enroll_me;
import javax.servlet.http.*;
import java.io.IOException;

public class DatePicker extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.getWriter().print("Hello from org.maria.enroll_me.DatePicker");
    }
}
