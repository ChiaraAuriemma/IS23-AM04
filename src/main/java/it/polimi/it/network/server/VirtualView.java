package it.polimi.it.network.server;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientRMI;
import it.polimi.it.network.client.ClientRMIApp;
import it.polimi.it.network.message.responses.LoginResponse;
import it.polimi.it.network.message.responses.TakeableTilesResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import static it.polimi.it.network.message.MessageType.TAKEABLETILES;

public class VirtualView {
    private Game game;

    private HashMap<User,String > typeOfConnection;

    private ServerTCP serverTCP;
    private RMIImplementation serverRMI;

    public VirtualView(Game game, ServerTCP serverTCP ,RMIImplementation serverRMI){
        this.game=game;
        typeOfConnection = new HashMap<>();
        this.serverTCP = serverTCP;
        this.serverRMI = serverRMI;
    }

    public void setUserTCP(User user,Socket socket){
        typeOfConnection.put(user,"TCP");
    }

    public void setUserRMI(User user,ClientRMI cr){
        typeOfConnection.put(user,"RMI");
    }

    public void takeableTiles(User user, List<List<Tile>> choosableTilesList){
        if(typeOfConnection.get(user).equals("TCP")){

            Socket socket = serverTCP.getUserTCP(user);
            Gson gson = new Gson();
            try{
                OutputStream outputStream = socket.getOutputStream();
                JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                TakeableTilesResponse takeableTilesResponse = new TakeableTilesResponse(TAKEABLETILES, choosableTilesList);
                String takeResp = gson.toJson(takeableTilesResponse);
                jsonWriter.jsonValue(takeResp);
                jsonWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(typeOfConnection.get(user).equals("RMI")){

            ClientRMI clientRMI = serverRMI.getUserRMI(user);
            clientRMI.takeableTiles(List<List<Tile>> choosableTilesList);
        }
    }





}
