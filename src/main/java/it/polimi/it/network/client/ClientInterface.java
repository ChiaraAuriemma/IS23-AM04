package it.polimi.it.network.client;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.view.View;
import it.polimi.it.view.ViewInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ClientInterface extends Remote, Serializable {
    //qui devo mettere solo i metodi visibili dal server (quindi tolgo startclient e login)
    public void login(String userName) throws RemoteException, IOException;

    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException, IOException;

    public void setStartOrder(ArrayList<String> order) throws RemoteException;

    public void setNewBoard(Tile[][] matrix) throws RemoteException;

    public void setNewCommon(CommonGoalCard card1, CommonGoalCard card2) throws RemoteException;

    public void setNewPersonal(PersonalGoalCard card) throws RemoteException;

    public void setNewShelfie(String username, Tile[][] shelfie) throws RemoteException;

    public void setNewPoints(String username, Integer points) throws RemoteException;

    public void notifyTurnStart(int maxValueofTiles) throws IOException;

    public void askColumn(boolean[] choosableColumns) throws IOException;

    public void createGame(int playerNumber) throws RemoteException;

    public void joinGame(int gameId) throws RemoteException;

    public void tilesNumMessage(int numOfTiles) throws RemoteException;

    public ViewInterface getView() throws RemoteException;

    public void selectedTiles(List<Tile> choices) throws IOException;

    public void chooseColumn(int column) throws RemoteException;

    public void setEndToken(String username) throws RemoteException;

    public void setFinalPoints(List<String> usernames, ArrayList<Integer> points) throws RemoteException;

    public void recover(Game game, int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, CommonGoalCard card1, CommonGoalCard card2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) throws RemoteException;

    public void updateView() throws IOException;

    void sendChatMessage(String chatMessage) throws RemoteException;

    void updateChat(List<String> currentChat) throws IOException;

    void setStageToNoTurn() throws RemoteException;

    void boardRefill() throws RemoteException;

    void setView(String viewChoice) throws RemoteException;

    void setGameStage(GameStage gameStage) throws RemoteException;
    TurnStages getGameStage()throws RemoteException;

    void ping() throws RemoteException;
}
