package it.polimi.it.network;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.controller.Lobby;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Server {
    private int port;
    private static Lobby lobby;

    //private ServerTCP serverTCP;
    public static void main(String[] args) throws FileNotFoundException {
        int portNumber;
        ServerTCP serverTCP;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader( new FileReader("src/main/java/it/polimi/it/network/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);

        lobby = new Lobby();

        if(args.length > 0){
            //fare check sulla porta che viene inserita
            portNumber = Integer.parseInt(args[1]);

        }else{
            portNumber = jsonObject.get("port").getAsInt();
        }

        serverTCP = new ServerTCP(portNumber);///sistemooooooooooooooooooooooooooooooooo
    }

    private static void createServerSocket(int portNumber){
        this.port = portNumber;
        serverTCP = new ServerTCP(portNumber);
    }
}
