package it.polimi.it.network.client;


import it.polimi.it.controller.GameController;
import it.polimi.it.view.GUI.GUIApplication;
import it.polimi.it.view.GUI.GUILauncher;
import it.polimi.it.view.GUI.GameStartController;
import it.polimi.it.view.GUI.GuiMain;
import it.polimi.it.view.View;
import it.polimi.it.view.ViewInterface;

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

        while (!inputLine.equalsIgnoreCase("CLI") && !inputLine.equalsIgnoreCase("GUI")) {
            System.out.println("Do you want to play using GUI or CLI?");
            inputLine = stdin.nextLine();
        }

        if(inputLine.equalsIgnoreCase("GUI")){
            GUIApplication guiApplication = new GUIApplication();
            guiApplication.main(args);
            ViewInterface view = new GuiMain();
        }else{
            while (!inputLine.equalsIgnoreCase("RMI") && !inputLine.equalsIgnoreCase("TCP")) {
                System.out.println("Choose a connection type");
                inputLine = stdin.nextLine();
            }
            ViewInterface view = new View();
            ClientInputReader cliR = new ClientInputReader();
            cliR.setConnectionType(inputLine.toUpperCase(), view);
            Thread thread = new Thread(cliR);
            thread.start();
        }


    }
}
