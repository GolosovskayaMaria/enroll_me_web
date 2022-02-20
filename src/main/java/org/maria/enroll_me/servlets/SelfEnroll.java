package org.maria.enroll_me.servlets;

import org.maria.enroll_me.ClientRow;
import org.maria.enroll_me.db.ClientsManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet("/enroll") // @WebServlet(name = "ShowHeaders", urlPatterns = {"/ShowHeaders"})
public class SelfEnroll extends HttpServlet {
    @Override protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        httpServletRequest.setAttribute("name", "Devcolibri");
        // TODO здесь мы разберем url и подготовим выбор даты

        //try {
            //ClientRow row = new ClientRow(0, "Test Insert", "+7911", "vk", "Gatchina");
            //ClientsManager.insert(row);
            //ClientRow firstRow = ClientsManager.select();
            //Logger.getLogger(this.getClass().getName()).info(firstRow.toString());
        //}
        //catch (SQLException e) {
          //  e.printStackTrace();
        //}
        httpServletRequest.getRequestDispatcher("datepicker.jsp").forward(httpServletRequest, httpServletResponse);
    }
}
