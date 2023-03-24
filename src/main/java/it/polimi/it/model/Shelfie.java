package it.polimi.it.model;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;

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


    public String getCell(int column, int row){
        return shelf[row][column].getColor();
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

    void addTile(int column, String[] order, int count){

        int numTiles = 0;

        for(int row=0; row<6; row++)
        {
            if(numTiles < order.length) {
                if (shelf[row][column].getColor().equals("DEFAULT")) {
                    shelf[row][column] = new Tile(PossibleColors.valueOf(order[numTiles]));
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
}
