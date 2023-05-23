package it.polimi.it.network.client;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.message.ErrorMessage;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.Payload;
import it.polimi.it.network.message.others.PingMessage;
import it.polimi.it.network.message.others.PongMessage;
import it.polimi.it.network.message.others.ThisNotTheDay;
import it.polimi.it.network.message.request.*;
import it.polimi.it.network.message.responses.*;
import it.polimi.it.view.GUI.GUIApplication;
import it.polimi.it.view.GUI.GUILauncher;
import it.polimi.it.view.GUI.GuiMain;
import it.polimi.it.view.View;
import it.polimi.it.view.ViewInterface;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClientTCP implements ClientInterface, Serializable, Runnable {

    private static final long serialVersionUID = -1334206444743011550L;
    private int port;
    private String ip;
    private ViewInterface view;
    private GUIApplication guiApplication;


    private String username;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket serverSocket;

    private ClientInputReader buffer;
    //private LinkedList<Message> messages;
    public ClientTCP(int port, String ip){
        this.port = port;
        this.ip = ip;
        //this.messages = new LinkedList<>();

        try{
            serverSocket = new Socket(ip,port);

            try{
                out = new ObjectOutputStream(serverSocket.getOutputStream());
                //out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                in = new ObjectInputStream(serverSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // qui metto un thread che parte su run()
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        }


    }

    public void setView(String viewChoice) {
        if(viewChoice.equalsIgnoreCase("CLI")){
            this.view = new View();
        }else if (viewChoice.equalsIgnoreCase("GUI")){
            this.view = new GuiMain();
        }

        view.askNickname();
    }




    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException {
        view.update();
        System.out.println("Please choose " + num + " tiles from the board...\n");
        view.takeableTiles(choosableTilesList);
    }

    @Override
    public void setStartOrder(ArrayList<String> order) {
        view.setOrderView(order);
    }

    @Override
    public void setNewBoard(Tile[][] matrix) {
        view.setBoardView(matrix);
    }

    @Override
    public void setNewCommon(CommonGoalCard card1, CommonGoalCard card2) {
        view.setCommon1View(card1);
        view.setCommon2View(card2);
    }

    @Override
    public void setNewPersonal(PersonalGoalCard card) {
        view.setPlayersPersonalCardView(card);
    }

    @Override
    public void setNewShelfie(String username, Tile[][] shelfie) {
        view.setPlayersShelfiesView(username, shelfie);
    }

    @Override
    public void setNewPoints(String username, Integer points) {
        view.setPlayersPointsView(username, points);
    }

    @Override
    public void notifyTurnStart(int maxValueofTiles) {
        view.update();
        view.NotifyTurnStart(maxValueofTiles,this.username);
        buffer.setStage(TurnStages.TILESNUM);
    }

    @Override
    public void askColumn(boolean[] choosableColumns) {
        view.update();
        view.askColumn();
        //view.setPossibleColumns(choosableColumns);
    }


    //IMPORTANTE : gestisco anche l'arrivo di messaggi d'errore da parte del server
    public void run()  {
        Message response;
        MessageType messType;
        //new Thread(this::networkReader).start();

        while(true){ //sostituisco con while connected
            try {
                response = (Message) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            messType = response.getType();

            switch (messType){
                case CREATEPLAYERRESPONSE:
                    LoginResponse loginResponse = (LoginResponse) response.getPayload();
                    username = loginResponse.getUsername();
                    //view : faccio passare la view alla schermata di selezione join o create game
                    //view : passo alla view lo user
                    buffer.setStage(TurnStages.CREATEorJOIN);

                    view.joinOrCreate(username);
                    break;
                case CREATEGAMERESPONSE:
                    CreateGameResponse createGameResponse = (CreateGameResponse) response.getPayload();
                    //view : passo alla view il game id
                    buffer.setStage(TurnStages.NOTHING);
                    view.setGameID(createGameResponse.getGameId());
                    break;
                case JOINGAMERESPONSE:
                    JoinGameResponse joinGameResponse = (JoinGameResponse) response.getPayload();

                    if(!buffer.getStage().equals(TurnStages.TILESNUM)){
                        buffer.setStage(TurnStages.NOTHING);
                    }
                    view.setGameID(joinGameResponse.getGameId());
                    break;
                case STARTORDERPLAYER:
                    StartOrderMessage startOrderMessage = (StartOrderMessage) response.getPayload();
                    //view : passo l'ordine dei giocatori in modo che può disporli
                    setStartOrder(startOrderMessage.getOrder());
                    buffer.setStage(TurnStages.NOTURN);
                    break;
                case INITIALMATRIX:
                    InitialMatrixMessage initialMatrixMessage = (InitialMatrixMessage) response.getPayload();
                    //view : passo la matrice in modo da visualizzare la board
                    setNewBoard(initialMatrixMessage.getMatrix());
                    break;
                case DRAWNCOMMONCARDS:
                    DrawnCommonCardsMessage drawnCommonCardsMessage = (DrawnCommonCardsMessage) response.getPayload();
                    //view : passo le common cards e la lista dei token delle common cards
                    setNewCommon(drawnCommonCardsMessage.getCard1() , drawnCommonCardsMessage.getCard2());
                    break;

                case DRAWNPERSONALCARD:
                    DrawnPersonalCardMessage drawnPersonalCardMessage = (DrawnPersonalCardMessage) response.getPayload();
                    //view : passo la personal card del giocatore
                    setNewPersonal(drawnPersonalCardMessage.getCard());
                    break;

                case STARTTURN:
                    StartTurnMessage startTurnMessage = (StartTurnMessage) response.getPayload();
                    notifyTurnStart(startTurnMessage.getMaxValueofTiles());
                    buffer.setStage(TurnStages.TILESNUM);
                    break;

                case TAKEABLETILES:
                    TakeableTilesResponse takeableTilesResponse = (TakeableTilesResponse) response.getPayload();
                    try {
                        takeableTiles(takeableTilesResponse.getChoosableTilesList(), takeableTilesResponse.getChoosableTilesList().get(0).size());
                    } catch (RemoteException e) { //trovo un modo migliore per gestirla
                        throw new RuntimeException(e);
                    }
                    //view : passo choosableTilesList per "illuminare" sulla board le tiles prendibili
                    buffer.setStage(TurnStages.CHOOSETILES);
                    break;

                case POSSIBLECOLUMNS:
                    PossibleColumnsResponse possibleColumnsResponse = (PossibleColumnsResponse) response.getPayload();
                    askColumn(possibleColumnsResponse.getChoosableColumns());
                    //view : passo il booleano con true e false sulle varie colonne della shelfie
                    buffer.setStage(TurnStages.CHOOSECOLUMN);
                    break;
                case SHELFIEUPDATE:
                    ShelfieUpdateMessage shelfieUpdateMessage = (ShelfieUpdateMessage) response.getPayload();
                    setNewShelfie(shelfieUpdateMessage.getUsername(), shelfieUpdateMessage.getShelfie());
                    buffer.setStage(TurnStages.NOTURN);
                    //view : passo lo user,la colonna e la lista ordinata di tiles scelte per aggiornare la shelfie dello user corrispondente
                    break;
                case BOARDUPDATE:
                    BoardUpdateMessage boardUpdateMessage = (BoardUpdateMessage) response.getPayload();
                    view.setBoardView(boardUpdateMessage.getMatrix());
                    //view : passo la nuova matrice in modo da visualizzare la board aggiornata
                    break;
                case POINTSUPDATE:
                    PointsUpdateMessage pointsUpdateMessage = (PointsUpdateMessage) response.getPayload();
                    setNewPoints(pointsUpdateMessage.getUsername(), pointsUpdateMessage.getPoint());
                    //view : passo lo user,il nuovo punteggio e se questo ha preso qualche common token
                    break;
                case ENDTOKEN:
                    EndTokenTakenMessage endTokenTakenMessage = (EndTokenTakenMessage) response.getPayload();
                    setEndToken(endTokenTakenMessage.getUsername());
                    //view : passo lo user che ha preso l'endToken
                    break;
                case FINALPOINTS:
                    FinalPointsMessage finalPointsMessage = (FinalPointsMessage) response.getPayload();
                    setFinalPoints(finalPointsMessage.getUsernames(),finalPointsMessage.getPoints());

                    //view : passo la lista degli utenti e la lista dei loro punteggi
                    break;
                case THISISNOTTHEDAY:
                    ThisNotTheDay recover = (ThisNotTheDay) response.getPayload();
                    recover(recover.getGame(), recover.getGameID());
                    break;

                case UPDATEVIEW: updateView();
                    break;

                case CHATUPDATE:
                    ChatUpdate chatUpdate = (ChatUpdate) response.getPayload();
                    view.updateChat(chatUpdate.getChatUpdate());
                    if(buffer.getStage() == TurnStages.NOTURN){
                        view.update();
                    }
                    break;

                case NOTURNSETTER:
                    buffer.setStage(TurnStages.NOTURN);
                    break;

                case BOARDREFILL:
                    boardRefill();
                    break;

                case PING:
                    System.out.println("ping received");
                    PingMessage pingMessage= (PingMessage) response.getPayload();
                    PongMessage pongMessage = new PongMessage(" ");
                    Message request = new Message(MessageType.PONG, pongMessage);

                    send(request);
                    System.out.println("sent pong ");

                    break;

                case ERROR:
                    ErrorMessage errorMessage = (ErrorMessage) response.getPayload();
                    view.printError(errorMessage.getError());
                    // il messaggio d'errore contiene la stringa error implementare la gestione dei vari errori
                    break;
            }
        }
    }



    //metodi da chiamare per mandare una request
    @Override
    public void login(String nickname) {
        LoginRequest loginRequest = new LoginRequest(nickname);
        Message request = new Message(MessageType.CREATEPLAYER, loginRequest);
        send(request);
    }
    @Override
        public void createGame(int playerNumber){
        CreateGameRequest createGameRequest = new CreateGameRequest(username,playerNumber);
        Message request = new Message(MessageType.CREATEGAME, createGameRequest);
        send(request);
    }

    @Override
    public void joinGame(int gameId){
        JoinGameRequest joinGameRequest = new JoinGameRequest(username,gameId);
        Message request = new Message(MessageType.JOINGAME, joinGameRequest);
        send(request);
    }
    @Override
    public void tilesNumMessage(int numOfTiles){
        TilesNumRequest tilesNumRequest = new TilesNumRequest(numOfTiles);
        Message request = new Message(MessageType.TILESNUMMESSAGE, tilesNumRequest);
        send(request);
    }

    @Override
    public ViewInterface getView(){
        return this.view;
    }

    @Override
    public void selectedTiles(List<Tile> choices){
        SelectedTilesRequest selectedTilesRequest = new SelectedTilesRequest(choices);
        Message request = new Message(MessageType.SELECTEDTILES, selectedTilesRequest);
        send(request);
        buffer.setStage(TurnStages.CHOOSECOLUMN);
    }
    @Override
    public void chooseColumn (int column){
        ChooseColumnRequest chooseColumnRequest = new ChooseColumnRequest(column);
        Message request = new Message(MessageType.CHOOSECOLUMN, chooseColumnRequest);
        send(request);
        buffer.setStage(TurnStages.NOTURN);
        System.out.println("End of your turn\n");
    }


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
    @Override
    public void setEndToken(String username) {
        view.setEndToken(username);
    }

    @Override
    public void setFinalPoints(List<String> usernames, ArrayList<Integer> points) {
        view.setFinalPoints(usernames, points);
    }

    @Override
    public void recover(Game game, int gameID) {
        //view.recover(game, gameID, username);
    }

    @Override
    public void updateView() {
        view.update();
    }

    @Override
    public void sendChatMessage(String chatMessage) {
        SendChatMessage sendChatMessage = new SendChatMessage(chatMessage);
        Message request = new Message(MessageType.CHAT, sendChatMessage);
        send(request);
    }

    @Override
    public void updateChat(List<String> currentChat) throws RemoteException{
        view.updateChat(currentChat);
        if(buffer.getStage() == TurnStages.NOTURN){
            view.update();
        }
    }

    @Override
    public void setStageToNoTurn() throws RemoteException {
        buffer.setStage(TurnStages.NOTURN);
    }

    @Override
    public void boardRefill(){
        view.boardRefill();
    }

    public void setBuffer(ClientInputReader buffer) {
        this.buffer = buffer;
        buffer.setStage(TurnStages.LOGIN);
    }


    /*public void networkReader(){
        while(true){//sostituisco con while connected
            try {
                messages.push((Message) in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }*/
}
