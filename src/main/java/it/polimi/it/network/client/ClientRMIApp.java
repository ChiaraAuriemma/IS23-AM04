package it.polimi.it.network.client;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.server.ServerInterface;
import it.polimi.it.view.View;
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
    private boolean nickOK = false;

    private String nickname;
    ViewInterface view;
    private List<Tile> lastChosen = new ArrayList<>();

    private GameStage stage;


    public ClientRMIApp(int port, String ip) throws RemoteException {
        this.port = port;
        this.ip = ip;
    }

    public void setView(String viewChoice) throws RemoteException{
       if(viewChoice.equalsIgnoreCase("CLI")){
           this.view = new View();
       }else if (viewChoice.equalsIgnoreCase("GUI")){
           this.view = new GUIHandler();
       }
        view.askNickname();
    }

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
     *
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


            //view : passo alla schermata con create e join game
        } catch (ExistingNicknameException | EmptyNicknameException | InvalidIDException e) {
            //view : notifico la view che deve riproporre l'inserimento del nickname con l'errore in rosso
            //view.askNicknameAgain(e.getMessage());
            view.printError(e.getMessage());
        }

    }

    @Override
    public void printError(String e)  throws RemoteException{
        view.printError(e);
    }

    /**
     * Communicates the server the number of people that the user wants in his new game
     *
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
            //view : notifico alla view che il numero di giocatori inseriti non è corretto
            //view.askNumPlayerAgain();
            view.printError(e.getMessage());
        }
    }


    /**
     * Method that communicates to the server the ID of the game that the user wants to join
     *
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
            //view.askIDAgain();
            view.printError(e.getMessage());
        }
    }

    @Override
    public void tilesNumMessage(int numOfTiles) throws RemoteException {
        try {   //comunico al server il numero scelto
            sr.tilesNumMessage(this.nickname, numOfTiles);
            stage.setStage(TurnStages.CHOOSETILES);
        } catch (IllegalValueException | WrongPlayerException | InvalidIDException | IOException e) {
            //view : notifico alla view che il numero di tiles indicato non è valido
            //view.askNumTilesAgain();
            view.printError(e.getMessage());
        }
    }

    @Override
    public void selectedTiles(List<Tile> choosenTiles) throws IOException {
        //mando le tiles a RMIImplementation
        try {
            sr.selectedTiles(this.nickname, choosenTiles);
            stage.setStage(TurnStages.CHOOSECOLUMN);
        } catch (WrongPlayerException | WrongListException | IllegalValueException | InvalidIDException | IOException e) {
            //view.askTilesAgain();
            view.printError(e.getMessage());
        }
    }

    @Override
    public void chooseColumn(int numOfColum) throws RemoteException {
        try {
            sr.chooseColumn(this.nickname, numOfColum);
            System.out.println("End of your turn\n");
        } catch (InvalidIDException | IllegalValueException | IOException e) {
            //view.askColumnAgain();
            view.printError(e.getMessage());
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
    public void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) throws RemoteException {

        view.recover(gameID, matrix, shelfies, id1, id2, personalGoalCard, points, playerList);
        stage.setStage(TurnStages.NOTURN);
    }


    @Override
    public void updateView() {
        view.update();
    }

    @Override
    public void sendChatMessage(String chatMessage) throws RemoteException {
        sr.chatMessage(this.nickname, chatMessage);
    }

    @Override
    public void updateChat(List<String> currentChat) {
        view.updateChat(currentChat);
        if(stage.getStage() == TurnStages.NOTURN){
            view.update();
        }
    }

    @Override
    public void setStageToNoTurn() throws RemoteException {
        stage.setStage(TurnStages.NOTURN);
    }

    @Override
    public void boardRefill() throws RemoteException {
        view.boardRefill();
    }

    @Override
    public void setNewShelfie(String username, Tile[][] shelfie) {
        view.setPlayersShelfiesView(username, shelfie);
    }

    @Override
    public void setNewBoard(Tile[][] matrix) {
        view.setBoardView(matrix);
    }

    @Override
    public void setNewPoints(String username, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) {
        view.setPlayersPointsView(username, points);

        //chiamo i metodi della view per settare i commontoken
    }

    @Override
    public void notifyTurnStart(int maxValueOfTiles){
        view.NotifyTurnStart(maxValueOfTiles,this.nickname);
        stage.setStage(TurnStages.TILESNUM);
    }

    @Override
    public void askColumn(boolean[] choosableColumns) {
        view.update();
        view.setPossibleColumns(choosableColumns);
    }

    @Override
    public void setStartOrder(ArrayList<String> order) {
        view.setOrderView(order);
        stage.setStage(TurnStages.NOTURN);
    }

    @Override
    public void setNewCommon(int id1, int id2, List<Integer> commonToken1, List<Integer> commonToken2) {
        view.setCommon1View(id1);
        view.setCommon2View(id2);

        //chiamo i metodi della view per settare i commontoken
    }


    @Override
    public void setNewPersonal(PersonalGoalCard card) {
        view.setPlayersPersonalCardView(card);
    }


    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) {
        //view : faccio vedere illuminate le tiles nella lista
        view.update();
        view.takeableTiles(choosableTilesList, num);
    }

    public void setGameStage(GameStage gameStage) throws RemoteException{
        this.stage = gameStage;
        stage.setStage(TurnStages.LOGIN);
    }

    @Override
    public TurnStages getGameStage() {
        return stage.getStage();
    }

    public void ping() throws RemoteException{
        return;
    }

    @Override
    public void sendChatPrivateMessage(String chatMessage, String receiver) throws RemoteException {
        sr.chatPrivateMessage(this.nickname, chatMessage, receiver);
    }

    public GameStage getStage() {
        return stage;
    }
}