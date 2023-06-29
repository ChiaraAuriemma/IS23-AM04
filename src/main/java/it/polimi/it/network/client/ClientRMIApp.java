package it.polimi.it.network.client;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.server.ServerInterface;
import it.polimi.it.view.Cli;
import it.polimi.it.view.GUI.GUIHandler;
import it.polimi.it.view.ViewInterface;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Class of the clients which choose to connect via RMI
 */
public class ClientRMIApp extends UnicastRemoteObject implements ClientInterface, RemoteInterface {
    private int port;
    private String ip;
    private Registry registry;
    private ServerInterface sr;
    private String nickname;
    ViewInterface view;
    private List<Tile> lastChosen = new ArrayList<>();
    private GameStage stage;


    /**
     * Constructor method of the ClientRMIApp class
     * @param port is the server port
     * @param ip is the ip address necessary for the RMI communication protocol.
     */
    public ClientRMIApp(int port, String ip) throws RemoteException {
        this.port = port;
        this.ip = ip;
    }


    /**
     * Setter method: given a string, instances either a CLI or a GUI
     * @param viewChoice is the client's choice of CLI or GUI
     */
    public void setView(String viewChoice) throws RemoteException{
       if(viewChoice.equalsIgnoreCase("CLI")){
           this.view = new Cli();
       }else if (viewChoice.equalsIgnoreCase("GUI")){
           this.view = new GUIHandler();
       }
        view.askNickname();
    }


    /**
     * Getter method of the view instance
     * @return the view instance
     * @throws RemoteException .
     */
    public ViewInterface getView() throws RemoteException{
        return this.view;
    }


    /**
     * First method used by the client, communicates with the view in order to let him choose his nickname,
     * then communicates it to the serverRMI to create an actual User instance.
     */
    public void startClient() {
        try {
            registry = LocateRegistry.getRegistry(ip, port);
            this.sr = (ServerInterface) registry.lookup("server_RMI");
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Couldn't reach the Server...");
        }
    }


