package it.polimi.it.network;

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

    public ClientTCPHandler(Socket socket){
        this.socket = socket;
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


            sendmessage(type, string)
            switch (type){
        }

    }
}
