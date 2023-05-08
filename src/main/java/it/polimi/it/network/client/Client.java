package it.polimi.it.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException, NotBoundException {

        Scanner stdin = new Scanner(System.in);
        String inputLine = " ";

        while(!inputLine.equalsIgnoreCase("RMI") && !inputLine.equalsIgnoreCase("TCP")){
            System.out.println("Choose a connection type");
            inputLine = stdin.nextLine();
        }

        ClientInputReader cliR = new ClientInputReader();
        cliR.setConnectionType(inputLine.toUpperCase());
        Thread thread = new Thread(cliR);
        thread.start();
    }
}