    /**
     * Method called by startClient, sends the server the chosen username
     * @param userName is the client's username
     * @throws RemoteException .
     */
    public void login(String userName) throws RemoteException,IOException {
        try {
            try {
                this.nickname = sr.login(this, userName);
            }catch (RemoteException r){
                view.printError("Server Unreachable");
            }
            if(!stage.getStage().equals(TurnStages.NOTURN)){
                stage.setStage(TurnStages.CREATEorJOIN);
                view.joinOrCreate(this.nickname);
            }
            else view.update();
        } catch (ExistingNicknameException | EmptyNicknameException | InvalidIDException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Method that prints an error message
     * @param e is the string that contains an error message.
     * @throws RemoteException .
     */
    @Override
    public void printError(String e)  throws RemoteException{
        if(!e.equals("You have won because this game was closed due to the lack of players! :(\n")){
            view.printError(e);
        }else{
            setStageToEndGame();
            view.printError(e + " Type create_game>>* or join_game>>* if you want to play again...  ");
        }
    }


    /**
     * Communicates the server the number of people that the user wants in his new game
     * @param playerNumber is the number of people that the user wants in his new game
     * @throws RemoteException .
     */
    @Override
    public void createGame(int playerNumber) throws RemoteException {
        int gameid = 0;
        try {
            try{
                gameid = sr.createGame(this.nickname, playerNumber, this);
            }catch (RemoteException r){
                view.printError("Server Unreachable");
            }
            stage.setStage(TurnStages.NOTHING);
            view.setGameID(gameid);
        } catch (WrongPlayerException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Method that communicates to the server the ID of the game that the user wants to join
     * @param gameID is the server the ID of the game that the user wants to join
     * @throws RemoteException .
     */
    public void joinGame(int gameID) throws RemoteException {
        try {
            int gameid = 0;
            try{
                gameid = sr.joinGame(this.nickname, gameID, this);
            } catch (RemoteException r){
                view.printError("Server Unreachable");
            }

            if(stage.getStage().equals(TurnStages.CREATEorJOIN)){
                stage.setStage(TurnStages.NOTHING);
            }
            view.setGameID(gameid);
        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | IOException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Communicates to the server the  number of tiles that the user wants to take from the LivingRoom.
     * @param numOfTiles is the number of tiles that the user wants to take from the LivingRoom.
     * @throws RemoteException .
     */
    @Override
    public void tilesNumMessage(int numOfTiles) throws RemoteException {
        try {
            try{
                sr.tilesNumMessage(this.nickname, numOfTiles);
            }catch (RemoteException r){
                view.printError("Server Unreachable");
            }
            stage.setStage(TurnStages.CHOOSETILES);
        } catch (IllegalValueException | WrongPlayerException | InvalidIDException | IOException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Communicates to the server the list of tiles that the user wants to take from the LivingRoom.
     * @param choosenTiles is the list of tiles that the user wants to take from the LivingRoom.
     * @throws RemoteException .
     */
    @Override
    public void selectedTiles(List<Tile> choosenTiles) throws IOException {
        try {
            try{
                sr.selectedTiles(this.nickname, choosenTiles);
            }catch (RemoteException r){
                view.printError("Server Unreachable");
            }
            stage.setStage(TurnStages.CHOOSECOLUMN);
        } catch (WrongTileException | WrongPlayerException | WrongListException | IllegalValueException | InvalidIDException | IOException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Communicates to the server the column in which the user wants to put the tiles that he took from the LivingRoom.
     * @param numOfColum is the column in which the user wants to put the tiles that he took from the LivingRoom.
     * @throws RemoteException .
     */
    @Override
    public void chooseColumn(int numOfColum) throws RemoteException {
        try {
            try{
                sr.chooseColumn(this.nickname, numOfColum);
            }catch (RemoteException r){
                view.printError("Server Unreachable");
            }
            System.out.println("End of your turn\n");
        } catch (InvalidIDException | IllegalValueException | IOException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Setter method for the EndToken
     * @param username is the player who won the endToken.
     */
    @Override
    public void setEndToken(String username) {
        view.setEndToken(username);
    }


    /**
     * Setter method for the match's final points in order to print a leaderboard given
     * @param usernames list of the players' usernames and a
     * @param points list of the players' scored points.
     * @throws IOException .
     */
    @Override
    public void setFinalPoints(List<String> usernames, ArrayList<Integer> points) throws IOException {
        setStageToEndGame();
        view.setFinalPoints(usernames, points);
    }


    /**
     * Method that restores all the game informations after a reconnections.
     * @param gameID is the game ID
     * @param matrix is the LivingRoom,
     * @param shelfies is a list of the players' shelves,
     * @param id1 is the ID of the first CommonGoalCard,
     * @param id2 is the ID of the second CommonGoalCard,
     * @param personalGoalCard is the player's PersonalGoalCard,
     * @param points a list of the players' current points,
     * @param playerList is the list of the players' nicknames.
     * @throws RemoteException .
     */
    @Override
    public void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) throws RemoteException {
        view.recover(gameID, matrix, shelfies, id1, id2, personalGoalCard, points, playerList);
        stage.setStage(TurnStages.NOTURN);
    }


    /**
     * Method that forces the view to update.
     */
    @Override
    public void updateView() {
        view.update();
    }


    /**
     * Sends to the server the latest chat message that the user wrote in the chat
     * @param chatMessage is the latest chat message that the user wrote in the chat
     */
    @Override
    public void sendChatMessage(String chatMessage){
        try {
            sr.chatMessage(this.nickname, chatMessage);
        } catch (RemoteException e) {
            view.printError("Server Unreachable");
        }
    }


    /**
     * Setter method for the user's latest chat messages that will be displayed in the view.
     * @param currentChat is the list of messages.
     * @throws IOException .
     */
    @Override
    public void updateChat(List<String> currentChat) throws IOException {
        view.updateChat(currentChat);
        if(stage.getStage() == TurnStages.NOTURN){
            view.update();
        }
    }


    /**
     * Setter method. Forces the gameStage to be NOTURN
     * @throws RemoteException .
     */
    @Override
    public void setStageToNoTurn() throws RemoteException {
        stage.setStage(TurnStages.NOTURN);
    }


    /**
     * Notifies the LivingRoom's refill
     * @throws RemoteException .
     */
    @Override
    public void boardRefill() throws RemoteException {
        view.boardRefill();
    }


    /**
     * Setter method for a player's bookshelf
     * @param username is the player's nickname
     * @param shelfie is the player's bookshelf
     */
    @Override
    public void setNewShelfie(String username, Tile[][] shelfie) {
        view.setPlayersShelfiesView(username, shelfie);
    }


    /**
     * Setter method for the view's LivingRoom.
     * @param matrix LivingRoom's disposition.
     */
    @Override
    public void setNewBoard(Tile[][] matrix) {
        view.setBoardView(matrix);
    }


    /**
     * Setter method for the points of a given user.
     * @param username is the client's username
     * @param points are the client's points
     */
    @Override
    public void setNewPoints(String username, Integer points) {
        view.setPlayersPointsView(username, points);
    }


    /**
     * Method that notifies the start of a turn.
     * @param maxValueOfTiles is the maximum number of tiles that the player might choose to take from the LivingRoom.
     */
    @Override
    public void notifyTurnStart(int maxValueOfTiles){
        view.NotifyTurnStart(maxValueOfTiles,this.nickname);
        stage.setStage(TurnStages.TILESNUM);
    }


    /**
     * Method invoked by the server, asks the user to choose a column of his shelf.
     * @param choosableColumns is a boolean array,
     *                         the true indexes represent the columns where the player may put the tiles.
     */
    @Override
    public void askColumn(boolean[] choosableColumns) {
        view.update();
        view.setPossibleColumns(choosableColumns);
    }


    /**
     * Setter method for the game order
     * @param order is the players' order.
     */
    @Override
    public void setStartOrder(ArrayList<String> order) {
        view.setOrderView(order);
        stage.setStage(TurnStages.NOTURN);
    }


    /**
     * Setter method for the ID's of the common cards and the ID's of the personal cards
     * @param id1 CommonGoalCard's ID 1
     * @param id2 CommonGoalCard's ID 2
     */
    @Override
    public void setNewCommon(int id1, int id2) {
        view.setCommon1View(id1);
        view.setCommon2View(id2);
    }


    /**
     * Setter method of the personal card
     * @param card is the PersonalGoalCard
     */
    @Override
    public void setNewPersonal(PersonalGoalCard card) {
        view.setPlayersPersonalCardView(card);
    }


    /**
     * Method that sends from the server to the client the list of groups of tiles which are all the possible groups
     * of tiles that the player might choose.
     * @param choosableTilesList is a list of groups of tiles which are all the possible groups of tiles that
     * the player might choose.
     * @param num is the exact number of tiles that the player has to take.
     */
    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) {
        view.update();
        view.takeableTiles(choosableTilesList, num);
    }


    /**
     * Setter method of the GameStage class of the client
     * @param gameStage is the new GameStage instance, also sets the stage to LOGIN
     * @throws RemoteException .
     */
    public void setGameStage(GameStage gameStage) throws RemoteException{
        this.stage = gameStage;
        stage.setStage(TurnStages.LOGIN);
    }


    /**
     * Getter method of the GameStage
     * @return the current GameStage.
     */
    @Override
    public TurnStages getGameStage() {
        return stage.getStage();
    }


    /**
     * Setter method for the stage to ENDGAME.
     */
    @Override
    public void setStageToEndGame() {
        stage.setStage(TurnStages.ENDGAME);
    }

    @Override
    public void setStageToCreate() throws RemoteException {
        stage.setStage(TurnStages.CREATEorJOIN);
    }


    /**
     * Ping method.
     * @throws RemoteException : if this exception is invoked, the server gets to know that this client disconnected
     */
    public void ping() throws RemoteException{
    }


    /**
     * Method that sends the private chat message written by this player to the receiver
     * @param chatMessage is the chat message written by this player
     * @param receiver is the receiver of the message
     * @throws RemoteException .
     */
    @Override
    public void sendChatPrivateMessage(String chatMessage, String receiver) throws RemoteException {
        try {
            sr.chatPrivateMessage(this.nickname, chatMessage, receiver);
        }catch (RemoteException r){
            view.printError("Server Unreachable");
        }catch (IllegalValueException e){
            view.printError(e.getMessage());
        }
    }

    /**
     * After the end of a game this method gives the possibility to create a new game.
     * @throws RemoteException .
     */
    @Override
    public void restart() throws RemoteException{
        view.clean();
    }


    /**
     * Getter method of the nickname
     * @return the nickname String
     * @throws RemoteException .
     */
    @Override
    public String getNickname() throws RemoteException {
        return this.nickname;
    }


    /**
     * Getter method of the current GameStage
     * @return the current GameStage.
     */
    public GameStage getStage() {
        return stage;
    }
}