package it.polimi.it.model.Board;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;

import java.io.Serializable;
import java.util.*;


/**
 *  Matrix that represents the LivingRoom of the game.
 * Abstract class, gets extended by B2P, B3P and B4P, according to the number of players.
 * Contains various methods to check, fill and take tiles from the 'LivingRoom'.
 */
public abstract class Board implements Serializable {


    private static final long serialVersionUID = -8544590988740975278L;

    protected Tile[][] matrix;
    protected TilesBag bag = new TilesBag();


    /**
     * Constructor method of the abstract class
     * Gets specified in the classes: B2P, B3P and B4P
     */
    public Board() {
    }


    /**
     * Getter method
     * @return the LivingRoom's tiles matrix.
     */
    public Tile[][] getMatrix() {
        return matrix;
    }


    /**
     * Checks if a refill of the board is needed calling method checkRefill
     * Eventually refills the board, extracting random tiles from the TilesBag
     */
    public boolean refill() {
        if (!checkRefill()) {
            return false;
        }
        for (int a=0; a<9; a++){
            for (int b=0; b<9; b++){
                if(!matrix[a][b].getColor().equals("DEFAULT") && !matrix[a][b].getColor().equals("XTILE")){
                    bag.boardCleaner(matrix[a][b].getColor());
                    matrix[a][b] = new Tile(PossibleColors.DEFAULT);
                }
            }
        }
        int remainingTiles;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j].getColor().equals("DEFAULT")) {
                    remainingTiles = bag.getTotRemaining();
                    if (remainingTiles > 0) {
                        Tile tile = bag.randomTiles(i, j);
                        matrix[i][j] = tile;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Given the Board matrix, checks if a refill is needed.
     * @return a boolean value which is true if the boards needs a refill, false otherwise
     */
    public Boolean checkRefill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!matrix[i][j].getColor().equals("DEFAULT") && !matrix[i][j].getColor().equals("XTILE")) {
                    if (i < 8) {
                        if (!matrix[i + 1][j].getColor().equals("DEFAULT") && !matrix[i + 1][j].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (i > 0) {
                        if (!matrix[i - 1][j].getColor().equals("DEFAULT") && !matrix[i - 1][j].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (j < 8) {
                        if (!matrix[i][j + 1].getColor().equals("DEFAULT") && !matrix[i][j + 1].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (j > 0) {
                        if (!matrix[i][j - 1].getColor().equals("DEFAULT") && !matrix[i][j - 1].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Finds the maximum number of Tiles that can be taken from the board
     * @param maxFromShelfie is the maximum number of tiles that can be contained in the columns of the player's Shelfie
     * @return the maximum number of Tiles that can be taken from the board, this number can't be higher than maxFromShelfie
     */
    public int findMaxAdjacent(int maxFromShelfie) {

        int max=0;
        int tempSize=0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!matrix[i][j].getColor().equals("DEFAULT") && !matrix[i][j].getColor().equals("XTILE")) {
                    List<Tile> currentGroup = new ArrayList<>();
                    groupComposer(i, j, currentGroup, maxFromShelfie);
                    tempSize = currentGroup.size();
                    currentGroup.clear();
                    if(tempSize>=maxFromShelfie){
                        return maxFromShelfie;
                    }
                    if(max<tempSize){
                        max= tempSize;
                    }
                }
            }
        }
        return max;
    }


    /**
     * Builds a group of adjacent Tiles with the purpose of finding the size of the biggest take-able group
     * @param i is the row of the starting Tile
     * @param j is the column of the starting Tile
     * @param currentGroup is the List in which a Tile is appended if is take-able
     * @param maxFromShelfie is the maximum number of tiles that can be contained in the columns of the player's Shelfie
     */
    private void groupComposer(int i, int j, List<Tile> currentGroup, int maxFromShelfie) {
        int ok = 0;

        if (i < 0 || i > 8 || j < 0 || j > 8){
            return;
        }
        if(matrix[i][j].getColor().equals("DEFAULT") || matrix[i][j].getColor().equals("XTILE")) {
            return;
        }
        if(currentGroup.contains(matrix[i][j])){
            return;
        }
        if (i == 0 || j == 0 || i == 8 || j == 8) {
            ok = 1;
        } else {
            if (matrix[i - 1][j].getColor().equals("DEFAULT") || matrix[i - 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
            if (matrix[i][j - 1].getColor().equals("DEFAULT") || matrix[i][j - 1].getColor().equals("XTILE")) {
                ok = 1;
            }
            if (matrix[i + 1][j].getColor().equals("DEFAULT") || matrix[i + 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
            if (ok == 0) {
                if (matrix[i][j + 1].getColor().equals("DEFAULT") || matrix[i][j + 1].getColor().equals("XTILE")) {
                    ok = 1;
                }
            }
        }
        if (ok == 0) {
            return;
        }
        addToTriplet(i, j, currentGroup);
        if (currentGroup.size() < maxFromShelfie) {
            groupComposer(i - 1, j, currentGroup, maxFromShelfie);
            groupComposer(i + 1, j, currentGroup, maxFromShelfie);
            groupComposer(i, j - 1, currentGroup, maxFromShelfie);
            groupComposer( i, j + 1, currentGroup, maxFromShelfie);
        }
    }


    /**
     * Removes the tiles that the player chose to take from the board
     * Given the coordinate (row and column of the Tiles in the board)
     * @param chosenTiles is a list of tiles in which are stored the Tiles chosen by the player
     */
    public void removeTiles(List<Tile> chosenTiles) {
        for (Tile choosenTile : chosenTiles) {
            int row = choosenTile.getRow();
            int col = choosenTile.getColumn();
            matrix[row][col] = new Tile(row, col, PossibleColors.DEFAULT);
        }
    }


    /**
     * Given the number of Tiles that a player wants to take from the board,
     * Returns every combination of Tiles that can be taken following the rules of the game
     * Eventually calls addTilesToTriplet
     * @param size is the number of Tiles that the player wants to take from the board
     * @return a List that contains a List of Tiles
     */
    public List<List<Tile>> choosableTiles(int size) {
        List<List<Tile>> choosableTilesList = new ArrayList<>();
        boolean tripletAlreadyIn;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!matrix[i][j].getColor().equals("DEFAULT") && !matrix[i][j].getColor().equals("XTILE")) {
                    List<Tile> tripletDD = new ArrayList<>();
                    addTilesTotripletDD(i, j, tripletDD, size);
                    tripletAlreadyIn = choosableTilesList.stream().anyMatch(list -> new HashSet<>(list).containsAll(tripletDD));
                    if(!tripletAlreadyIn){
                        if (tripletDD.size() == size) {
                            choosableTilesList.add(tripletDD);
                        }
                    }
                    List<Tile> tripletRR = new ArrayList<>();
                    addTilesTotripletRR(i, j, tripletRR, size);
                    tripletAlreadyIn = choosableTilesList.stream().anyMatch(list -> new HashSet<>(list).containsAll(tripletRR));
                    if(!tripletAlreadyIn){
                        if (tripletRR.size() == size) {
                            choosableTilesList.add(tripletRR);
                        }
                    }
                    List<Tile> tripletDR = new ArrayList<>();
                    addTilesTotripletDR(i, j, tripletDR, size);
                    tripletAlreadyIn = choosableTilesList.stream().anyMatch(list -> new HashSet<>(list).containsAll(tripletDR));
                    if(!tripletAlreadyIn){
                        if (tripletDR.size() == size) {
                            choosableTilesList.add(tripletDR);
                        }
                    }
                    List<Tile> tripletRD = new ArrayList<>();
                    addTilesTotripletRD(i, j, tripletRD, size);
                    tripletAlreadyIn = choosableTilesList.stream().anyMatch(list -> new HashSet<>(list).containsAll(tripletRD));
                    if(!tripletAlreadyIn){
                        if (tripletRD.size() == size) {
                            choosableTilesList.add(tripletRD);
                        }
                    }
                    List<Tile> tripletLD = new ArrayList<>();
                    addTilesTotripletLD(i, j, tripletLD, size);
                    tripletAlreadyIn = choosableTilesList.stream().anyMatch(list -> new HashSet<>(list).containsAll(tripletLD));
                    if(!tripletAlreadyIn){
                        if (tripletLD.size() == size) {
                            choosableTilesList.add(tripletLD);
                        }
                    }
                    List<Tile> tripletDL = new ArrayList<>();
                    addTilesTotripletDL(i, j, tripletDL, size);
                    tripletAlreadyIn = choosableTilesList.stream().anyMatch(list -> new HashSet<>(list).containsAll(tripletDL));
                    if(!tripletAlreadyIn){
                        if (tripletDL.size() == size) {
                            choosableTilesList.add(tripletDL);
                        }
                    }
                }
            }
        }
        return choosableTilesList;
    }


    /**
     * Checks if the Tile at the given row and column is take-able, if so, the tile is added to the list
     * @param row is the row of the tile that has to be checked
     * @param column is the column of the tile that has to be checked
     * @param triplet is the current list, used to check if the current tile is already present in the list
     * @return true if the tile has to be added to the list, false otherwise
     */
    private boolean tileChecker(int row, int column, List<Tile> triplet){

        if (row < 0 || row > 8 || column < 0 || column > 8 || matrix[row][column].getColor().equals("DEFAULT") || matrix[row][column].getColor().equals("XTILE")) {
            return false;
        }
        if(triplet.contains(matrix[row][column])){
            return false;
        }
        if (row == 0 || column == 0 || row == 8 || column == 8) {
            return true;
        }
        if (matrix[row - 1][column].getColor().equals("DEFAULT") || matrix[row - 1][column].getColor().equals("XTILE")) {
            return true;
        }
        if (matrix[row][column - 1].getColor().equals("DEFAULT") || matrix[row][column - 1].getColor().equals("XTILE")) {
            return true;
        }
        if (matrix[row + 1][column].getColor().equals("DEFAULT") || matrix[row + 1][column].getColor().equals("XTILE")) {
            return true;
        }
        if (matrix[row][column + 1].getColor().equals("DEFAULT") || matrix[row][column + 1].getColor().equals("XTILE")) {
            return true;
        }
        return false;
    }


    /**
     * Adds the tile of the board at the given row and column to the list
     * @param i is the row of the tile that has to be added
     * @param j is the column of the tile that has to be added
     * @param triplet is the list in which the list has to be added
     */
    private void addToTriplet(int i, int j, List<Tile> triplet) {
        switch (matrix[i][j].getColor()) {
            case "BLUE":
                triplet.add(new Tile(i, j, PossibleColors.BLUE));
                break;
            case "GREEN":
                triplet.add(new Tile(i, j, PossibleColors.GREEN));
                break;
            case "CYAN":
                triplet.add(new Tile(i, j, PossibleColors.CYAN));
                break;
            case "YELLOW":
                triplet.add(new Tile(i, j, PossibleColors.YELLOW));
                break;
            case "WHITE":
                triplet.add(new Tile(i, j, PossibleColors.WHITE));
                break;
            case "PINK":
                triplet.add(new Tile(i, j, PossibleColors.PINK));
                break;
        }
    }


    /**
     * Method that finds the successive tile to be added to the triplet,
     * traversing the board with a Down->Down movement, starting from
     * @param i row
     * @param j column
     * @param triplet list of tiles in which the new tile is added
     * @param size maximum size of the list.
     */
    private void addTilesTotripletDD(int i, int j, List<Tile> triplet, int size) {
        if(tileChecker(i, j, triplet) && triplet.size()<size){
            addToTriplet(i, j, triplet);
            if (triplet.size() < size) {
                addTilesTotripletDD(i + 1, j, triplet, size);
            }
        }
    }


    /**
     * Method that finds the successive tile to be added to the triplet,
     * traversing the board with a Right->Right movement, starting from
     * @param i row
     * @param j column
     * @param triplet list of tiles in which the new tile is added
     * @param size maximum size of the list.
     */
    private void addTilesTotripletRR(int i, int j, List<Tile> triplet, int size) {
        if(tileChecker(i, j, triplet) && triplet.size()<size){
            addToTriplet(i, j, triplet);
            if (triplet.size() < size) {
                addTilesTotripletRR(i, j+1, triplet, size);
            }
        }
    }


    /**
     * Method that finds the successive tile to be added to the triplet,
     * traversing the board with a Right->Down movement, starting from
     * @param i row
     * @param j column
     * @param triplet list of tiles in which the new tile is added
     * @param size maximum size of the list.
     */
    private void addTilesTotripletRD(int i, int j, List<Tile> triplet, int size) {
        if(tileChecker(i, j, triplet) && triplet.size()<size){
            addToTriplet(i, j, triplet);
            if (triplet.size() < size) {
                j=j+1;
                if(tileChecker(i, j, triplet) && triplet.size()<size){
                    addToTriplet(i, j, triplet);
                    if (triplet.size() < size) {
                        i=i+1;
                        if(tileChecker(i, j, triplet) && triplet.size()<size){
                            addToTriplet(i, j, triplet);
                        }
                    }
                }
            }
        }
    }


    /**
     * Method that finds the successive tile to be added to the triplet,
     * traversing the board with a Down->Right movement, starting from
     * @param i row
     * @param j column
     * @param triplet list of tiles in which the new tile is added
     * @param size maximum size of the list.
     */
    private void addTilesTotripletDR(int i, int j, List<Tile> triplet, int size) {
        if(tileChecker(i, j, triplet) && triplet.size()<size){
            addToTriplet(i, j, triplet);
            if (triplet.size() < size) {
                i=i+1;
                if(tileChecker(i, j, triplet) && triplet.size()<size){
                    addToTriplet(i, j, triplet);
                    if (triplet.size() < size) {
                        j=j+1;
                        if(tileChecker(i, j, triplet) && triplet.size()<size){
                            addToTriplet(i, j, triplet);
                        }
                    }
                }
            }
        }
    }


    /**
     * Method that finds the successive tile to be added to the triplet,
     * traversing the board with a Left->Down movement, starting from
     * @param i row
     * @param j column
     * @param triplet list of tiles in which the new tile is added
     * @param size maximum size of the list.
     */
    private void addTilesTotripletLD(int i, int j, List<Tile> triplet, int size) {
        if(tileChecker(i, j, triplet) && triplet.size()<size){
            addToTriplet(i, j, triplet);
            if (triplet.size() < size) {
                i=i-1;
                if(tileChecker(i, j, triplet) && triplet.size()<size){
                    addToTriplet(i, j, triplet);
                    if (triplet.size() < size) {
                        j=j+1;
                        if(tileChecker(i, j, triplet) && triplet.size()<size){
                            addToTriplet(i, j, triplet);
                        }
                    }
                }
            }
        }
    }


    /**
     * Method that finds the successive tile to be added to the triplet,
     * traversing the board with a Down->Left movement, starting from
     * @param i row
     * @param j column
     * @param triplet list of tiles in which the new tile is added
     * @param size maximum size of the list.
     */
    private void addTilesTotripletDL(int i, int j, List<Tile> triplet, int size) {
        if(tileChecker(i, j, triplet) && triplet.size()<size){
            addToTriplet(i, j, triplet);
            if (triplet.size() < size) {
                i=i+1;
                if(tileChecker(i, j, triplet) && triplet.size()<size){
                    addToTriplet(i, j, triplet);
                    if (triplet.size() < size) {
                        j=j-1;
                        if(tileChecker(i, j, triplet) && triplet.size()<size){
                            addToTriplet(i, j, triplet);
                        }
                    }
                }
            }
        }
    }
}