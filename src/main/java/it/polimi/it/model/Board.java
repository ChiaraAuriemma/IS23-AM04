package it.polimi.it.model;
import java.util.Scanner;


public abstract class Board {
    //The maximum dimension of the board is a 9x9 matrix
    public Tile[][] Matrix;

    public Board(){

    }

    //Checks if a refill of the board is needed; eventually refills the board
    void Refill(){
        if(!CheckRefill()){
            return;
        }
        int NremainingTiles=132;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(Matrix[i][j].getColor().equals("DEFAULT")){
                    NremainingTiles=TilesBag.getTotRemaining();
                    if(NremainingTiles>0) {
                        Tile tile = TilesBag.RandomTiles();
                        Matrix[i][j] = tile;
                    }
                }
            }
        }
        return;
    }

    private Boolean CheckRefill(){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                Tile til = Matrix[i][j];
                if(!til.getColor().equals("DEFAULT") && !til.getColor().equals("XTILE")){
                    //you get in this branch if the tile in position i;j is a 'Color' tile;
                    if(i<8){
                        if(!Matrix[i+1][j].getColor().equals("DEFAULT") && !Matrix[i+1][j].getColor().equals("XTILE")){
                           return false;
                        }
                    }
                    if (i>0){
                        if(!Matrix[i-1][j].getColor().equals("DEFAULT") && !Matrix[i-1][j].getColor().equals("XTILE")){
                            return false;
                        }
                    }
                    if (j<8){
                        if(!Matrix[i][j+1].getColor().equals("DEFAULT") && !Matrix[i][j+1].getColor().equals("XTILE")){
                            return false;
                        }
                    }
                    if(i>0){
                        if(!Matrix[i][j-1].getColor().equals("DEFAULT") && !Matrix[i][j-1].getColor().equals("XTILE")){
                            return false;
                        }
                    }
                }
            }
        }
        //you get to this point of the method only if all the 'color' tiles don't have any adjacent
        return true;

    }

    Tile[] ChooseTiles(){
        Tile[] ChosenTiles;
        ChosenTiles = new Tile[4];
        int Number=0;

        System.out.println("How many tiles would you like to get?");
        Scanner s = new Scanner(System.in);
        Number = s.nextInt();
        while(Number<0 || Number>4){
            Number = s.nextInt();
            if (Number<=0 || Number>=4){
                System.out.println("Retry!");
            }
        }

        if (Number>0 && Number<4){
            System.out.println("Now choose " + Number + " adjacent tiles from the board!");
        }

        for (int i=3; i>3-Number; i--){
            ChosenTiles[i] = new Tile(PossibleColors.DEFAULT);
        }
        //Parte per la vera scelta:
            //individuare a seconda di quante tiles si vuole prendere in totale, le "prendibili"
            //cioè tutte quelle (coppie\triplette\singole tile) che sono tra loro adiacenti e con almeno un lato libero

        return ChosenTiles;
    }
}
