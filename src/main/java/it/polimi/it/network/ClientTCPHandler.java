package it.polimi.it.network;

import jdk.internal.org.jline.utils.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCPHandler implements Runnable{
    private Socket socket;

    public ClientTCPHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        PrintWriter out;
        try {
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String userInput;
        while (userInput = in.readLine())///slide 20
    }
}
