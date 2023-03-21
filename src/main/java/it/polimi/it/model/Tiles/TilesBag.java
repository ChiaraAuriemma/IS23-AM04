package it.polimi.it.model.Tiles;

import java.util.HashMap;
import java.util.Random;

public class TilesBag {

    public static HashMap<PossibleColors, Integer> remainingTiles = new HashMap<>();

    public static int totalRemaining;

    static Random rand = new Random();

    //constructor
    public TilesBag(){
        remainingTiles.put(PossibleColors.CYAN, 22);
        remainingTiles.put(PossibleColors.BLUE, 22);
        remainingTiles.put(PossibleColors.GREEN, 22);
        remainingTiles.put(PossibleColors.PINK, 22);
        remainingTiles.put(PossibleColors.YELLOW, 22);
        remainingTiles.put(PossibleColors.WHITE, 22);
        totalRemaining = 132;
    }

    public static int getTotRemaining(){
        return totalRemaining;
    }

    public static Tile randomTiles(int row, int col){
        int random_color = 0;
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
}
