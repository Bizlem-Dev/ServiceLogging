package com.userPanel.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.userPanel.util.ConnectionImpl;

public class ADBConnection {
    String dbtime = "";
    String query = null;
    private Connection conn = null;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public ADBConnection() {
        // TODO Auto-generated constructor stub

        PropUtil prp = new PropUtil();
        prp.setprop();
    }

    public List<String> GetUidpass(String user) {
        List<String> keys = new ArrayList<String>();
        try {

            query = "SELECT entity_id,password FROM person  where username='"
                    + user + "'";
            conn = ConnectionImpl.getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                keys.add(rs.getString(1));
                keys.add(rs.getString(2));
            }

            conn.close();
            statement.close();
            rs.close();

            return keys;
        } // end try

        catch (Exception e) {
            e.printStackTrace();
            return keys;
        }

    }

    public String Getuid(String user) {
        String keys = null;
        try {
            query = "SELECT entity_id FROM person  where username=?";
            conn = ConnectionImpl.getConnection();
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                keys = rs.getString(1);
            }

            conn.close();
            preparedStatement.close();
            rs.close();

            return keys;
        } // end try

        catch (Exception e) {
            e.printStackTrace();
            return keys;
        }
    }

    public String Getpass(String user) {
        String keys = null;
        try {
            query = "SELECT password FROM person  where username=?";
            conn = ConnectionImpl.getConnection();
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                keys = rs.getString(1);
            }

            conn.close();
            preparedStatement.close();
            rs.close();

            return keys;
        } // end try

        catch (Exception e) {
            e.printStackTrace();
            return keys;
        }
    }

    public String SaveUser(String UserId, String serviceId, String username,
            String email, String isAdmin, int sno, String openid) {
        String keys = "failure";
        try {
            query = "UPDATE rave2.adminpanel SET username=?,email=?,isAdmin=?,openid=? WHERE serviceId=? && UserId=? && Sno=?";
            conn = ConnectionImpl.getConnection();
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, isAdmin);
            preparedStatement.setString(4, openid);
            preparedStatement.setString(5, serviceId);
            preparedStatement.setString(6, UserId);
            preparedStatement.setInt(7, sno);

            int result = preparedStatement.executeUpdate();

            conn.close();
            preparedStatement.close();
            if (result == 1)
                keys = "success";

            return keys;
        } // end try

        catch (Exception e) {
            e.printStackTrace();
            return keys;
        }
    }

}
