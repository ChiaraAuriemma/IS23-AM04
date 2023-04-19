package it.polimi.it.network.server;

import it.polimi.it.controller.Lobby;
import it.polimi.it.network.Message;
import it.polimi.it.network.MessageType;
import jdk.internal.org.jline.utils.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCPHandler implements Runnable{
    private Socket socket;

    private PrintWriter out;
    private Scanner in;

    private Lobby lobby;

    public ClientTCPHandler(Socket socket, Lobby lobby){
        this.socket = socket;
        this.lobby = lobby;
    }

    @Override
    public void run() {

        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String message;

        while (true){
            message = in.nextLine();

            Message m = new Message(MessageType.valueOf(message), "input");

            switch (m.getType()){
                case CREATEPLAYER: lobby.createUser("Giovanni");
                    //come passo il parametro ricevuto dal messaggio?
            }
        }

    }
}
