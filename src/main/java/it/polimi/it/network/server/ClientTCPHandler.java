package it.polimi.it.network.server;


import it.polimi.it.Exceptions.*;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.User;
import it.polimi.it.network.message.ErrorMessage;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.others.PingMessage;
import it.polimi.it.network.message.request.*;
import it.polimi.it.network.message.responses.CreateGameResponse;
import it.polimi.it.network.message.responses.JoinGameResponse;
import it.polimi.it.network.message.responses.LoginResponse;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Timer;

public class ClientTCPHandler implements Runnable,Serializable{
    private static final long serialVersionUID = -3839996564824280040L;
    private Socket socket;
    private ServerTCP serverTCP;
    private GameController gameController;
    private Lobby lobby;
    private User user;

    private ObjectOutputStream out;
    private ObjectInputStream in;


    private Timer timer;
    private Message ping;


    public ClientTCPHandler(Lobby lobby, ServerTCP serverTCP){
        this.socket = serverTCP.getClientSocket();
        this.lobby = lobby;
        this.serverTCP = serverTCP;


        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            //out.flush();
        } catch (IOException e) {
            e.printStackTrace();

        }

        try{

            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void run() {
        Message request;
        MessageType messType;

        Message response;


        while(true) {//CAMBIAMO : se è ancora connesso rimani nel while

            //disconnectionTimer();
            try {
                request = (Message) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            messType = request.getType();

            switch(messType){
                case CREATEPLAYER:
                    LoginRequest loginRequest = (LoginRequest) request.getPayload();
                    synchronized (lobby){
                        try {
                            user = lobby.createUser(loginRequest.getNickname());
                            serverTCP.setUserTCP(user,socket);
                            LoginResponse loginResponse = new LoginResponse(user);
                            response = new Message(MessageType.CREATEPLAYERRESPONSE, loginResponse);

                        } catch (ExistingNicknameException | EmptyNicknameException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);

                        } catch (RemoteException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                        }
                    }

                    send(response);

                case CREATEGAME:

                    CreateGameRequest createGameRequest = (CreateGameRequest) request.getPayload();
                    synchronized (lobby){
                        try {
                            this.gameController = lobby.createGame(createGameRequest.getUser(), createGameRequest.getPlayerNumber(),createGameRequest.getClient());
                            CreateGameResponse createGameResponse = new CreateGameResponse(gameController.getGameID());
                            response = new Message(MessageType.CREATEGAMERESPONSE, createGameResponse);

                        } catch (WrongPlayerException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);

                        }
                    }

                    send(response);

                case JOINGAME:
                    JoinGameRequest joinGameRequest = (JoinGameRequest) request.getPayload();
                    //ottengo il riferimento alla view(dal Gamecontroller) e gli passo lo user come tcp

                    synchronized (lobby){
                        try {
                            this.gameController = lobby.joinGame(joinGameRequest.getUser(), joinGameRequest.getID(), joinGameRequest.getClient());
                            JoinGameResponse joinGameResponse = new JoinGameResponse(gameController.getGameID());
                            response = new Message(MessageType.JOINGAMERESPONSE, joinGameResponse);
                        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | RemoteException e) {

                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);


                        }
                        send(response);
                    }


                case TILESNUMMESSAGE:
                    TilesNumRequest tilesNumRequest = (TilesNumRequest) request.getPayload();

                    synchronized (gameController){
                        try {
                            this.gameController.getFromViewNTiles(this.user,tilesNumRequest.getNumTiles());
                        } catch (WrongPlayerException | WrongListException | RemoteException | IllegalValueException e) {

                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                            send(response);
                        }
                    }


                case SELECTEDTILES:
                    SelectedTilesRequest selectedTilesRequest = (SelectedTilesRequest) request.getPayload();

                    synchronized (gameController){
                        try {
                            this.gameController.getTilesListFromView(this.user, selectedTilesRequest.getChoosenTiles());
                        } catch (WrongPlayerException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                            send(response);
                        }
                    }


                case CHOOSECOLUMN:
                    ChooseColumnRequest chooseColumnRequest = (ChooseColumnRequest) request.getPayload();

                    synchronized (gameController){
                        try {
                            this.gameController.getColumnFromView(this.user, chooseColumnRequest.getColumnNumber());
                        } catch (InvalidIDException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                            send(response);
                        } catch (IllegalValueException | RemoteException e) {
                            System.out.println("ERRORE");
                        }
                    }


                case PONG://risposta del ping del timer


                default: //messaggio non valido
                    System.out.println("User sent an illegal type of message");

            }
            timer.cancel();
        }

    }
/*
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
    //}




    public void send(Message message){

        synchronized (out){
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
