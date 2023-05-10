package it.polimi.it.network.client;

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
        Thread thread = new Thread(cliR);
        thread.start();
        cliR.setConnectionType(inputLine.toUpperCase());
    }
}
