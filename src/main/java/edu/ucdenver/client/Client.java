package edu.ucdenver.client;

/**
 * Test sample for Homework #5
 * Author: J. Pastorino
 *
 * This program should start run and terminate successfully in a couple seconds.
 * If your program does not end properly, check your server code.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    private final int serverPort;
    private final String serverIp;
    private boolean isConnected;
    private Socket serverConnection;
    private PrintWriter output;
    private BufferedReader input;

    public Client(String ip, int port) {
        this.serverPort = port;
        this.serverIp = ip;
        this.isConnected = false;
    }

    public Client(){
        this.serverIp = "127.0.0.1";
        this.serverPort = 10000;
        this.isConnected = false;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    private PrintWriter getOutputStream() throws IOException {
        return new PrintWriter(this.serverConnection.getOutputStream(), true);
    }

    private BufferedReader getInputStream() throws IOException {
        return new BufferedReader(new InputStreamReader(this.serverConnection.getInputStream()));
    }

    public void connect() {
        displayMessage("Attempting connection to Server");
        try {
            this.serverConnection = new Socket(this.serverIp, this.serverPort);
            this.output = this.getOutputStream();
            this.input = this.getInputStream();
            this.isConnected = true;  // at this point, no exception then we're connected!
            displayMessage("Connected");
        } catch (IOException e) { //If something went wrong....
            this.input = null;
            this.output = null;
            this.serverConnection = null;
            this.isConnected = false;
            displayMessage("Not Connected");
        }
    }

    public void disconnect() {
        displayMessage("Disconnecting From Server");
        try {
            this.input.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.output.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            this.serverConnection.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        displayMessage("Disconnected");
    }

    public String sendRequest(String request) throws IOException { //send message and returns the server response.
        displayMessage("Sending>> " + request);
        this.output.println(request);
        String srvResponse = this.input.readLine();
        displayMessage("Received>> " + srvResponse);
        return srvResponse;
    }

    private void displayMessage(String message) {  // We can improve this method to be log-type one
        System.out.println("[CLI]" + message);
    }


    public static void main(String[] args) {
        System.out.println("Main Ended");
    }

}