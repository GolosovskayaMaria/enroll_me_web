package org.maria.enroll_me.db;

import org.maria.enroll_me.ClientRow;
import org.maria.enroll_me.Meeting;

import java.sql.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.logging.Logger;

public class MeetingsTable {

    public static String createInvite(String appId,int userId, String data)  throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "INSERT INTO meetings (ApplicationId, UserId, MeetupDate) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, appId);
            ps.setInt(2, userId);
            System.out.println(data);
            ps.setString( 3, data);
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
        //  java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            ps.setString(1, dateFormat.format(utilDate));
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
                        results.getString("MeetupDate"),
                        results.getDate("CreateDate"));
            }
            throw new SQLException("meeting not found");
        }
    }
    public static LinkedList<Meeting> selectAllDay(String appId , int  mounth ,int day) throws SQLException , ParseException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "SELECT * FROM meetings WHERE  ApplicationId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, appId);
            ResultSet results = ps.executeQuery();
            LinkedList<Meeting> meetings = new LinkedList<>();
          //  long limit = 3600000*24;
            while(results.next()) {
               // Date de =    results.getDate("CreateDate");
                String MeetupDate = results.getString("MeetupDate");
            if(MeetupDate == null) continue;
               DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              java.util.Date de = dateFormat.parse(MeetupDate);
          if( de.getDate() == day && de.getMonth() == mounth)
                    meetings.add(new Meeting(
                            results.getInt("ID"),
                            results.getString("ApplicationId"),
                            results.getInt("UserId"),
                            results.getString("MeetupDate"),
                            results.getDate("CreateDate")));
            }
            return meetings;
        }
    }
    public static LinkedList<Meeting> selectAll(String appId) throws SQLException
    {
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "SELECT * FROM meetings WHERE  ApplicationId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, appId);
            ResultSet results = ps.executeQuery();
            LinkedList<Meeting> meetings = new LinkedList<>();
         //   long limit = 3600000*24;
            while(results.next()) {
            Date de =    results.getDate("CreateDate");
          //  if(((System.currentTimeMillis() - de.getTime()) < limit))
                meetings.add(new Meeting(
                        results.getInt("ID"),
                        results.getString("ApplicationId"),
                        results.getInt("UserId"),
                        results.getString("MeetupDate"),
                        results.getDate("CreateDate")));
            }
            return meetings;
        }
    }
    public static void del_Meeting(int MeetingId) throws SQLException{
        try(Connection conn = DataBaseManager.getConnection()) {
            String sql = "DELETE FROM meetings WHERE ID=" + MeetingId ;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
        }
    }
}

