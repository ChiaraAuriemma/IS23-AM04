package it.polimi.it.model;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.server.RMIImplementation;
import it.polimi.it.network.server.ServerTCP;
import it.polimi.it.network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;

public class VirtualViewStub extends VirtualView {


    @Override
    public void startOrder(ArrayList<User> order){

    }

    @Override
    public void insertDisconnection (String username){

    }

    @Override
    public void removeDisconnection (String username){

    }

    @Override
    public void initialMatrix(Tile[][] matrix){

    }

    @Override
    public void drawnCommonCards(CommonGoalCard card1, CommonGoalCard card2, List<Integer> commonToken1, List<Integer> commonToken2){

    }
    @Override
    public void drawnPersonalCard(String username, PersonalGoalCard card){

    }

    @Override
    public void startTurn(String username,int maxValueofTiles){

    }

    @Override
    public void notifyTurnStart(User user){

    }

    @Override
    public void takeableTiles(String username, List<List<Tile>> choosableTilesList, int num){

    }

    @Override
    public void possibleColumns(String username, boolean[] choosableColumns){

    }

    @Override
    public void shelfieUpdate(User user){

    }

    @Override
    public void boardUpdate(Tile[][] matrix){

    }

    @Override
    public void pointsUpdate(User user, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) {

    }

    @Override
    public void endTokenTaken(User user){

    }

    @Override
    public void finalPoints(List<User> user, ArrayList<Integer> points){

    }

    @Override
    public void viewUpdate(){

    }

    @Override
    public void resetAfterDisconnection(String user, int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, CommonGoalCard card1, CommonGoalCard card2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList){

    }

    @Override
    public void setServerTCP(ServerTCP serverTCP){

    }

    @Override
    public void setServerRMI(RMIImplementation serverRMI){

    }

    @Override
    public void sendChatUpdate(List<String> currentChat, User receiver){

    }

    @Override
    public void boardRefill(){

    }

    @Override
    public void notifyEndGameDisconnection(){

    }


}
