package com.userPanel.util;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author Administrator
 * @version 1.0
 * @created 29-May-2012 12:10:18 PM
 */
public class ConnectionImpl {

    private static ResourceBundle bundle = null;

    public ConnectionImpl() {
    }

    public static Connection getConnection() throws Exception {
        {
            bundle = ResourceBundle.getBundle("serverConfig");
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(
                    bundle.getString("ravedbUrl"),
                    bundle.getString("ravedbuser"),
                    bundle.getString("ravedbpswd"));
            return con;

        }
    }
    public static Connection getConnectionServiceDb() throws Exception {
        {
            bundle = ResourceBundle.getBundle("serverConfig");
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(
                    bundle.getString("servicedbUrl"),
                    bundle.getString("ravedbuser"),
                    bundle.getString("ravedbpswd"));
            return con;

        }
    }
    
    public static Connection getConnectionOpenfire() throws Exception {
        {
            bundle = ResourceBundle.getBundle("serverConfig");
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(
                    bundle.getString("openfiredbUrl"),
                    bundle.getString("ravedbuser"),
                    bundle.getString("ravedbpswd"));
            return con;

        }
    }

}