package it.polimi.it.network.message.others;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ThisNotTheDay extends Payload  implements Serializable {
    private static final long serialVersionUID = -3294233920082178659L;
    private int gameID;
    private Tile[][] matrix;
    private ArrayList<Tile[][]> shelfies = new ArrayList<>();
    private int id1;
    private int id2;
    private PersonalGoalCard personalGoalCard;
    private ArrayList<Integer> points;
    private List<String> playerList = new ArrayList<>();

    //Tile[][] matrix, ArrayList<Tile[][]> shelfies, it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard card1, CommonGoalCard card2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList

    public ThisNotTheDay(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList
    ){
        this.gameID=gameID;
        this.matrix=matrix;
        this.shelfies=shelfies;
        this.id1 = id1;
        this.id2 = id2;
        this.personalGoalCard=personalGoalCard;
        this.points=points;
        this.playerList=playerList;
    }


    public int getGameID() {
        return gameID;
    }

    public Tile[][] getMatrix() {
        return matrix;
    }


    public ArrayList<Tile[][]> getShelfies() {
        return shelfies;
    }


    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public List<String> getPlayerList() {
        return playerList;
    }

}
