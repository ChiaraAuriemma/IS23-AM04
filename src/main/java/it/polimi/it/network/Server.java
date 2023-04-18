package it.polimi.it.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.controller.Lobby;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port;
    private static Lobby lobby;

    public static void main(String[] args) throws FileNotFoundException {
        int portTCP, portRMI;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader( new FileReader("src/main/java/it/polimi/it/network/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);

        lobby = new Lobby();

        //----> prendiamo il numero della porta da terminale
        if(args.length == 3){
            //fare check sulla porta che viene inserita
            portTCP = Integer.parseInt(args[1]);
            portRMI = Integer.parseInt(args[2]);

        }else{ //----> qui prendiamo il numero della porta da file Json
            portTCP = jsonObject.get("portTCP").getAsInt();
            portRMI = jsonObject.get("portRMI").getAsInt();
        }

        ServerTCP serverTCP = new ServerTCP(portTCP, lobby);
        serverTCP.startServer();

        ServerRMI serverRMI = new ServerRMI(portRMI, lobby);
        serverRMI.startServer();

    }

    public Lobby getLobby(){
        return lobby;
    }


}