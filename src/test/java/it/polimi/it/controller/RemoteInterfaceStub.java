package it.polimi.it.controller;

import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.client.RemoteInterface;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RemoteInterfaceStub implements RemoteInterface {
    @Override
    public void printError(String s) throws RemoteException {

    }

    @Override
    public void setEndToken(String username) throws RemoteException {

    }

    @Override
    public void setFinalPoints(List<String> usernames, ArrayList<Integer> points) throws IOException {

    }

    @Override
    public void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) throws RemoteException {

    }

    @Override
    public void updateView() throws RemoteException {

    }

    @Override
    public void updateChat(List<String> currentChat) throws IOException {

    }

    @Override
    public void setStageToNoTurn() throws RemoteException {

    }

    @Override
    public void boardRefill() throws RemoteException {

    }

    @Override
    public void setStartOrder(ArrayList<String> order) throws RemoteException {

    }

    @Override
    public void setNewBoard(Tile[][] matrix) throws RemoteException {

    }

    @Override
    public void setNewCommon(int id1, int id2) throws RemoteException {

    }

    @Override
    public void setNewPersonal(PersonalGoalCard card) throws RemoteException {

    }

    @Override
    public void setNewShelfie(String username, Tile[][] shelfie) throws RemoteException {

    }

    @Override
    public void setNewPoints(String username, Integer points) throws RemoteException {

    }

    @Override
    public void notifyTurnStart(int maxValueofTiles) throws RemoteException {

    }

    @Override
    public void askColumn(boolean[] choosableColumns) throws RemoteException {

    }

    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException {

    }

    @Override
    public void ping() throws RemoteException {

    }

    @Override
    public void restart() throws RemoteException {

    }
}
