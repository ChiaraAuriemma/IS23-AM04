package it.polimi.it.network.client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client implements Serializable {

    private static final long serialVersionUID = 749383786771428581L;

    public static void main(String[] args) throws IOException, NotBoundException {

        Scanner stdin = new Scanner(System.in);
        String inputLine = " ";

        while (!inputLine.equalsIgnoreCase("RMI") && !inputLine.equalsIgnoreCase("TCP")) {
            System.out.println("Choose a connection type");
            inputLine = stdin.nextLine();
        }

        ClientInputReader cliR = new ClientInputReader();
        cliR.setConnectionType(inputLine.toUpperCase());
        Thread thread = new Thread(cliR);
        thread.start();
    }
}
