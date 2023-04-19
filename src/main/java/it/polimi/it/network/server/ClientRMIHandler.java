package it.polimi.it.network.server;

import it.polimi.it.controller.Lobby;
import jdk.internal.org.jline.utils.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRMIHandler implements Runnable {

    private Socket socket;
    private Lobby lobby;


    public ClientRMIHandler(Socket socket, Lobby lobby){
        this.socket = socket;
        this.lobby=lobby;
    }

    @Override
    public void run() {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream());  //ottiene stream di output per il client
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //ottiene stream di input dal client
            String userInput;
            while ((userInput = in.readLine()) != null) {
                ///slide 20
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
