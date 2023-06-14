package it.polimi.it.network.client;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.ErrorMessage;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.others.PingMessage;
import it.polimi.it.network.message.others.PongMessage;
import it.polimi.it.network.message.others.ThisNotTheDay;
import it.polimi.it.network.message.request.*;
import it.polimi.it.network.message.responses.*;
import it.polimi.it.view.GUI.GUIApplication;
import it.polimi.it.view.View;
import it.polimi.it.view.ViewInterface;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
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

    private GameStage stage;

    public ClientTCP(int port, String ip){
        this.port = port;
        this.ip = ip;

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
            this.view = new GUIHandler();
        }

        view.askNickname();
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
                    if(!stage.getStage().equals(TurnStages.NOTURN)){
                        stage.setStage(TurnStages.CREATEorJOIN);
                        try {
                            view.joinOrCreate(username);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        view.update();
                    }

                    break;
                case CREATEGAMERESPONSE:
                    CreateGameResponse createGameResponse = (CreateGameResponse) response.getPayload();
                    //view : passo alla view il game id
                    stage.setStage(TurnStages.NOTHING);
                    view.setGameID(createGameResponse.getGameId());
                    break;
                case JOINGAMERESPONSE:
                    JoinGameResponse joinGameResponse = (JoinGameResponse) response.getPayload();

                    if(stage.getStage().equals(TurnStages.CREATEorJOIN)){
                        stage.setStage(TurnStages.NOTHING);
                    }
                    view.setGameID(joinGameResponse.getGameId());
                    break;
                case STARTORDERPLAYER:
                    StartOrderMessage startOrderMessage = (StartOrderMessage) response.getPayload();
                    //view : passo l'ordine dei giocatori in modo che può disporli
                    view.setOrderView(startOrderMessage.getOrder());
                    stage.setStage(TurnStages.NOTURN);
                    break;

                case DRAWNCOMMONCARDS:
                    DrawnCommonCardsMessage drawnCommonCardsMessage = (DrawnCommonCardsMessage) response.getPayload();
                    //view : passo le common cards e la lista dei token delle common cards
                    view.setCommon1View(drawnCommonCardsMessage.getId1());
                    view.setCommon2View(drawnCommonCardsMessage.getId2());
                    break;

                case DRAWNPERSONALCARD:
                    DrawnPersonalCardMessage drawnPersonalCardMessage = (DrawnPersonalCardMessage) response.getPayload();
                    //view : passo la personal card del giocatore
                    view.setPlayersPersonalCardView(drawnPersonalCardMessage.getCard());
                    break;

                case STARTTURN:
                    StartTurnMessage startTurnMessage = (StartTurnMessage) response.getPayload();
                    view.update();
                    view.NotifyTurnStart(startTurnMessage.getMaxValueofTiles(),this.username);

                    stage.setStage(TurnStages.TILESNUM);
                    break;

                case TAKEABLETILES:
                    TakeableTilesResponse takeableTilesResponse = (TakeableTilesResponse) response.getPayload();
                    view.update();
                    view.takeableTiles(takeableTilesResponse.getChoosableTilesList(), takeableTilesResponse.getNum());

                    //view : passo choosableTilesList per "illuminare" sulla board le tiles prendibili
                    stage.setStage(TurnStages.CHOOSETILES);
                    break;

                case POSSIBLECOLUMNS:
                    PossibleColumnsResponse possibleColumnsResponse = (PossibleColumnsResponse) response.getPayload();
                    view.update();
                    view.setPossibleColumns(possibleColumnsResponse.getChoosableColumns());

                    //view : passo il booleano con true e false sulle varie colonne della shelfie
                    stage.setStage(TurnStages.CHOOSECOLUMN);
                    break;
                case SHELFIEUPDATE:
                    ShelfieUpdateMessage shelfieUpdateMessage = (ShelfieUpdateMessage) response.getPayload();
                    view.setPlayersShelfiesView(shelfieUpdateMessage.getUsername(), shelfieUpdateMessage.getShelfie());
                    stage.setStage(TurnStages.NOTURN);
                    //view : passo lo user,la colonna e la lista ordinata di tiles scelte per aggiornare la shelfie dello user corrispondente
                    break;
                case BOARDUPDATE:
                    BoardUpdateMessage boardUpdateMessage = (BoardUpdateMessage) response.getPayload();
                    view.setBoardView(boardUpdateMessage.getMatrix());
                    //view : passo la nuova matrice in modo da visualizzare la board aggiornata
                    break;
                case POINTSUPDATE:
                    PointsUpdateMessage pointsUpdateMessage = (PointsUpdateMessage) response.getPayload();
                    view.setPlayersPointsView(pointsUpdateMessage.getUsername(), pointsUpdateMessage.getPoint());
                    //view : passo lo user,il nuovo punteggio e se questo ha preso qualche common token
                    break;
                case ENDTOKEN:
                    EndTokenTakenMessage endTokenTakenMessage = (EndTokenTakenMessage) response.getPayload();
                    view.setEndToken(endTokenTakenMessage.getUsername());
                    //view : passo lo user che ha preso l'endToken
                    break;
                case FINALPOINTS:
                    FinalPointsMessage finalPointsMessage = (FinalPointsMessage) response.getPayload();
                    try {
                        view.setFinalPoints(finalPointsMessage.getUsernames(),finalPointsMessage.getPoints());

                        if(view instanceof View){
                            setStageToEndGame();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    //view : passo la lista degli utenti e la lista dei loro punteggi
                    break;
                case THISNOTTHEDAY:
                    ThisNotTheDay recover = (ThisNotTheDay) response.getPayload();
                    try {
                        view.recover( recover.getGameID(), recover.getMatrix(), recover.getShelfies(), recover.getId1(), recover.getId2(), recover.getPersonalGoalCard(), recover.getPoints(), recover.getPlayerList());
                        stage.setStage(TurnStages.NOTURN);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case UPDATEVIEW:
                    view.update();
                    break;

                case CHATUPDATE:
                    ChatUpdate chatUpdate = (ChatUpdate) response.getPayload();
                    try {
                        view.updateChat(chatUpdate.getChatUpdate());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if(stage.getStage() == TurnStages.NOTURN){
                        view.update();
                    }
                    break;

                case NOTURNSETTER:
                    stage.setStage(TurnStages.NOTURN);
                    break;

                case BOARDREFILL:
                    view.boardRefill();
                    break;

                case PING:

                    PingMessage pingMessage= (PingMessage) response.getPayload();
                    PongMessage pongMessage = new PongMessage(" ");
                    Message request = new Message(MessageType.PONG, pongMessage);

                    send(request);

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
    }
    @Override
    public void chooseColumn (int column){
        ChooseColumnRequest chooseColumnRequest = new ChooseColumnRequest(column);
        Message request = new Message(MessageType.CHOOSECOLUMN, chooseColumnRequest);
        send(request);
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
    public void sendChatMessage(String chatMessage) {
        SendChatMessage sendChatMessage = new SendChatMessage(chatMessage);
        Message request = new Message(MessageType.CHAT, sendChatMessage);
        send(request);
    }

    public void setGameStage(GameStage gameStage) {
        this.stage = gameStage;
        stage.setStage(TurnStages.LOGIN);
    }

    @Override
    public void setStageToEndGame() {
        stage.setStage(TurnStages.ENDGAME);
    }

    @Override
    public TurnStages getGameStage() {
        return stage.getStage();
    }

    @Override
    public void sendChatPrivateMessage(String chatMessage, String receiver) {
        SendPrivateChatMessage sendPrivateChatMessage = new SendPrivateChatMessage(this.username, chatMessage, receiver);
        Message request = new Message(MessageType.PRIVATECHAT, sendPrivateChatMessage);
        send(request);

    }

}
