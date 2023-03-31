package it.polimi.it.model.Board;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public abstract class Board {


    /**
     * Matrix that represents the LivingRoom of the game
     */
    protected Tile[][] matrix;
    protected TilesBag bag = new TilesBag();


    /**
     * Constructor method of the abstract class
     * Gets specified in the classes: B2P, B3P and B4P
     */
    public Board() {

    }


    /**
     * Checks if a refill of the board is needed calling method checkRefill
     * Eventually refills the board, extracting random tiles from the TilesBag
     */
    public void refill() {
        if (!checkRefill()) {
            return;
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
    }


    /**
     * Given the Board matrix, checks if a refill is needed.
     * @return a boolean value which is true if the boards needs a refill, false otherwise
     */
    public Boolean checkRefill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //Tile til = matrix[i][j];
                if (!matrix[i][j].getColor().equals("DEFAULT") && !matrix[i][j].getColor().equals("XTILE")) {
                    // eseguo i controlli solo se la tessera è colorata:


                    if (i < 8) {
                        if (!matrix[i + 1][j].getColor().equals("DEFAULT") && !matrix[i + 1][j].getColor().equals("XTILE")) {
                            return false;//dato che ho trovato un'altra tessera colorata a fianco di una tessera colorata, il refill non è necessario
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
                    if (i > 0) {
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
     * @param currentGruop is the List in which a Tile is appended if is take-able
     * @param maxFromShelfie is the maximum number of tiles that can be contained in the columns of the player's Shelfie
     */
    private void groupComposer(int i, int j, List<Tile> currentGruop, int maxFromShelfie) {
        int ok = 0;
        if(currentGruop.contains(matrix[i][j])){
            return;
        }
        if (i < 0 || i > 8 || j < 0 || j > 8 || matrix[i][j].getColor().equals("DEFAULT") || matrix[i][j].getColor().equals("XTILE")) {
            return;
        }
        if (i == 0 || j == 0 || i == 8 || j == 8) {
            ok = 1;
        } else if (i >= 1) {
            if (matrix[i - 1][j].getColor().equals("DEFAULT") || matrix[i - 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (j >= 1) {
            if (matrix[i][j - 1].getColor().equals("DEFAULT") || matrix[i][j - 1].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (i < 8) {
            if (matrix[i + 1][j].getColor().equals("DEFAULT") || matrix[i + 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (j < 8 && ok == 0) {
            if (matrix[i][j + 1].getColor().equals("DEFAULT") || matrix[i][j + 1].getColor().equals("XTILE")) {
                ok = 1;
            }
        }
        if (ok == 0) {
            return;
        }
        switch (matrix[i][j].getColor()) {
            case "BLUE":
                currentGruop.add(new Tile(i, j, PossibleColors.BLUE));
                break;
            case "GREEN":
                currentGruop.add(new Tile(i, j, PossibleColors.GREEN));
                break;
            case "CYAN":
                currentGruop.add(new Tile(i, j, PossibleColors.CYAN));
                break;
            case "YELLOW":
                currentGruop.add(new Tile(i, j, PossibleColors.YELLOW));
                break;
            case "WHITE":
                currentGruop.add(new Tile(i, j, PossibleColors.WHITE));
                break;
            case "PINK":
                currentGruop.add(new Tile(i, j, PossibleColors.PINK));
                break;
        }
        if (currentGruop.size() < maxFromShelfie) {
            groupComposer(i - 1, j, currentGruop, maxFromShelfie);
            groupComposer(i + 1, j, currentGruop, maxFromShelfie);
            groupComposer(i, j - 1, currentGruop, maxFromShelfie);
            groupComposer( i, j + 1, currentGruop, maxFromShelfie);
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
            matrix[row][col] = new Tile(PossibleColors.DEFAULT);
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
                    List<Tile> triplet = new ArrayList<>();
                    addTilesTotriplet(i, j, triplet, size);

                    tripletAlreadyIn = choosableTilesList.stream().anyMatch(list -> new HashSet<>(list).containsAll(triplet));
                    if(!tripletAlreadyIn){
                        if (triplet.size() == size) {
                            choosableTilesList.add(triplet);
                        }
                    }
                }
            }
        }
        return choosableTilesList;
    }



    /**
     * Helper method that can be called only by choosableTiles
     * Adds to the List of tiles 'triplet' the take-able tiles following the rules of the game
     * This method is recursive and calls itself on every direction departing from the starting cell, if the triplet isn't completed
     * @param i is the row of the starting Tile
     * @param j is the column of the starting Tile
     * @param triplet is the List in which a Tile is appended if is take-able
     * @param size is the maximum size of the List 'triplet', once is reached the List can't be expanded and the traversing of the board stops
     */
    private void addTilesTotriplet(int i, int j, List<Tile> triplet, int size) {
        int ok = 0;
        //Tile current = new Tile(i, j, matrix[i][j].getColor())
        if(triplet.contains(matrix[i][j])){//se questa Tile fa già parte della lista Triplet non ha senso farci dei controlli
            return;
        }
        if (i < 0 || i > 8 || j < 0 || j > 8 || matrix[i][j].getColor().equals("DEFAULT") || matrix[i][j].getColor().equals("XTILE")) {
            return;
        }
        if (i == 0 || j == 0 || i == 8 || j == 8) {
            ok = 1;
        } else if (i >= 1) {
            if (matrix[i - 1][j].getColor().equals("DEFAULT") || matrix[i - 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (j >= 1) {
            if (matrix[i][j - 1].getColor().equals("DEFAULT") || matrix[i][j - 1].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (i < 8) {
            if (matrix[i + 1][j].getColor().equals("DEFAULT") || matrix[i + 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (j < 8 && ok == 0) {
            if (matrix[i][j + 1].getColor().equals("DEFAULT") || matrix[i][j + 1].getColor().equals("XTILE")) {
                ok = 1;
            }
        }
        if (ok == 0) {
            return;
        }
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
        if (triplet.size() < size) {
            addTilesTotriplet(i - 1, j, triplet, size);
            addTilesTotriplet(i + 1, j, triplet, size);
            addTilesTotriplet(i, j - 1, triplet, size);
            addTilesTotriplet( i, j + 1, triplet, size);
        }
    }
}
