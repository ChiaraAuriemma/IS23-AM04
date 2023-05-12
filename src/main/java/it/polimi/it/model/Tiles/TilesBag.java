package it.polimi.it.model.Tiles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

public class TilesBag implements Serializable{

    private static final long serialVersionUID = -6550378525445784789L;
    /**
     * Hashmap used to store the number of the remaining tiles of each color
     */
    private static HashMap<PossibleColors, Integer> remainingTiles = new HashMap<>();

    /**
     * Stores the total number of the remaining Tiles
     */
    private int totalRemaining;




    /**
     * Constructor method of the TilesBag
     * Initializes totalRemaining to 132, which is the total number of tiles in the bag when the bag is full
     * Initializes an HashMap that uses the PossibleColors Enumeration as Key and stores, for each color, 22,
     *      which is the maximum number of tiles of the same color that can be used during a game
     */
    public TilesBag(){
        remainingTiles.put(PossibleColors.CYAN, 22);
        remainingTiles.put(PossibleColors.BLUE, 22);
        remainingTiles.put(PossibleColors.GREEN, 22);
        remainingTiles.put(PossibleColors.PINK, 22);
        remainingTiles.put(PossibleColors.YELLOW, 22);
        remainingTiles.put(PossibleColors.WHITE, 22);
        totalRemaining = 132;
    }



    /**
     * Getter method
     * @return the total number of the tiles that are still in the bag
     */
    public int getTotRemaining(){
        return totalRemaining;
    }


    /**
     * Extracts a random color and creates a Tile of that color
     * @param row is the row parameter to be given to the constructor of the Tile
     * @param col is the column parameter to be given to the constructor of the Tile
     * @return the new Tile that got created
     */
    public Tile randomTiles(int row, int col){
        int random_color;

        Random rand = new Random();

        if (totalRemaining==0){
            return new Tile(PossibleColors.DEFAULT);
        }

        while(true) {
            random_color = rand.nextInt(6);
            switch (random_color) {

                case 0:
                    if (remainingTiles.get(PossibleColors.BLUE) > 0) {
                        remainingTiles.put(PossibleColors.BLUE, remainingTiles.get(PossibleColors.BLUE)-1);
                        totalRemaining--;
                        return new Tile(row, col, PossibleColors.BLUE);
                    }
                    break;

                case 1:
                    if (remainingTiles.get(PossibleColors.CYAN) > 0) {
                        remainingTiles.put(PossibleColors.CYAN, remainingTiles.get(PossibleColors.CYAN)-1);
                        totalRemaining--;
                        return new Tile(row, col, PossibleColors.CYAN);
                    }
                    break;

                case 2:
                    if (remainingTiles.get(PossibleColors.GREEN) > 0) {
                        remainingTiles.put(PossibleColors.GREEN, remainingTiles.get(PossibleColors.GREEN)-1);
                        totalRemaining--;
                        return new Tile(row, col, PossibleColors.GREEN);
                    }
                    break;

                case 3:
                    if (remainingTiles.get(PossibleColors.YELLOW) > 0) {
                        remainingTiles.put(PossibleColors.YELLOW, remainingTiles.get(PossibleColors.YELLOW)-1);
                        totalRemaining--;
                        return new Tile(row, col, PossibleColors.YELLOW);
                    }
                    break;

                case 4:
                    if (remainingTiles.get(PossibleColors.PINK) > 0) {
                        remainingTiles.put(PossibleColors.PINK, remainingTiles.get(PossibleColors.PINK)-1);
                        totalRemaining--;
                        return new Tile(row, col, PossibleColors.PINK);
                    }
                    break;

                case 5:
                    if (remainingTiles.get(PossibleColors.WHITE) > 0) {
                        remainingTiles.put(PossibleColors.WHITE, remainingTiles.get(PossibleColors.WHITE)-1);
                        totalRemaining--;
                        return new Tile(row, col, PossibleColors.WHITE);
                    }
                    break;
            }
        }
    }

    /**
     * Given a color of a tile, puts it back in the bag.
     * This method is used when a refill is needed and there are still some tiles on the board.
     * @param colorRetrieved is the color of the tile that needs to be put back in the bag from the Board
     */
    public void boardCleaner(String colorRetrieved){
        switch (colorRetrieved){
            case "BLUE":
                remainingTiles.put(PossibleColors.BLUE, remainingTiles.get(PossibleColors.BLUE)+1);
                break;
            case "GREEN":
                remainingTiles.put(PossibleColors.GREEN, remainingTiles.get(PossibleColors.GREEN)+1);
                break;
            case "CYAN":
                remainingTiles.put(PossibleColors.CYAN, remainingTiles.get(PossibleColors.CYAN)+1);
                break;
            case "YELLOW":
                remainingTiles.put(PossibleColors.YELLOW, remainingTiles.get(PossibleColors.YELLOW)+1);
                break;
            case "WHITE":
                remainingTiles.put(PossibleColors.WHITE, remainingTiles.get(PossibleColors.WHITE)+1);
                break;
            case "PINK":
                remainingTiles.put(PossibleColors.PINK, remainingTiles.get(PossibleColors.PINK)+1);
                break;
        }
        totalRemaining++;
    }
}