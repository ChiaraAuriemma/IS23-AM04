package it.polimi.it.network.client;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.view.View;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ClientInterface extends Remote, Serializable {
    //qui devo mettere solo i metodi visibili dal server (quindi tolgo startclient e login)
    public void startClient() throws IOException, NotBoundException;
    public void login(String userName) throws RemoteException;

    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException;

    public void setStartOrder(ArrayList<User> order) throws RemoteException;

    public void setNewBoard(Tile[][] matrix) throws RemoteException;

    public void setNewCommon(CommonGoalCard card1, CommonGoalCard card2) throws RemoteException;

    public void setNewPersonal(PersonalGoalCard card) throws RemoteException;

    public void setNewShelfie(User receiver, Tile[][] shelfie) throws RemoteException;

    public void setNewPoints(User user, Integer points) throws RemoteException;

    public void notifyTurnStart(int maxValueofTiles)throws RemoteException;

    public void askColumn(boolean[] choosableColumns)throws RemoteException;

    public void createGame(int playerNumber) throws RemoteException;

    public void joinGame(int gameId) throws RemoteException;

    public void tilesNumMessage(int numOfTiles) throws RemoteException;

    public View getView() throws RemoteException;

    public void selectedTiles(List<Tile> choices) throws RemoteException;

    public void chooseColumn(int column) throws RemoteException;

    void setEndToken(User user) throws RemoteException;

    void setFinalPoints(List<User> users, ArrayList<Integer> points) throws RemoteException;

    void recover(Game game, int gameID) throws RemoteException;

    void updateView() throws RemoteException;
}
