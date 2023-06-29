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
import it.polimi.it.view.Cli;
import it.polimi.it.view.GUI.GUIApplication;
import it.polimi.it.view.GUI.GUIHandler;
import it.polimi.it.view.ViewInterface;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Class of the clients which choose to connect via TCP
 */
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

    private boolean isConnected;
    /**
     * Constructor method of the ClientTCP class
     * @param port is the server port
     * @param ip is the ip address necessary for the TCP communication protocol.
     */
    public ClientTCP(int port, String ip){
        this.port = port;
        this.ip = ip;
        try{
            serverSocket = new Socket(ip,port);
            try{
                out = new ObjectOutputStream(serverSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in = new ObjectInputStream(serverSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        }

    }


    /**
     * Setter method: given a
     * @param viewChoice string, instances either a CLI or a GUI
     */
    public void setView(String viewChoice) {
        if(viewChoice.equalsIgnoreCase("CLI")){
            this.view = new Cli();
        }else if (viewChoice.equalsIgnoreCase("GUI")){
            this.view = new GUIHandler();
        }
        view.askNickname();
    }


    /**
     * Runnable method that receives the TCP messages from the server,
     * then operates the right action according to the message type.
     */
    public void run()  {
        Message response=null;
        MessageType messType;

        this.isConnected = true;
        while(isConnected){
            try {
                response = (Message) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("SERVER DISCONNECTED!");
                isConnected=false;
            }
            messType = response.getType();

            switch (messType){

                case CREATEPLAYERRESPONSE:
                    LoginResponse loginResponse = (LoginResponse) response.getPayload();
                    username = loginResponse.getUsername();
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
                    view.setOrderView(startOrderMessage.getOrder());
                    stage.setStage(TurnStages.NOTURN);
                    break;

                case DRAWNCOMMONCARDS:
                    DrawnCommonCardsMessage drawnCommonCardsMessage = (DrawnCommonCardsMessage) response.getPayload();
                    view.setCommon1View(drawnCommonCardsMessage.getId1());
                    view.setCommon2View(drawnCommonCardsMessage.getId2());
                    break;

                case DRAWNPERSONALCARD:
                    DrawnPersonalCardMessage drawnPersonalCardMessage = (DrawnPersonalCardMessage) response.getPayload();
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
                    stage.setStage(TurnStages.CHOOSETILES);
                    break;

                case POSSIBLECOLUMNS:
                    PossibleColumnsResponse possibleColumnsResponse = (PossibleColumnsResponse) response.getPayload();
                    view.update();
                    view.setPossibleColumns(possibleColumnsResponse.getChoosableColumns());
                    stage.setStage(TurnStages.CHOOSECOLUMN);
                    break;

                case SHELFIEUPDATE:
                    ShelfieUpdateMessage shelfieUpdateMessage = (ShelfieUpdateMessage) response.getPayload();
                    view.setPlayersShelfiesView(shelfieUpdateMessage.getUsername(), shelfieUpdateMessage.getShelfie());
                    stage.setStage(TurnStages.NOTURN);
                    break;

                case BOARDUPDATE:
                    BoardUpdateMessage boardUpdateMessage = (BoardUpdateMessage) response.getPayload();
                    view.setBoardView(boardUpdateMessage.getMatrix());
                    break;

                case POINTSUPDATE:
                    PointsUpdateMessage pointsUpdateMessage = (PointsUpdateMessage) response.getPayload();
                    view.setPlayersPointsView(pointsUpdateMessage.getUsername(), pointsUpdateMessage.getPoint());
                    break;

                case ENDTOKEN:
                    EndTokenTakenMessage endTokenTakenMessage = (EndTokenTakenMessage) response.getPayload();
                    view.setEndToken(endTokenTakenMessage.getUsername());
                    break;

                case FINALPOINTS:
                    FinalPointsMessage finalPointsMessage = (FinalPointsMessage) response.getPayload();
                    try {
                        setStageToEndGame();
                        view.setFinalPoints(finalPointsMessage.getUsernames(),finalPointsMessage.getPoints());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

                case RESTART:
                    view.clean();
                    break;

                case PING:
                    PingMessage pingMessage= (PingMessage) response.getPayload();
                    PongMessage pongMessage = new PongMessage(" ");
                    Message request = new Message(MessageType.PONG, pongMessage);
                    send(request);
                    break;

                case ERROR:
                    ErrorMessage errorMessage = (ErrorMessage) response.getPayload();
                    if(!errorMessage.getError().equals("You have won because this game was closed due to the lack of players! :(\n")){
                        view.printError(errorMessage.getError());
                    }else{
                        setStageToEndGame();
                        view.printError(errorMessage.getError() + " Type create_game>>* or join_game>>* if you want to play again...  ");
                    }
                    break;

                default: break;
            }
        }

        try {
            in.close();
            out.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("\n");
        }
    }


    /**
     * Sends to the server the player's nickname String
     * @param nickname String.
     */
    @Override
    public void login(String nickname) {
        LoginRequest loginRequest = new LoginRequest(nickname);
        Message request = new Message(MessageType.CREATEPLAYER, loginRequest);
        send(request);
    }


    /**
     * Communicates the server the number of people that the user wants in his new game
     * @param playerNumber .
     */
    @Override
        public void createGame(int playerNumber){
        CreateGameRequest createGameRequest = new CreateGameRequest(username,playerNumber);
        Message request = new Message(MessageType.CREATEGAME, createGameRequest);
        send(request);
    }


    /**
     * Method that communicates to the server the ID of the game that the user wants to join
     * @param gameId .
     */
    @Override
    public void joinGame(int gameId){
        JoinGameRequest joinGameRequest = new JoinGameRequest(username,gameId);
        Message request = new Message(MessageType.JOINGAME, joinGameRequest);
        send(request);
    }


    /**
     * Communicates to the server the number of tiles that the user wants to take from the LivingRoom.
     * @param numOfTiles .
     */
    @Override
    public void tilesNumMessage(int numOfTiles){
        TilesNumRequest tilesNumRequest = new TilesNumRequest(numOfTiles);
        Message request = new Message(MessageType.TILESNUMMESSAGE, tilesNumRequest);
        send(request);
    }


    /**
     * Getter method for the ViewInterface.
     * @return the instance of the view.
     */
    @Override
    public ViewInterface getView(){
        return this.view;
    }


    /**
     * Communicates to the server the list of tiles that the user wants to take from the LivingRoom.
     * @param choices .
     */
    @Override
    public void selectedTiles(List<Tile> choices){
        SelectedTilesRequest selectedTilesRequest = new SelectedTilesRequest(choices);
        Message request = new Message(MessageType.SELECTEDTILES, selectedTilesRequest);
        send(request);
    }


    /**
     * Communicates to the server the column in which the user wants to put the tiles that he took from the LivingRoom.
     * @param column .
     */
    @Override
    public void chooseColumn (int column){
        ChooseColumnRequest chooseColumnRequest = new ChooseColumnRequest(column);
        Message request = new Message(MessageType.CHOOSECOLUMN, chooseColumnRequest);
        send(request);
        System.out.println("End of your turn\n");
    }


    /**
     * Method that sends a TCP message to the server.
     * @param message is the message to be sent to the server.
     */
    public void send(Message message){
        synchronized (out){
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.out.println("Couldn't reach the server...\n");
            }
        }
    }


    /**
     * Sends to the server the latest chatMessage written by the player.
     * @param chatMessage that the user wrote in the chat
     */
    @Override
    public void sendChatMessage(String chatMessage) {
        SendChatMessage sendChatMessage = new SendChatMessage(chatMessage);
        Message request = new Message(MessageType.CHAT, sendChatMessage);
        send(request);
    }


    /**
     * Setter and initializer method
     * @param gameStage is the new GameStage instance that will be linked to the client.
     *                  The stage is set to LOGIN.
     */
    public void setGameStage(GameStage gameStage) {
        this.stage = gameStage;
        stage.setStage(TurnStages.LOGIN);
    }


    /**
     * Forces the GameStage to be ENDGAME
     */
    @Override
    public void setStageToEndGame() {
        stage.setStage(TurnStages.ENDGAME);
    }


    /**
     *  Forces the GameStage to be CREATEorJOIN
     */
    @Override
    public void setStageToCreate() {
        stage.setStage(TurnStages.CREATEorJOIN);
    }


    /**
     * Getter method for the TurnStage
     * @return the current turn Stage.
     */
    @Override
    public TurnStages getGameStage() {
        return stage.getStage();
    }


    /**
     * Method that sends a new private chatMessage.
     * @param chatMessage written by this player to the
     * @param receiver of the message
     */
    @Override
    public void sendChatPrivateMessage(String chatMessage, String receiver) {
        SendPrivateChatMessage sendPrivateChatMessage = new SendPrivateChatMessage(this.username, chatMessage, receiver);
        Message request = new Message(MessageType.PRIVATECHAT, sendPrivateChatMessage);
        send(request);
    }


    /**
     * Getter method of the nickname
     * @return the nickname String
     */
    @Override
    public String getNickname(){
        return this.username;
    }

}
