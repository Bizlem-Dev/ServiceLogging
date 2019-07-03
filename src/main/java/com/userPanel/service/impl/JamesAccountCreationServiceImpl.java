package com.userPanel.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import com.userPanel.service.JamesAccountCreationService;

public class JamesAccountCreationServiceImpl implements
        JamesAccountCreationService {
    private ResourceBundle bundle = ResourceBundle.getBundle("serverConfig");
    private String serverAddress = bundle.getString("james_server");
    private int port = Integer.parseInt(bundle.getString("james_port"));
    private String userId = bundle.getString("james_userId"); // administrators
                                                              // userid
    private String password = bundle.getString("james_password");

    private Socket connectionSocket = null;
    private PrintStream out = System.err;
    private BufferedReader in = null;
    private boolean connectedStatus;

    /*
     * public JamesAccountCreationServiceImpl(String server, int port, String
     * uid, String pwd) { this.userId = uid; this.password = pwd;
     * this.serverAddress = server; this.port = port;
     * 
     * this.connectedStatus = connect(); }
     */

    public boolean isConnected() {
        return this.connectedStatus;
    }

    private boolean connect() {
        try {
            this.connectionSocket = new Socket(this.serverAddress, this.port);
            this.out = new PrintStream(connectionSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(
                    connectionSocket.getInputStream()));
            // Login to the server
            // log (in.readLine()); //wait for the connection
            out.println(this.userId);// send userid
            // log(in.readLine());//wait for \n
            out.println(this.password);// send the password
            // log (in.readLine());
            if (out.checkError()) {
                this.connectedStatus = true;
                log("Server accepted connection");
                return true;
            } else {
                this.connectedStatus = false;
                log("Server refused connection");
                return false;
            }
        } catch (UnknownHostException e) {
            this.connectedStatus = false;
            log("Unable to connect to the host!");
            return false;
        } catch (IOException e) {
            this.connectedStatus = false;
            log("Couldn't get I/O for " + "the connection to server.");
            return false;
        }
    }

    private void log(String note) {
        System.err.println(getClass().getName() + " " + note);
    }

    public boolean verifyUser(String userName) throws IOException {
        if (!isConnected())
            connect();
        if (!isConnected())
            log("broken"); // do something

        String response = execute("verify " + userName); // verify if specified
        // user exist
        boolean exists = false;
        if (null != response && -1 == response.indexOf("does not"))
            exists = true;

        return exists;
    }

    public boolean addUser(String userName, String password) throws IOException {
        if (!isConnected())
            connect();
        if (!isConnected())
            log("broken: no connection to James"); // do something

        String response = execute("adduser " + userName + " " + password);
        boolean exists = false;
        if (null == response) {
        } else if (0 <= response.indexOf("added")) {
            exists = true;
        } else if (0 <= response.indexOf("already")) {
            exists = false;
        }

        return exists;
    }

    private String execute(String command) throws IOException {
        String serverSays;
        if (connectedStatus == false)
            connect();
        // now execute the command
        log("executing: -" + command + "-");
        out.println(command);
        out.println("###");
        String returnString = "";
        serverSays = in.readLine();
        while (serverSays != null && serverSays.endsWith("###") == false) {
            returnString += serverSays + "\n";
            serverSays = in.readLine();
        }
        return returnString;
    }
}
