package it.polimi.it.network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.User;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Objects;

public class Server implements Serializable {
    private static final long serialVersionUID = 3768243144909833291L;
    private static Lobby lobby;
    private HashMap<User,String > typeOfConnection = new HashMap<>();


    /**
     * Starts the Server, launches both RMI and TCP communication protocols.
     * @param args are the input parameters
     * @throws FileNotFoundException ,
     * @throws RemoteException ,
     * @throws AlreadyBoundException .
     */
    public static void main(String[] args) throws FileNotFoundException, RemoteException, AlreadyBoundException {
        int portTCP, portRMI;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("ServerConfig.json"))));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);

        if(args.length == 3){
            portTCP = Integer.parseInt(args[1]);
            portRMI = Integer.parseInt(args[2]);
        }else{
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


    /**
     * Getter method
     * @return the instance of the Lobby.
     */
    public Lobby getLobby(){
        return lobby;
    }
}