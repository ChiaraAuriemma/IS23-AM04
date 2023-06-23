package it.polimi.it.network.client;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.server.ServerInterface;
import it.polimi.it.view.Cli;
import it.polimi.it.view.ViewInterface;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ClientRMIApp extends UnicastRemoteObject implements ClientInterface, RemoteInterface {
    //private static final long serialVersionUID = 5072588874360370885L;
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
     * @param port and
     * @param ip are the port and ip address necessary for the RMI communication protocol.
     */
    public ClientRMIApp(int port, String ip) throws RemoteException {
        this.port = port;
        this.ip = ip;
    }


    /**
     * Setter method: given a
     * @param viewChoice string, instances either a CLI or a GUI
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
     * Getter method
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
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            this.sr = (ServerInterface) registry.lookup("server_RMI");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }

    }


    /**
     * Method called by startClient, sends the server the chosen username
     * @param userName .
     * @throws RemoteException .
     */
    public void login(String userName) throws RemoteException,IOException {
        try {
            this.nickname = sr.login(this, userName);
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
     * @param e that contains an error message.
     * @throws RemoteException .
     */
    @Override
    public void printError(String e)  throws RemoteException{
        view.printError(e);
    }


    /**
     * Communicates the server the number of people that the user wants in his new game
     * @param playerNumber .
     * @throws RemoteException .
     */
    @Override
    public void createGame(int playerNumber) throws RemoteException {
        try {
            int gameid = sr.createGame(this.nickname, playerNumber, this);
            stage.setStage(TurnStages.NOTHING);
            view.setGameID(gameid);
        } catch (WrongPlayerException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Method that communicates to the server the ID of the game that the user wants to join
     * @param gameID .
     * @throws RemoteException .
     */
    public void joinGame(int gameID) throws RemoteException {
        try {
            int gameid = sr.joinGame(this.nickname, gameID, this);

            if(stage.getStage().equals(TurnStages.CREATEorJOIN)){
                stage.setStage(TurnStages.NOTHING);
            }
            view.setGameID(gameid);
        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | IOException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Communicates to the server the
     * @param numOfTiles number of tiles that the user wants to take from the LivingRoom.
     * @throws RemoteException .
     */
    @Override
    public void tilesNumMessage(int numOfTiles) throws RemoteException {
        try {
            sr.tilesNumMessage(this.nickname, numOfTiles);
            stage.setStage(TurnStages.CHOOSETILES);
        } catch (IllegalValueException | WrongPlayerException | InvalidIDException | IOException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Communicates to the server the
     * @param choosenTiles list of tiles that the user wants to take from the LivingRoom.
     * @throws RemoteException .
     */
    @Override
    public void selectedTiles(List<Tile> choosenTiles) throws IOException {
        try {
            sr.selectedTiles(this.nickname, choosenTiles);
            stage.setStage(TurnStages.CHOOSECOLUMN);
        } catch (WrongTileException | WrongPlayerException | WrongListException | IllegalValueException | InvalidIDException | IOException e) {
            view.printError(e.getMessage());
        }
    }


    /**
     * Communicates to the server the
     * @param numOfColum column in which the user wants to put the tiles that he took from the LivingRoom.
     * @throws RemoteException .
     */
    @Override
    public void chooseColumn(int numOfColum) throws RemoteException {
        try {
            sr.chooseColumn(this.nickname, numOfColum);
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
        view.setFinalPoints(usernames, points);
        //if(view instanceof Cli){
            setStageToEndGame();
        //}
    }


    /**
     * Method that restores all the game informations after a reconnections.
     * @param gameID ,
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
     * Sends to the server the latest
     * @param chatMessage that the user wrote in the chat
     * @throws RemoteException .
     */
    @Override
    public void sendChatMessage(String chatMessage) throws RemoteException {
        sr.chatMessage(this.nickname, chatMessage);
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
     * Setter method for a player's shelfie.
     * @param username is the player's nickname,
     * @param shelfie is the player's Shelfie.
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
     * @param username ,
     * @param points ,
     * @param commonToken1 is the list of the player's tokens for the first CommonGoalCard,
     * @param commonToken2 is the list of the player's tokens for the second CommonGoalCard,
     */
    @Override
    public void setNewPoints(String username, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) {
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
     * Setter method for:
     * @param order players' order.
     */
    @Override
    public void setStartOrder(ArrayList<String> order) {
        view.setOrderView(order);
        stage.setStage(TurnStages.NOTURN);
    }


    /**
     * Setter method for the following parameters:
     * @param id1 CommonGoalCard's ID 1,
     * @param id2 CommonGoalCard's ID 2,
     * @param commonToken1 CommonGoalCard's token 1,
     * @param commonToken2 CommonGoalCard's token 2,
     */
    @Override
    public void setNewCommon(int id1, int id2, List<Integer> commonToken1, List<Integer> commonToken2) {
        view.setCommon1View(id1);
        view.setCommon2View(id2);
    }


    /**
     * Setter method.
     * @param card PersonalGoalCard
     */
    @Override
    public void setNewPersonal(PersonalGoalCard card) {
        view.setPlayersPersonalCardView(card);
    }


    /**
     * Method that sends from the server to the client the following parameters:
     * @param choosableTilesList is a list of groups of tiles
     *                           which are all the possible groups of tiles that the player might choose.
     * @param num is the exact number of tiles that the player has to take.
     */
    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) {
        view.update();
        view.takeableTiles(choosableTilesList, num);
    }


    /**
     * Setter method
     * @param gameStage is the new GameStage instance, also sets the stage to LOGIN
     * @throws RemoteException .
     */
    public void setGameStage(GameStage gameStage) throws RemoteException{
        this.stage = gameStage;
        stage.setStage(TurnStages.LOGIN);
    }


    /**
     * Getter method
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


    /**
     * Ping method.
     * @throws RemoteException : if this exception is invoked, the server gets to know that this client disconnected
     */
    public void ping() throws RemoteException{
    }


    /**
     * Method that sends the private
     * @param chatMessage written by this player to the
     * @param receiver of the message
     * @throws RemoteException .
     */
    @Override
    public void sendChatPrivateMessage(String chatMessage, String receiver) throws RemoteException {
        try {
            sr.chatPrivateMessage(this.nickname, chatMessage, receiver);
        }catch (IllegalValueException e){
            view.printError(e.getMessage());
        }
    }


    /**
     * Getter method
     * @return the nickname String
     * @throws RemoteException .
     */
    @Override
    public String getNickname() throws RemoteException {
        return this.nickname;
    }


    /**
     * Getter method
     * @return the current GameStage.
     */
    public GameStage getStage() {
        return stage;
    }
}