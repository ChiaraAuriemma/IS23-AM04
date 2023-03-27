package it.polimi.it.model;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public class Shelfie{

    private String player;

    private User user;

    private int commonToken1;

    private int commonToken2;

    private boolean endToken1;

    private static Tile[][] shelf;

    private final int personalCardID;

    public Shelfie(int personalCardID){

        shelf = new Tile[6][5];

        for(int row=0; row<6; row++){
            for(int column=0; column<5; column++){
                shelf[row][column] = new Tile(PossibleColors.DEFAULT);
            }
        }

        this.personalCardID = personalCardID;

        this.player = user.getNickname();

        this.endToken1 = false;

        this.commonToken1 = 0;

        this.commonToken2 = 0;

    }


    public Tile getCell(int column, int row){
        return shelf[row][column];
    }

    boolean[] chooseColumn(int count){

        boolean[] checkColumn = {false, false, false, false, false};
        int numDefault;

        for(int column=0; column<5; column++){
            numDefault = 0;
            for(int row=0; row<6; row++){
                if(shelf[row][column].getColor().equals("DEFAULT")){
                    numDefault++;
                }
            }
            if(numDefault >= count){
                checkColumn[column] = true;
            }
        }

        return checkColumn;
    }

    void addTile(int column, List<Tile> choosen){

        int numTiles = 0;

        for(int row=0; row<6; row++)
        {
            if(numTiles < choosen.size()) {
                if (shelf[row][column].getColor().equals("DEFAULT")) {
                    shelf[row][column] = choosen.get(numTiles);
                    //shelf[row][column].setRow(row);
                    //shelf[row][column].setColumn(column);
                    numTiles++;
                }
            }
        }

    }

    boolean checkEnd(){

        int count = 0;

        for(int row=0; row<6; row++){
            for(int  column=0; column<5; column++){
                if(shelf[row][column].getColor().equals("DEFAULT")){
                    count ++;
                }
            }
        }
        if(count == 0){
            endToken1 = true;
        }else {
            endToken1 = false;
        }

        return endToken1;

    }

    int possibleTiles(){

        int total = 0;
        int count;

        for(int column=0; column<5; column++){

            count = 0;

            for(int row=0; row<6; row++){
                if(shelf[row][column].getColor().equals("DEFAULT")){
                    count ++;
                    if(count >= 3){
                        return 3;
                    }
                }
            }
            if(count > total){
                total = count;
            }
        }
        return total;
    }

    int getPersonalCardID(){
        return personalCardID;
    }

    void setCommonToken1(int val){
        commonToken1 = val;
    }

    void setCommonToken2(int val){
        commonToken2 = val;
    }

    int getCommonToken1(){
        return commonToken1;
    }

    int getCommonToken2(){
        return commonToken2;
    }

    Tile[][] getShelf(){return shelf;}
}
