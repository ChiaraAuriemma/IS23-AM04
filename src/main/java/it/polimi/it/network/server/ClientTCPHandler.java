package it.polimi.it.network.server;


import it.polimi.it.Exceptions.*;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.RemoteInterface;
import it.polimi.it.network.message.ErrorMessage;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.others.PingMessage;
import it.polimi.it.network.message.others.ThisNotTheDay;
import it.polimi.it.network.message.request.*;
import it.polimi.it.network.message.responses.*;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClientTCPHandler implements Runnable,Serializable, RemoteInterface {
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
    private boolean isConnected;
    private boolean pong = true;


    /**
     * Constructor method
     * @param lobby is the instance of the lobby
     * @param serverTCP is the TCP server's instance.
     */
    public ClientTCPHandler(Lobby lobby, ServerTCP serverTCP){
        this.socket = serverTCP.getClientSocket();
        this.lobby = lobby;
        this.serverTCP = serverTCP;

        try{
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();

        }
        try{
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this::disconnectionTimer).start();
    }


    /**
     * Run method for the class:
     * the method keeps catching messages from the client. According to the messages' types,
     * the method performs different actions.
     */
    @Override
    public void run() {
        Message request;
        MessageType messType;
        Message response;

        this.isConnected = true;
        while(isConnected) {
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
                            if(user.getInGame()){
                                user.getGame().getVirtualView().setUser(loginRequest.getNickname(),this);
                                user.getGame().getVirtualView().removeDisconnection(user.getNickname());
                                this.gameController = lobby.getGameController(user.getGame().getGameid());
                                this.gameController.resetGame(user);
                            }
                            serverTCP.setUserTCP(user,socket);
                            LoginResponse loginResponse = new LoginResponse(user.getNickname());
                            response = new Message(MessageType.CREATEPLAYERRESPONSE, loginResponse);
                        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                        } catch (InvalidIDException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    send(response);
                    break;

                case CREATEGAME:
                    CreateGameRequest createGameRequest = (CreateGameRequest) request.getPayload();
                    synchronized (lobby){
                        try {
                            this.gameController = lobby.createGame(createGameRequest.getUsername(), createGameRequest.getPlayerNumber());
                            this.gameController.getGame().getVirtualView().setUser(createGameRequest.getUsername(), this);
                            CreateGameResponse createGameResponse = new CreateGameResponse(gameController.getGameID());
                            response = new Message(MessageType.CREATEGAMERESPONSE, createGameResponse);
                        } catch (WrongPlayerException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                        }
                    }
                    send(response);
                    break;

                case JOINGAME:
                    JoinGameRequest joinGameRequest = (JoinGameRequest) request.getPayload();
                    synchronized (lobby){
                        try {
                            lobby.getGameController(joinGameRequest.getID()).getGame().getVirtualView().setUser(joinGameRequest.getUsername(), this);
                            this.gameController = lobby.joinGame(joinGameRequest.getUsername(), joinGameRequest.getID());
                            JoinGameResponse joinGameResponse = new JoinGameResponse(gameController.getGameID());
                            response = new Message(MessageType.JOINGAMERESPONSE, joinGameResponse);
                        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | RemoteException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    send(response);
                    break;

                case TILESNUMMESSAGE:
                    TilesNumRequest tilesNumRequest = (TilesNumRequest) request.getPayload();
                    synchronized (gameController){
                        try {
                            this.gameController.getFromViewNTiles(this.user.getNickname(),tilesNumRequest.getNumTiles());
                        } catch (WrongPlayerException | RemoteException | IllegalValueException | InvalidIDException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                            send(response);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case SELECTEDTILES:
                    SelectedTilesRequest selectedTilesRequest = (SelectedTilesRequest) request.getPayload();
                    synchronized (gameController){
                        try {
                            this.gameController.getTilesListFromView(this.user.getNickname(), selectedTilesRequest.getChoosenTiles());
                        } catch (WrongTileException | WrongPlayerException | WrongListException | RemoteException | IllegalValueException | InvalidIDException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                            send(response);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case CHOOSECOLUMN:
                    ChooseColumnRequest chooseColumnRequest = (ChooseColumnRequest) request.getPayload();
                    synchronized (gameController){
                        try {
                            this.gameController.getColumnFromView(this.user.getNickname(), chooseColumnRequest.getColumnNumber());
                        } catch (InvalidIDException | IllegalValueException | RemoteException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                            send(response);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case CHAT:
                    SendChatMessage sendChatMessage = (SendChatMessage) request.getPayload();
                    synchronized (gameController){
                        try {
                            this.gameController.pushChatMessage(sendChatMessage.getChatMessage());
                        } catch (RemoteException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                            send(response);
                        }
                    }
                    break;

                case PRIVATECHAT:
                    SendPrivateChatMessage sendPrivateChatMessage = (SendPrivateChatMessage) request.getPayload();
                    synchronized (gameController){
                        try {
                            this.gameController.pushChatPrivateMessage(sendPrivateChatMessage.getSender(),sendPrivateChatMessage.getChatMessage(), sendPrivateChatMessage.getReceiver());
                        } catch (RemoteException | IllegalValueException e) {
                            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
                            response = new Message(MessageType.ERROR, errorMessage);
                            send(response);
                        }
                    }
                    break;

                case PONG:
                    System.out.println("Received pong from " );
                    pong = true;
                    break;
            }
        }

        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("non sono riuscito a chiudere la connessione con ");
        }
    }


    /**
     * Disconnection Timer and Timer's Task.
     * Every 15 seconds a ping message is sent to the client
     * in order to check if the user is still online.
     */
    private void disconnectionTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(pong){
                    pong = false;
                    System.out.println(" is still connected, SADLY");
                    ping();
                }else{
                    if(gameController != null && user.getInGame()){
                        if(gameController.getCurrentPlayer() == gameController.getPlayerNumber(user)){
                            try {
                                gameController.turnDealer();
                            } catch (InvalidIDException | IllegalValueException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        lobby.disconnect_user(user.getNickname());
                        serverTCP.removeUserTCP(user.getNickname());
                        System.out.println(" disconnected");
                        timer.cancel();
                    }
                }
            }
        };
        timer.schedule(timerTask, 1000, 15000);
   }


    /**
     * Method that sends already formatted TCP messages to the client
     * @param message is the message that has to be sent.
     */
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


    /********************************************************************************************
     *                                                                                          *
     * Methods that, given certain parameters, create new messages of a different type each.    *
     * Later, the formatted message is sent calling the 'send' method.                          *
     *                                                                                          *
     ********************************************************************************************/


    @Override
    public void setStartOrder(ArrayList<String> order) throws RemoteException{
        StartOrderMessage startOrderMessage = new StartOrderMessage(order);
        Message message = new Message(MessageType.STARTORDERPLAYER, startOrderMessage);
        send(message);
    }


    @Override
    public void setNewBoard(Tile[][] matrix) throws RemoteException{
        Tile[][] m = new Tile[9][9];
        for(int k = 0; k < 9; k++){
            for(int j = 0; j < 9; j++){
                m[k][j] = new Tile(k,j, PossibleColors.valueOf(matrix[k][j].getColor()));
            }
        }
        BoardUpdateMessage boardUpdateMessage = new BoardUpdateMessage(m);
        Message message = new Message(MessageType.BOARDUPDATE, boardUpdateMessage);
        send(message);
    }


    @Override
    public void setNewCommon(int id1, int id2, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException{
        DrawnCommonCardsMessage drawnCommonCardsMessage = new DrawnCommonCardsMessage(id1,id2,commonToken1,commonToken2);
        Message message = new Message(MessageType.DRAWNCOMMONCARDS, drawnCommonCardsMessage);
        send(message);
    }


    @Override
    public void setNewPersonal(PersonalGoalCard card) throws RemoteException{
        PersonalGoalCard personalCard = card;
        DrawnPersonalCardMessage drawnPersonalCardMessage = new DrawnPersonalCardMessage(personalCard);
        Message message = new Message(MessageType.DRAWNPERSONALCARD,drawnPersonalCardMessage);
        send(message);
    }


    @Override
    public void notifyTurnStart(int maxValueofTiles) throws RemoteException{
        StartTurnMessage startTurnMessage = new StartTurnMessage(maxValueofTiles);
        Message message = new Message(MessageType.STARTTURN,startTurnMessage);
        send(message);
    }


    @Override
    public void setStageToNoTurn() throws RemoteException{
        NoTurnSetter noTurnSetter = new NoTurnSetter(true);
        Message message = new Message(MessageType.NOTURNSETTER,noTurnSetter);
        send(message);
    }


    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException{
        TakeableTilesResponse takeableTilesResponse = new TakeableTilesResponse(choosableTilesList, num);
        Message response = new Message(MessageType.TAKEABLETILES , takeableTilesResponse);
        send(response);
    }


    @Override
    public void askColumn(boolean[] choosableColumns) throws RemoteException{
        PossibleColumnsResponse possibleColumnsResponse = new PossibleColumnsResponse(choosableColumns);
        Message response = new Message(MessageType.POSSIBLECOLUMNS , possibleColumnsResponse);
        send(response);
    }


    @Override
    public void setNewShelfie(String username, Tile[][] shelfie) throws RemoteException{
        Tile[][] s = new Tile[6][5];
        for(int k = 0; k < 6; k++){
            for(int j = 0; j < 5; j++){
                s[k][j] = new Tile(k,j, PossibleColors.valueOf(shelfie[k][j].getColor()));
            }
        }
        ShelfieUpdateMessage shelfieUpdateMessage = new ShelfieUpdateMessage(username, s);
        Message message = new Message(MessageType.SHELFIEUPDATE, shelfieUpdateMessage);
        send(message);
    }


    @Override
    public void setNewPoints(String username, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException{
        PointsUpdateMessage pointsUpdateMessage = new PointsUpdateMessage(username,points,commonToken1,commonToken2);
        Message message = new Message(MessageType.POINTSUPDATE, pointsUpdateMessage);
        send(message);
    }


    @Override
    public void setEndToken(String username) throws RemoteException{
        EndTokenTakenMessage endTokenTakenMessage = new EndTokenTakenMessage(username);
        Message message = new Message(MessageType.ENDTOKEN, endTokenTakenMessage);
        send(message);
    }


    @Override
    public void setFinalPoints(List<String> usernames, ArrayList<Integer> points) throws RemoteException{
        FinalPointsMessage finalPointsMessage = new FinalPointsMessage(usernames,points);
        Message message = new Message(MessageType.FINALPOINTS, finalPointsMessage);
        send(message);
    }


    @Override
    public void updateView() throws RemoteException{
        ViewUpdate viewUpdate = new ViewUpdate();
        Message message = new Message(MessageType.UPDATEVIEW,viewUpdate);
        send(message);
    }


    @Override
    public void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) throws RemoteException{
        ThisNotTheDay recover = new ThisNotTheDay(gameID, matrix, shelfies, id1, id2 , personalGoalCard, points, playerList);
        Message message = new Message(MessageType.THISNOTTHEDAY, recover);
        send(message);
    }


    @Override
    public void boardRefill() throws RemoteException{
        BoardRefill boardRefill = new BoardRefill();
        Message message = new Message(MessageType.BOARDREFILL, boardRefill);
        send(message);
    }


    @Override
    public void printError(String s)  throws RemoteException{
        ErrorMessage errorMessage = new ErrorMessage(s);
        Message message = new Message(MessageType.ERROR, errorMessage);
        send(message);
    }


    @Override
    public void updateChat(List<String> currentChat) throws RemoteException{
        ChatUpdate chatUpdate = new ChatUpdate(currentChat);
        Message messageChat = new Message(MessageType.CHATUPDATE, chatUpdate);
        send(messageChat);
    }


    @Override
    public void ping(){
        PingMessage pingMessage = new PingMessage(" ");
        ping = new Message(MessageType.PING, pingMessage);
        send(ping);
    }
}
