package it.polimi.it.view;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class View {
    private HashMap<User, Tile[][]> playersShelfies = new HashMap<>();
    private HashMap<User, Integer> playersPoints = new HashMap<>();
    private Tile[][] playersPersonalCard;
    private ArrayList<User> order;
    private Tile[][] board = new Tile[9][9];
    //private Tile[][] highlightedBoard = new Tile[9][9];
    private Tile[][] common1;
    private Tile[][] common2;

    public void setPlayersShelfiesView(User player, Tile[][] shelfie){
        playersShelfies.put(player, shelfie);
    }

    public void setPlayersPointsView(User player, int points){
        playersPoints.put(player, points);
    }

    public void SetOrderView(ArrayList<User> order){
        Collections.copy(this.order, order);
    }

    public void setPlayersPersonalCardView(PersonalGoalCard card){
        playersPersonalCard = new Tile[6][5];
        for(int row=0; row<6; row++){
            for(int column=0; column<5; column++){
                playersPersonalCard[row][column] = new Tile(row, column, PossibleColors.DEFAULT);
            }
        }
        playersPersonalCard[card.getCyanposRow()][card.getCyanposColumn()] = new Tile(PossibleColors.CYAN);
        playersPersonalCard[card.getBlueposRow()][card.getBlueposColumn()] = new Tile(PossibleColors.BLUE);
        playersPersonalCard[card.getPinkposRow()][card.getPinkposColumn()] = new Tile(PossibleColors.PINK);
        playersPersonalCard[card.getYellowposRow()][card.getYellowposColumn()] = new Tile(PossibleColors.YELLOW);
        playersPersonalCard[card.getGreenposRow()][card.getGreenposColumn()] = new Tile(PossibleColors.GREEN);
        playersPersonalCard[card.getWhiteposRow()][card.getWhiteposColumn()] = new Tile(PossibleColors.WHITE);
    }

    public void setBoardView(Tile[][] matrix){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                board[i][j] = new Tile(PossibleColors.valueOf(matrix[i][j].getColor()));
            }
        }
    }

    public void setCommon1View(CommonGoalCard card1){
        int id1 = card1.getID();
    }
    public void setCommon2View(CommonGoalCard card2){
        int id2 = card2.getID();
    }
}
