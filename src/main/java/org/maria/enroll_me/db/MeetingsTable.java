package org.maria.enroll_me.db;

import org.maria.enroll_me.ClientRow;
import org.maria.enroll_me.Meeting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

public class MeetingsTable {

    public static String createInvite(String appId,int userId)  throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "INSERT INTO meetings (ApplicationId, UserId) VALUES(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, appId);
            ps.setInt(2, userId);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()) {
                int id = keys.getInt(1);
                Logger.getLogger(DataBaseManager.class.getName()).info("success invite " + String.valueOf(id));
                return String.valueOf(id);
            }
            throw new SQLException("cant insert ClientRow");
        }
    }

    public static void updateInvite(int invite_id, java.util.Date utilDate)  throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {

            String sql = "UPDATE meetings set MeetupDate=? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(utilDate.getTime()));
            ps.setInt(2, invite_id);
            ps.executeUpdate();
            Logger.getLogger(DataBaseManager.class.getName()).info("success invite update" + String.valueOf(invite_id));
        }
    }

    public static Meeting select(String inviteId) throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "SELECT * FROM meetings WHERE ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, inviteId);
            ResultSet results = ps.executeQuery();
            while(results.next()) {
                return new Meeting(
                        results.getInt("ID"),
                        results.getString("ApplicationId"),
                        results.getInt("UserId"),
                        results.getDate("MeetupDate"),
                        results.getDate("CreateDate"));
            }
            throw new SQLException("meeting not found");
        }
    }

    public static LinkedList<Meeting> selectAll(String appId) throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "SELECT * FROM meetings WHERE ApplicationId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, appId);
            ResultSet results = ps.executeQuery();
            LinkedList<Meeting> meetings = new LinkedList<>();
            while(results.next()) {
                meetings.add(new Meeting(
                        results.getInt("ID"),
                        results.getString("ApplicationId"),
                        results.getInt("UserId"),
                        results.getDate("MeetupDate"),
                        results.getDate("CreateDate")));
            }
            return meetings;
        }
    }

}

