package it.polimi.it.network.server;


import it.polimi.it.controller.Exceptions.ExistingNicknameException;
import it.polimi.it.controller.Exceptions.InvalidIDException;
import it.polimi.it.controller.Exceptions.WrongPlayerException;
import it.polimi.it.controller.Exceptions.WrongTurnException;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.User;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.errors.ExistingNicknameError;
import it.polimi.it.network.message.errors.InvalidIDError;
import it.polimi.it.network.message.errors.WrongPlayerError;
import it.polimi.it.network.message.others.PingMessage;
import it.polimi.it.network.message.request.*;
import it.polimi.it.network.message.responses.CreateGameResponse;
import it.polimi.it.network.message.responses.LoginResponse;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientTCPHandler implements Runnable{
    private Socket socket;
    private ServerTCP serverTCP;
    private GameController gameController;

    private User user;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Lobby lobby;
    private Timer timer;
    private Message ping;

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

            disconnectionTimer();
            try {
                request = (Message) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            messType = request.getType();

            switch(messType){
                case CREATEPLAYER:
                    LoginRequest loginRequest = (LoginRequest) request.getPayload();

                    try {
                        user = lobby.createUser(loginRequest.getNickname());
                        serverTCP.setUserTCP(user,socket);
                        LoginResponse loginResponse = new LoginResponse(user);
                        response = new Message(MessageType.CREATEPLAYERRESPONSE, loginResponse);

                        try {
                            out.writeObject(response);
                            out.flush();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }

                    } catch (ExistingNicknameException e) {// sistemo non va mandato il paylod ma il messaggio d'errore con dentro il payload
                        ExistingNicknameError existingNicknameError = new ExistingNicknameError(e.getMessage());
                        try {
                            out.writeObject(existingNicknameError);
                            out.flush();
                        } catch (IOException ex) {
                            System.out.println(e.getMessage());
                        }
                    }

                case CREATEGAME:

                    CreateGameRequest createGameRequest = (CreateGameRequest) request.getPayload();

                    try {
                        this.gameController = lobby.createGame(createGameRequest.getUser(), createGameRequest.getPlayerNumber());
                        CreateGameResponse createGameResponse = new CreateGameResponse(gameController.getGameID());
                        response = new Message(MessageType.CREATEGAMERESPONSE, createGameResponse);
                        try {
                            out.writeObject(response);
                            out.flush();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }

                    } catch (WrongPlayerException e) {//se il numero di giocatori è sbagliato -> mando messaggio di errore e rifaccio
                        WrongPlayerError wrongPlayerError = new WrongPlayerError(e.getMessage());
                        try{
                            out.writeObject(wrongPlayerError);
                            out.flush();
                        } catch (IOException ex) {
                            System.out.println(e.getMessage());
                        }
                    }

                case JOINGAME:
                    JoinGameRequest joinGameRequest = (JoinGameRequest) request.getPayload();
                    //ottengo il riferimento alla view(dal Gamecontroller) e gli passo lo user come tcp

                    try {
                        this.gameController = lobby.joinGame(joinGameRequest.getUser(), joinGameRequest.getID());

                    } catch (InvalidIDException e) {//se il numero di giocatori è sbagliato -> mando messaggio di errore e rifaccio
                        InvalidIDError invalidIDErrorError = new InvalidIDError(e.getMessage());
                        try{
                            out.writeObject(invalidIDErrorError);
                            out.flush();
                        } catch (IOException ex) {
                            System.out.println(e.getMessage());
                        }

                    } catch (WrongPlayerException e) {
                        WrongPlayerError wrongPlayerError = new WrongPlayerError(e.getMessage());
                        try{
                            out.writeObject(wrongPlayerError);
                            out.flush();
                        } catch (IOException ex) {
                            System.out.println(e.getMessage());
                        }
                    }

                case TILESNUMMESSAGE:
                    TilesNumRequest tilesNumRequest = (TilesNumRequest) request.getPayload();

                    try{
                        this.gameController.getFromViewNTiles(this.user,tilesNumRequest.getNumTiles());
                    } catch (WrongListException e) {
                        throw new RuntimeException(e);//da modificare con l'invio di messaggi
                    } catch (WrongTurnException e) {
                        throw new RuntimeException(e);//da modificare con l'invio di messaggi
                    }

                case SELECTEDTILES:
                    SelectedTilesRequest selectedTilesRequest = (SelectedTilesRequest) request.getPayload();
                    try {
                        this.gameController.getTilesListFromView(this.user, selectedTilesRequest.getChoosenTiles());
                    } catch (WrongPlayerException e) {//deh se non vanno bene devi hiederle di nuovo
                        throw new RuntimeException(e);//da modificare con l'invio di messaggi
                    }

                case CHOOSECOLUMN:
                    ChooseColumnRequest chooseColumnRequest = (ChooseColumnRequest) request.getPayload();
                    try {
                        this.gameController.getColumnFromView(this.user, chooseColumnRequest.getColumnNumber(), chooseColumnRequest.getOrderedTiles());
                    } catch (InvalidIDException e) {//deh se non va bene devi hiederla di nuovo
                        throw new RuntimeException(e);//da modificare con l'invio di messaggi
                    }

                case PONG://risposta del ping del timer


                default: //messaggio non valido
                    System.out.println("User sent an illegal type of message");

            }
            timer.cancel();
        }

    }

    private void disconnectionTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                PingMessage pingMessage = new PingMessage(" ");
                ping = new Message(MessageType.CREATEGAMERESPONSE, pingMessage);
                try {
                    out.writeObject(ping);
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(timerTask, 1000, 20000);
                    /*
                    public void schedule(TimerTask task, long delay, long period)
                        task - task to be scheduled.
                        delay - delay in milliseconds before task is to be executed.        1000->1secondo
                        period - time in milliseconds between successive task executions.   200000->20 secondi
                     */
    }
}
