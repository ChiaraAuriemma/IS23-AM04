package it.polimi.it.network.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.it.controller.Exceptions.ExistingNicknameException;
import it.polimi.it.controller.Exceptions.InvalidIDException;
import it.polimi.it.controller.Exceptions.WrongPlayerException;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.User;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.errors.ExistingNicknameError;
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

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Lobby lobby;

    public ClientTCPHandler(Socket socket, Lobby lobby, ServerTCP serverTCP){
        this.socket = socket;
        this.lobby = lobby;
        this.serverTCP = serverTCP;
    }

    @Override
    public void run() {

       try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message request;
        MessageType messType;

        Message response;






        while(true) {//CAMBIAMO : se è ancora connesso rimani nel while

            try {
                request = (Message) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            messType = request.getType();

            switch(messType){
                case CREATEPLAYER:
                    //LoginRequest loginRequest = (LoginRequest) request;
                    LoginRequest loginRequest = (LoginRequest) request.getPayload();

                    try {
                        user = lobby.createUser(loginRequest.getNickname());
                    } catch (ExistingNicknameException e) {
                        ExistingNicknameError existingNicknameError = new ExistingNicknameError(e.getMessage());
                        try {
                            out.writeObject(existingNicknameError);
                            out.flush();
                        } catch (IOException ex) {
                            System.out.println(e.getMessage());
                        }
                    }
                    serverTCP.setUserTCP(user,socket);
                    LoginResponse loginResponse = new LoginResponse( user);

                    response = new Message(MessageType.CREATEPLAYERRESPONSE, loginResponse);

                    try {
                        out.writeObject(response);
                        out.flush();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

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







    }
}
