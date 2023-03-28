package it.polimi.it.model;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;

import java.util.HashMap;
import java.util.List;

public class Shelfie{

    private int commonToken1;

    private int commonToken2;

    private boolean endToken1;

    private static Tile[][] shelf;

    public Shelfie(){

        shelf = new Tile[6][5];

        for(int row=0; row<6; row++){
            for(int column=0; column<5; column++){
                shelf[row][column] = new Tile(PossibleColors.DEFAULT);
            }
        }

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
                    shelf[row][column] = new Tile(row, column, PossibleColors.valueOf(choosen.get(numTiles).getColor()));
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

    int checkAdjacentsPoints() {

        int total = 0;

        boolean[][] visited = new boolean[6][5];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                if (!visited[row][col]) {
                    int count = searchAdjacents(shelf, visited, directions, row, col, shelf[row][col].getColor());

                    if (count >= 3) {
                        switch (count) {
                            case 3:
                                total = total + 2;
                                break;
                            case 4:
                                total = total + 3;
                                break;
                            case 5:
                                total = total + 5;
                                break;
                            default:
                                total = total + 8;
                                break;
                        }
                    }
                }
            }


        }
        return total;
    }

    private int searchAdjacents (Tile[][] shelf, boolean[][] visited, int[][] directions, int row, int col, String color) {

        visited[row][col] = true;
        int count = 1;

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < 6 && newCol >= 0 && newCol < 5 &&
                    !visited[newRow][newCol] && shelf[newRow][newCol].getColor().equals(color)) {
                count += searchAdjacents(shelf, visited, directions, newRow, newCol, color);
            }
        }

        return count;
    }
}


