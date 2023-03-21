package it.polimi.it.model.Tiles;

import java.util.Random;

public class TilesBag {
    public static int NremainingBlue;
    public static int NremainingCyan;
    public static int NremainingGreen;
    public static int NremainingYellow;
    public static int NremainingWhite;
    public static int NremainingPink;
    public static int NtotalRemaining;


    static Random rand = new Random();

    //constructor
    public TilesBag(){
        NremainingBlue = 22;
        NremainingCyan = 22;
        NremainingGreen = 22;
        NremainingPink = 22;
        NremainingYellow = 22;
        NremainingWhite = 22;
        NtotalRemaining = 132;
    }

    public static int getTotRemaining(){
        return NtotalRemaining;
    }

    public static Tile RandomTiles(int row, int col){
        int random_color = 0;
        while(true) {
            random_color = rand.nextInt(6);
            switch (random_color) {

                case 0:
                    if (NremainingBlue > 0) {
                        NremainingBlue--;
                        NtotalRemaining--;
                        return new Tile(row, col, PossibleColors.BLUE);
                    }
                    break;

                case 1:
                    if (NremainingCyan > 0) {
                        NremainingCyan--;
                        NtotalRemaining--;
                        return new Tile(row, col, PossibleColors.CYAN);
                    }
                    break;

                case 2:
                    if (NremainingGreen > 0) {
                        NremainingGreen--;
                        NtotalRemaining--;
                        return new Tile(row, col, PossibleColors.GREEN);
                    }
                    break;

                case 3:
                    if (NremainingYellow > 0) {
                        NremainingYellow--;
                        NtotalRemaining--;
                        return new Tile(row, col, PossibleColors.YELLOW);
                    }
                    break;

                case 4:
                    if (NremainingPink > 0) {
                        NremainingPink--;
                        NtotalRemaining--;
                        return new Tile(row, col, PossibleColors.PINK);
                    }
                    break;

                case 5:
                    if (NremainingWhite > 0) {
                        NremainingWhite--;
                        NtotalRemaining--;
                        return new Tile(row, col, PossibleColors.WHITE);
                    }
                    break;
            }
        }
    }
}