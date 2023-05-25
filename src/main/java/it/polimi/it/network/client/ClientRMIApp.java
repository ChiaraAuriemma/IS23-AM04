package it.polimi.it.network.client;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.server.ServerInterface;
import it.polimi.it.view.GUI.GuiMain;
import it.polimi.it.view.View;
import it.polimi.it.view.ViewInterface;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientRMIApp extends UnicastRemoteObject implements ClientInterface {
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
           this.view = new GuiMain();
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
    public void login(String userName) throws RemoteException {
        try {
            this.nickname = sr.login(this, userName);
            stage.setStage(TurnStages.CREATEorJOIN);
            view.joinOrCreate(this.nickname);

            //view : passo alla schermata con create e join game
        } catch (ExistingNicknameException | EmptyNicknameException e) {
            //view : notifico la view che deve riproporre l'inserimento del nickname con l'errore in rosso
            view.askNicknameAgain(e.getMessage());
        }

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
            view.askNumPlayerAgain();
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

            if(!stage.getStage().equals(TurnStages.TILESNUM)){
                stage.setStage(TurnStages.NOTHING);
            }

            view.setGameID(gameid);
        } catch (InvalidIDException | WrongPlayerException | IllegalValueException e) {
            view.askIDAgain();
        }
    }

    @Override
    public void tilesNumMessage(int numOfTiles) throws RemoteException {
        try {   //comunico al server il numero scelto
            sr.tilesNumMessage(this.nickname, numOfTiles);
            stage.setStage(TurnStages.CHOOSETILES);
        } catch (IllegalValueException | WrongPlayerException e) {
            //view : notifico alla view che il numero di tiles indicato non è valido
            view.askNumTilesAgain();
        } catch (InvalidIDException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void selectedTiles(List<Tile> choosenTiles) throws RemoteException {
        //mando le tiles a RMIImplementation
        try {
            sr.selectedTiles(this.nickname, choosenTiles);
            stage.setStage(TurnStages.CHOOSECOLUMN);
        } catch (WrongPlayerException | WrongListException e) {
            view.askTilesAgain();
        } catch (IllegalValueException | InvalidIDException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chooseColumn(int numOfColum) throws RemoteException {
        try {
            sr.chooseColumn(this.nickname, numOfColum);
            System.out.println("End of your turn\n");
        } catch (InvalidIDException | IllegalValueException e) {
            view.askColumnAgain();
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
        //view.recover(game, gameID, nickname);
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
    public void updateChat(List<String> currentChat) throws RemoteException{
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
    public void setNewPoints(String username, Integer points) {
        view.setPlayersPointsView(username, points);
    }

    @Override
    public void notifyTurnStart(int maxValueOfTiles) {
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
    public void setNewCommon(CommonGoalCard card1, CommonGoalCard card2) {
        view.setCommon1View(card1);
        view.setCommon2View(card2);
    }


    @Override
    public void setNewPersonal(PersonalGoalCard card) {
        view.setPlayersPersonalCardView(card);
    }


    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException {
        //view : faccio vedere illuminate le tiles nella lista
        view.update();
        switch (num){
            case 1:
                System.out.println("Please choose " + num + " tiles from the board... ( Use take_tiles>>(row,column) )\n");
                break;
            case 2:
                System.out.println("Please choose " + num + " tiles from the board... ( Use take_tiles>>(row,column);(row,column) )\n");
                break;
            case 3:
                System.out.println("Please choose " + num + " tiles from the board... ( Use take_tiles>>(row,column);(row,column);(row,column) )\n");
                break;
            default:
                System.out.println("Wrong number");
                break;
        }

        view.takeableTiles(choosableTilesList);
    }

    public void setGameStage(GameStage gameStage) throws RemoteException{
        this.stage = gameStage;
        stage.setStage(TurnStages.LOGIN);
    }

    public void ping() throws RemoteException{
        return;
    }
}