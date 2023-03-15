package it.polimi.it.model;

import java.util.Random;
public class TilesBag {
    public int NremainingBlue;
    public int NremainingCyan;
    public int NremainingGreen;
    public int NremainingYellow;
    public int NremainingWhite;
    public int NremainingPink;
    public int NtotalRemaining;


    Random rand = new Random();

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

    public int getTotRemaining(){
        return NtotalRemaining;
    }

    public Tile RandomTiles(){
        int random_color = 0;
        while(true) {
            random_color = rand.nextInt(6);
            switch (random_color) {

                case 0:
                    if (NremainingBlue > 0) {
                        NremainingBlue--;
                        NtotalRemaining--;
                        return new Tile(PossibleColors.BLUE);
                    }
                    break;

                case 1:
                    if (NremainingCyan > 0) {
                        NremainingCyan--;
                        NtotalRemaining--;
                        return new Tile(PossibleColors.CYAN);
                    }
                    break;

                case 2:
                    if (NremainingGreen > 0) {
                        NremainingGreen--;
                        NtotalRemaining--;
                        return new Tile(PossibleColors.GREEN);
                    }
                    break;

                case 3:
                    if (NremainingYellow > 0) {
                        NremainingYellow--;
                        NtotalRemaining--;
                        return new Tile(PossibleColors.YELLOW);
                    }
                    break;

                case 4:
                    if (NremainingPink > 0) {
                        NremainingPink--;
                        NtotalRemaining--;
                        return new Tile(PossibleColors.PINK);
                    }
                    break;

                case 5:
                    if (NremainingWhite > 0) {
                        NremainingWhite--;
                        NtotalRemaining--;
                        return new Tile(PossibleColors.WHITE);
                    }
                    break;
                // default: return new Tile(PossibleColors.DEFAULT);
            }
        }
    }
}
