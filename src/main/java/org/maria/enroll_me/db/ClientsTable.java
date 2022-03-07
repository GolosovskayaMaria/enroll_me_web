package org.maria.enroll_me.db;

import com.mysql.cj.xdevapi.Client;
import org.maria.enroll_me.ClientRow;
import org.maria.enroll_me.db.DataBaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Logger;

public class ClientsTable {

    public static ClientRow insert(ClientRow row) throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "INSERT INTO clients (ApplicationId, Name, Phone, SocialMedia, Location) VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, row.getApp_id());
            ps.setString(2, row.getName());
            ps.setString(3, row.getPhone());
            ps.setString(4, row.getSocilaMedia());
            ps.setString(5, row.getLocation());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()) {
                int id = keys.getInt(1);
                row.setId(id);
                return row;
            }
            throw new SQLException("cant insert ClientRow");
        }
    }

    public static LinkedList<ClientRow> select(String appId) throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "SELECT * FROM clients WHERE ApplicationId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, appId);
            ResultSet results = ps.executeQuery();
            LinkedList<ClientRow> clients = new LinkedList<ClientRow>();
            while(results.next()) {
                ClientRow row = new ClientRow(
                        results.getInt("ID"),
                        results.getString("ApplicationId"),
                        results.getString("Name"),
                        results.getString("Phone"),
                        results.getString("SocialMedia"),
                        results.getString("Location"));
                clients.add(row);
            }
            return  clients;
        }
    }

    public static ClientRow get(int clientId) throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "SELECT * FROM clients WHERE ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientId);
            ResultSet results = ps.executeQuery();
            if(results.next()) {
                ClientRow row = new ClientRow(
                        results.getInt("ID"),
                        results.getString("ApplicationId"),
                        results.getString("Name"),
                        results.getString("Phone"),
                        results.getString("SocialMedia"),
                        results.getString("Location"));
                return row;
            } else
            {
                throw new SQLException("client not found");
            }
        }
    }
}

