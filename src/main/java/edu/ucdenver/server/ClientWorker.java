package edu.ucdenver.server;


import edu.ucdenver.tournament.Tournament;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientWorker implements Runnable {

    private Socket connection;
    private Boolean keepRunningClient;
    private Server server;

    public ClientWorker(Server server, Socket connection){

        this.connection = connection;
        this.server = server;
        this.keepRunningClient = true;

    }


    @Override
    public void run() {

        PrintWriter output = null;
        BufferedReader input = null;
        String clientMsg;
        String serverResponse;


        try{
            output = getOutputStream(this.connection);
            input = getInputStream(this.connection);

            // client running set to false if clientMsg = T|
            while(this.keepRunningClient){
                try {
                    clientMsg = input.readLine();
                    if(clientMsg.equals("T|")){
                        sendMessage("0|OK", output);
                        server.removeCW(this);
                        break;
                    }
                    else if(clientMsg.equals("TERMINATE|")){
                        // send message to server to terminate
                        this.server.shutdown();
                        break;
                    }
                    else{
                        serverResponse = processClientMessage(clientMsg);
                        sendMessage(serverResponse, output);
                    }
                }
                catch(Exception e){
                    System.err.println(e);
                    break;
                }
            }

        }
        catch(IOException ioe) {
            System.err.println(ioe);
        }
        finally{
            closeConnection(this.connection, input, output);
        }

    }

    private void displayMessage(String message){
        System.out.println("[SRV] " + message);
    }

    private PrintWriter getOutputStream(Socket socket) throws IOException{
        return new PrintWriter(socket.getOutputStream(), true);
    }

    private BufferedReader getInputStream(Socket socket) throws IOException{
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void closeConnection(Socket socket, BufferedReader buffRead, PrintWriter printWrite){
        displayMessage("Disconnecting From Server");

        try {
            buffRead.close();

        } catch (IOException | NullPointerException e) {
            System.out.println("Input socket.");
            e.printStackTrace();
        }

        try {
            printWrite.close();

        } catch (NullPointerException e) {
            System.out.println("Output socket.");
            e.printStackTrace();
        }

        try{
            socket.close();
            this.connection.close();
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        displayMessage("Disconnected");
    }

    private void sendMessage(String message, PrintWriter printWrite){
        printWrite.println(message);
    }

    protected String processClientMessage(String message) throws IllegalArgumentException{
        // parsing command for tournament handling based on parsed message

        String[] splitMessage = message.split("\\|");
        Tournament t = server.getTournament();
        switch(splitMessage[0]){
            case "D":
                try {
                    t.addTeam(splitMessage[1], splitMessage[2]);
                    return "0|OK|Added team to tournament";
                }
                catch(IllegalArgumentException iae){
                    return "1|ERR|" + iae;
                }
            case "C":
                try {
                    t.addCountry(splitMessage[1]);
                    return "0|OK|Added Country";
                }
                catch(IllegalArgumentException iae){
                    return "1|ERR|" + iae;
                }
            case "R":
                try {
                    t.addReferee(splitMessage[1], splitMessage[2]);
                    return "0|OK|Referee added to tournament";
                }
                catch(IllegalArgumentException iae){
                    return "1|ERR|" + iae;
                }
            case "P":
                try {
                    t.addPlayer(splitMessage[1], splitMessage[2], Integer.parseInt(splitMessage[3]), Double.parseDouble(splitMessage[4]), Double.parseDouble(splitMessage[5]));
                    return "0|OK|Player added to tournament";
                }
                catch(IllegalArgumentException iae){
                    return "1|ERR|" + iae;
                }
            case "M":
                try {
                    t.addMatch(LocalDateTime.parse(splitMessage[1]), splitMessage[2], splitMessage[3]);
                    return "0|OK|Successfully added match to tournament.";
                }
                catch(IllegalArgumentException iae){
                    return "1|ERR|" + iae;
                }
            case "A":
                t.addRefereeToMatch(LocalDateTime.parse(splitMessage[1]), splitMessage[2]);
                break;
            case "Z":
                try {
                    t.checkRefereeForMatch(LocalDateTime.parse(splitMessage[1]), splitMessage[2]);
                }
                catch(IllegalArgumentException iae){
                    return "1|ERR|" + iae;
                }
                break;
            case "S":
                t.setMatchScore(LocalDateTime.parse(splitMessage[1]), Integer.parseInt(splitMessage[2]), Integer.parseInt(splitMessage[3]));
                break;
            case "L":
                t.getUpcomingMatches();
                break;
            case "G":
                t.getMatchesOn(LocalDateTime.parse(splitMessage[1]));
                break;
            case "F":
                t.getMatchesFor(splitMessage[1]);
                break;
            case "U":
                t.getMatchLineUps(LocalDateTime.parse(splitMessage[1]));
                break;
            case "H":
                t.getListCountries();
                break;
            case "W":
                t.getTeams();
                break;
            case "Y":
                try {
                    t.addPlayerToMatch(LocalDateTime.parse(splitMessage[1]), splitMessage[2], splitMessage[3]);
                    return "0|OK|Player added to line up.";
                }
                catch(Exception e){
                    return "1|ERR|" + e;
                }
            case "T":
                this.keepRunningClient = false;
                break;
        }

        return null;

    }



    protected void forceShutdown(){
        this.keepRunningClient = false;
    }

}
