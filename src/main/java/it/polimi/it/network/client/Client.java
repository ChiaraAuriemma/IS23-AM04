package it.polimi.it.network.client;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.controller.GameController;
import it.polimi.it.view.GUI.GUIApplication;
import it.polimi.it.view.GUI.GUILauncher;
import it.polimi.it.view.GUI.GameStartController;
import it.polimi.it.view.GUI.GuiMain;
import it.polimi.it.view.View;
import it.polimi.it.view.ViewInterface;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client implements Serializable {

    private static final long serialVersionUID = 749383786771428581L;


/*
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

 */

    public static void main(String[] args) throws IOException, NotBoundException {

        ClientInterface client;

        if (args.length != 2) {
            System.out.println("Invalid number of parameter: write RMI or TCP and CLI or GUI");
            return;
        }

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("src/main/resources/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
        if (args[0].equalsIgnoreCase("tcp")) {
            ClientTCP clientTCP = new ClientTCP(jsonObject.get("portTCP").getAsInt(), jsonObject.get("ip").getAsString());
            Thread thread = new Thread(clientTCP);
            client = clientTCP;
            thread.start();
        } else{
            ClientRMIApp clientRMI = new ClientRMIApp(jsonObject.get("portRMI").getAsInt(), jsonObject.get("ip").getAsString());
            clientRMI.startClient();
            client = clientRMI;
        }

        if (args[1].equalsIgnoreCase("CLI")) {
            ClientInputReader cliR = new ClientInputReader();
            Thread thread = new Thread(cliR);
            thread.start();
            cliR.setConnectionType(client);
            client.setView("CLI");
            cliR.setView(client.getView());
            client.setBuffer(cliR);
            //fra

        } else if (args[1].equalsIgnoreCase("GUI")){
            GUIApplication guiApplication = new GUIApplication();
            guiApplication.main(args);
            client.setView("GUI");
            //fatti un metodo per la tua classe che chiamerà i metodi della tua view (il client input reader della gui)

        }


    }
}
