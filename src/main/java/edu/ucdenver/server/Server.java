package edu.ucdenver.server;

import edu.ucdenver.tournament.Tournament;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private final int port;
    private final int backlog;
    private static final int connectionCounter = 0;
    private Boolean keepServerRunning;
    private ServerSocket socketServer;
    private ArrayList<ClientWorker> clientWorkers;
    private Tournament tournament;

    public Server(int port, int backlog){
        this.port = port;
        this.backlog = backlog;
        this.clientWorkers = new ArrayList<>();
        this.keepServerRunning = true;
        this.tournament = new Tournament("Tourney", LocalDate.now(), LocalDate.now());


        try{
            socketServer = new ServerSocket(this.port, this.backlog);
        }catch(IOException ioe){
            System.err.println(ioe);
        }

    }

    private void displayMessage(String message){
        System.out.println("[SRV] " + message);
    }

    private Socket waitForConnection() throws IOException{

        Socket connection;
        try {
            connection = socketServer.accept();
        }
        catch (IOException ioe){
            // close the server if there is an error accepting a client
            socketServer.close();
            throw new IOException();
        }

        displayMessage("Connected to client.");
        return connection;

    }

    @Override
    public void run(){

        ExecutorService executorService = Executors.newCachedThreadPool();
        try{
            while(this.keepServerRunning) {

                // For each client connection add a new ClientWorker instance
                ClientWorker clientWorker = new ClientWorker(this, waitForConnection());
                clientWorkers.add(clientWorker);

                // for each client start a new thread
                executorService.execute(clientWorker);
            }

            for(ClientWorker cw : clientWorkers){
                cw.forceShutdown();
                clientWorkers.remove(cw);
            }
            System.out.println(clientWorkers.size());
            this.socketServer.close();

        } catch(IOException ioe){
            for(ClientWorker cw : clientWorkers){
                // close every client thread
                cw.forceShutdown();
                clientWorkers.remove(cw);
            }
            System.out.println(clientWorkers.size());

            // give time for threads to finish their work
            executorService.shutdown();
            System.err.println(ioe);
            try{
                this.socketServer.close();
            }
            catch(IOException ioee){
                System.err.println(ioee);
            }

        }
    }

    public void shutdown(){
        this.keepServerRunning = false;
        try {
            this.socketServer.close();
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }
    }

    public void removeCW(ClientWorker cw){
        cw.forceShutdown();
        clientWorkers.remove(cw);
    }


    public Tournament getTournament(){
        return tournament;
    }

    public void setTournament(Tournament t){
        this.tournament =  t;
    }

    public static void main(String[] args){
        Server server = new Server(10000, 10);
        server.run();
    }

}
