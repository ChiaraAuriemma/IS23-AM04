package it.polimi.it.network.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws FileNotFoundException, RemoteException, NotBoundException {

        System.out.println("Choose a connection type");

        Scanner stdin = new Scanner(System.in);
        String inputLine = stdin.nextLine();
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader( new FileReader("src/main/java/it/polimi/it/network/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);

        ClientInputReader cliR = new ClientInputReader();
        Thread thread = new Thread(cliR);
        thread.start();

        if(inputLine.equals("TCP")){
            ClientTCP clientTCP = new ClientTCP(jsonObject.get("portTCP").getAsInt(),jsonObject.get("ip").getAsString());
            clientTCP.startClient();
        }else if(inputLine.equals("RMI") ){
            ClientRMIApp clientRMIApp = new ClientRMIApp(jsonObject.get("portRMI").getAsInt(),jsonObject.get("ip").getAsString());
            clientRMIApp.startClient();
        }
    }
}
