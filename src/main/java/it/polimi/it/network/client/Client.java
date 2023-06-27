package it.polimi.it.network.client;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.view.GUI.GUIApplication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.util.Objects;


/**
 * Main class for the Client,
 * Instances either ClientTCP or ClientRMIApp
 * Instances either GUI classes or CLI.
 */
public class Client implements Serializable {

    private static final long serialVersionUID = 749383786771428581L;

    public static void main(String[] args) throws IOException, NotBoundException {

        ClientInterface client;

        if (args.length < 2) {
            System.out.println("Invalid number of parameter: write RMI or TCP and CLI or GUI");
            return;
        }

        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("ServerConfig.json"))));
        JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
        String ipString = jsonObject.get("ip").getAsString();

        if(args.length==3){
            if (args[2]!=null){
                ipString=args[2];
            }
        }
        if (args[0].equalsIgnoreCase("tcp")) {
            ClientTCP clientTCP = new ClientTCP(jsonObject.get("portTCP").getAsInt(), ipString);
            Thread thread = new Thread(clientTCP);
            client = clientTCP;
            thread.start();
        } else if(args[0].equalsIgnoreCase("rmi")){
            ClientRMIApp clientRMI = new ClientRMIApp(jsonObject.get("portRMI").getAsInt(), ipString);
            clientRMI.startClient();
            client = clientRMI;
        }else{
            System.out.println("Invalid number of parameter: write RMI or TCP and CLI or GUI");
            return;
        }

        if (args[1].equalsIgnoreCase("CLI")) {
            GameStage gameStage = new GameStage();
            ClientInputReader cliR = new ClientInputReader(gameStage);
            Thread thread = new Thread(cliR);
            thread.start();
            cliR.setConnectionType(client);
            client.setView("CLI");
            cliR.setView(client.getView());
            client.setGameStage(gameStage);

        } else if (args[1].equalsIgnoreCase("GUI")){
            client.setView("GUI");
            GameStage gameStage = new GameStage();
            client.setGameStage(gameStage);
            GUIApplication.setClient(client);
            GUIApplication.main(args);
        }else{
            System.out.println("Invalid number of parameter: write RMI or TCP and CLI or GUI");
            return;
        }
    }
}
