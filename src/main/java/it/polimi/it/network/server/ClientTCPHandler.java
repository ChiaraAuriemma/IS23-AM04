package it.polimi.it.network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.it.controller.Exceptions.InvalidIDException;
import it.polimi.it.controller.Exceptions.WrongPlayerException;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.User;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.request.*;
import it.polimi.it.network.message.responses.CreateGameResponse;
import it.polimi.it.network.message.responses.LoginResponse;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCPHandler implements Runnable{
    private Socket socket;
    private ServerTCP serverTCP;
    private GameController gameController;

    private User user;

    private PrintWriter out;
    private Scanner in;

    private Lobby lobby;

    public ClientTCPHandler(Socket socket, Lobby lobby, ServerTCP serverTCP){
        this.socket = socket;
        this.lobby = lobby;
        this.serverTCP = serverTCP;
    }

    @Override
    public void run() {

       /* try {
            in = new Scanner(socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Message request;
        MessageType messType;
        Gson gson = new Gson();

        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            try {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                while (jsonReader.hasNext()) {

                    request = gson.fromJson(jsonReader, Message.class);
                    messType = request.getType();

                    switch(messType){
                        case CREATEPLAYER:
                            LoginRequest loginRequest = (LoginRequest) request;
                            User newUser = lobby.createUser(loginRequest.getNickname());//per le eccezioni creo dei messaggi apposta
                            serverTCP.setUserTCP(newUser,socket);
                            this.user = newUser;

                            LoginResponse loginResponse = new LoginResponse(loginRequest.getType(), newUser);
                            String loginResp = gson.toJson(loginResponse);
                            jsonWriter.jsonValue(loginResp);
                            jsonWriter.flush();

                        case CREATEGAME:
                            CreateGameRequest createGameRequest = (CreateGameRequest) request;
                            this.gameController = lobby.createGame(createGameRequest.getUser(), createGameRequest.getPlayerNumber());
                            //creo la virtualview e gli passo lo user come tcp
                            //risolvo l'errore alla riga sotto (tolgo enum ???)
                            CreateGameResponse createGameResponse = new CreateGameResponse(createGameRequest.getType(),gameController.getGameID());
                            String createResp = gson.toJson(createGameResponse);
                            jsonWriter.jsonValue(createResp);
                            jsonWriter.flush();

                        case JOINGAME:
                            JoinGameRequest joinGameRequest = (JoinGameRequest) request;
                            this.gameController = lobby.joinGame(joinGameRequest.getUser(),joinGameRequest.getId());
                            //ottengo il riferimento alla view(dal Gamecontroller) e gli passo lo user come tcp

                        case TILESNUMMESSAGE:
                            TilesNumRequest tilesNumRequest = (TilesNumRequest) request;
                            this.gameController.getFromViewNTiles(this.user,tilesNumRequest.getNumTiles());

                        case SELECTEDTILES:
                            SelectedTilesRequest selectedTilesRequest = (SelectedTilesRequest) request;
                            this.gameController.getTilesListFromView(this.user, selectedTilesRequest.getChoosenTiles());

                        case CHOOSECOLUMN:
                            ChooseColumnRequest chooseColumnRequest = (ChooseColumnRequest) request;
                            this.gameController.getColumnFromView(this.user, chooseColumnRequest.getColumnNumber(), chooseColumnRequest.getOrderedTiles());

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }






    }
}
