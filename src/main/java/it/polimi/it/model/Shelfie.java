package it.polimi.it.model;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

/**
 *Matrix that represent the BookShelf in the game
*/

public class Shelfie{

    private int commonToken1;
    private int commonToken2;
    private boolean endToken1;
    private static Tile[][] shelf;

    /**
     * Constructor method that instances a 6x5 matrix with a DEFAULT type tile in each position and instances the other parameters
     */

    public Shelfie(){

        shelf = new Tile[6][5];
        for(int row=0; row<6; row++){
            for(int column=0; column<5; column++){
                shelf[row][column] = new Tile(row, column, PossibleColors.DEFAULT);
            }
        }
        this.endToken1 = false;
        this.commonToken1 = 0;
        this.commonToken2 = 0;
    }


    public Tile getCell(int column, int row){
        return shelf[row][column];
    }

    /**
     * Method that check which column could be chosen to insert the tiles
     * @param count is the number of tiles chosen by the player
     * @return a boolean array where "true" means that there is enough DEAFULT tiles in that column to insert the chosen tiles
     */
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

    /**
     * Method that add the chosen tiles in the chosen column
     * @param column is the column chosen by the player to add the tiles
     * @param chosen is the list of tiles to add
     * @return a boolean that is true if the BookShelf is full
     */

    boolean addTile(int column, List<Tile> chosen){

        int numTiles = 0;
        for(int row=0; row<6; row++) {
            if(numTiles < chosen.size()) {
                if (shelf[row][column].getColor().equals("DEFAULT")) {
                        shelf[row][column] = new Tile(row, column, PossibleColors.valueOf(chosen.get(numTiles).getColor()));
                        numTiles++;
                }
            }
        }

        return checkEnd();
    }

    /**
     * Method that check if the BookShelf of a player is full
     * @return a boolean that represents if the BookShelf is full or not
     */
    public boolean checkEnd(){
        for(int row=0; row<6; row++){
            for(int  column=0; column<5; column++){
                if(shelf[row][column].getColor().equals("DEFAULT")){
                    endToken1 = false;
                    return false;
                }
            }
        }
        endToken1 = true;
        return true;
    }

    /**
     * Method that checks the maximum number of tiles that could be chosen by a player using the number of DEFAULT tiles in each column
     * @return the maximum value of tiles that can be chosen
     */
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

    public Tile[][] getShelf(){return shelf;}

    /**
     * Method that checks how many groups of adjacents and same type tiles there are in the BookShelf, it also counts how many point for each group
     * @return total of points based on the sum of the different groups
     */
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

    /**
     * Method used by checkAdjacentsPoints() method to do a recursive count of adjacents tiles
     * @param shelf is the matrix that represent the BookShelf
     * @param visited is a 2 dimensional array used to check which tiles is already counted in the total
     * @param directions is a vector used to do the next recursive step
     * @param row is the row index of the tile
     * @param col is the column index of the tile
     * @param color is the type of the tile
     * @return the count of the adjacents tiles of the same type
     */
    private int searchAdjacents (Tile[][] shelf, boolean[][] visited, int[][] directions, int row, int col, String color) {

        visited[row][col] = true;
        int count = 1;

        for (int[] dir : directions) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];

            if (nextRow >= 0 && nextRow < 6 && nextCol >= 0 && nextCol < 5 &&
                    !visited[nextRow][nextCol] && shelf[nextRow][nextCol].getColor().equals(color) &&
                    !shelf[nextRow][nextCol].getColor().equals("DEFAULT") &&
                    !shelf[nextRow][nextCol].getColor().equals("XTILE")) {
                count += searchAdjacents(shelf, visited, directions, nextRow, nextCol, color);
            }
        }
        return count;
    }


    public void setShelf(Tile[][] matrix){
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                shelf[i][j] = new Tile(i, j, PossibleColors.valueOf(matrix[i][j].getColor()));
            }
        }
    }
}


