package org.maria.enroll_me.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DataBaseManager {
    public static java.sql.Connection getConnection() throws SQLException
    {
        java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/enroll_me", "root", "root");
        Logger.getLogger(DataBaseManager.class.getName()).info("connection success");
        return connection;
    }
}
