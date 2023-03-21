package it.polimi.it.model.Board;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;

public class B2P extends Board{

    public Tile[][] Matrix;

    /**
     * Constructor of the board for 2 player
     * Initializes a 9x9 matrix with the X or colored Tiles where needed
     */

    public B2P(){
        Matrix = new Tile[9][9];

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(i==0 || i==8){
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==1 && j<3) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==2 && j<3) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==1 && j>4) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==2 && j>5) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==3 && j<2) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==3 && j==8) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==4 && j==0) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==4 && j==8) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==5 && j==0) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==5 && j>6) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==6 && j<3) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==6 && j>5) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==7 && j<4) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==7 && j>5) {
                    Matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else{
                    Tile tile = TilesBag.RandomTiles(i, j);
                    Matrix[i][j] = tile;
                }
            }
        }
    }
}
