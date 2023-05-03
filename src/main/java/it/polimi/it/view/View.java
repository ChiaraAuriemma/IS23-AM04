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
    private HashMap<User, String> playersNicknames = new HashMap<>();
    private Tile[][] playersPersonalCard;
    private ArrayList<User> order;
    private Tile[][] board = new Tile[9][9];
    //private Tile[][] highlightedBoard = new Tile[9][9];
    private String common1;
    private String common1SecondPart;
    private String common2;
    private String common2SecondPart;

    public void setPlayersShelfiesView(User player, Tile[][] shelfie){
        playersShelfies.put(player, shelfie);
    }

    public void setPlayersNicknamesView(User player){
        playersNicknames.put(player, player.getNickname());
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
        common1 = commonDescription(id1);
        common1SecondPart = commonDescriptionSecondPart(id1);
    }
    public void setCommon2View(CommonGoalCard card2){
        int id2 = card2.getID();
        common2 = commonDescription(id2);
        common2SecondPart = commonDescriptionSecondPart(id2);
    }

    public String commonDescription(int id) {
        return switch (id) { // stringhe lunghe 48 caselle
            case 1 ->  "Two groups each containing 4 tiles of the same ";
            case 2 ->  "Two columns each formed by 6  different types  ";
            case 3 ->  "Four groups each containing at least 4 tiles of";
            case 4 ->  "Six groups each containing at least 2 tiles of ";
            case 5 ->  "Three columns each formed by 6 tiles of        ";
            case 6 ->  "Two lines each formed by 5 different types     ";
            case 7 ->  "Four lines each formed by 5 tiles of maximum   ";
            case 8 ->  "Four tiles of the same type in the four corners";
            case 9 ->  "Eight tiles of the same type. There’s no       ";
            case 10 -> "Five tiles of the same type forming an X.      ";
            case 11 -> "Five tiles of the same type forming a diagonal.";
            case 12 -> "Five columns of increasing or decreasing       ";
            default -> null;
        };
    }
    public String commonDescriptionSecondPart(int id) {
        return switch (id) {
            case 1 ->      "type in a 2x2 square.                          ";
            case 2, 6 ->   "of tiles.                                      ";
            case 3, 4 ->   "the same type that may differ for each group.  ";
            case 5 ->      "maximum three different types.                 ";
            case 7 ->      "three different types.                         ";
            case 8 ->      "of the bookshelf.                              ";
            case 9 ->      "restriction about the position of these tiles. ";
            case 10, 11 -> "                                               ";
            case 12 ->     "height from the left to the right.             ";
            default -> null;
        };
    }
}
