package it.polimi.it.network.client;


import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RemoteInterface extends Remote, Serializable {

    public void printError(String s)  throws RemoteException;

    public void setEndToken(String username) throws RemoteException;

    public void setFinalPoints(List<String> usernames, ArrayList<Integer> points) throws RemoteException;

    public void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) throws RemoteException;

    public void updateView() throws RemoteException;

    public void updateChat(List<String> currentChat) throws RemoteException;

    public void setStageToNoTurn() throws RemoteException;

    public void boardRefill() throws RemoteException;

    public void setStartOrder(ArrayList<String> order) throws RemoteException;

    public void setNewBoard(Tile[][] matrix) throws RemoteException;

    public void setNewCommon(int id1, int id2, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException;

    public void setNewPersonal(PersonalGoalCard card) throws RemoteException;

    public void setNewShelfie(String username, Tile[][] shelfie) throws RemoteException;

    public void setNewPoints(String username, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException;

    public void notifyTurnStart(int maxValueofTiles) throws RemoteException;

    public void askColumn(boolean[] choosableColumns) throws RemoteException;

    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException;

    public void ping() throws RemoteException;
}
