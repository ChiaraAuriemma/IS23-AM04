package it.polimi.it.network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Objects;

public class Server implements Serializable {
    private static final long serialVersionUID = 3768243144909833291L;
    private static Lobby lobby;
    private HashMap<User,String > typeOfConnection = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException, RemoteException, AlreadyBoundException {
        int portTCP, portRMI;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("ServerConfig.json"))));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);

        //----> prendiamo il numero della porta da terminale
        if(args.length == 3){
            //fare check sulla porta che viene inserita
            portTCP = Integer.parseInt(args[1]);
            portRMI = Integer.parseInt(args[2]);

        }else{ //----> qui prendiamo il numero della porta da file Json
            portTCP = jsonObject.get("portTCP").getAsInt();
            portRMI = jsonObject.get("portRMI").getAsInt();
        }



        ServerTCP serverTCP = new ServerTCP(portTCP);
        //serverTCP.startServer();
        Thread threadTCP = new Thread(serverTCP);
        threadTCP.start();

        RMIImplementation serverRMI = new RMIImplementation();
        serverRMI.startServer(portRMI);

        lobby = new Lobby();
        lobby.setServerRMI(serverRMI);
        lobby.setServerTCP(serverTCP);
        serverRMI.setLobby(lobby);
        serverTCP.setLobby(lobby);

    }

    public Lobby getLobby(){
        return lobby;
    }

   /* public void gameInitialization(User user){
        String type = typeOfConnection.get(user);
        if(type.equals("RMI")){
           // RMIImplementation
        }
    }


    */

}