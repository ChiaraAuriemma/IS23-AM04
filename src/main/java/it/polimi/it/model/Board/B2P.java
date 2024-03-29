package it.polimi.it.model.Board;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import java.io.Serializable;


/**
 * 9x9 Tiles matrix that represents the configuration of the Board when there are 2 players
 */
public class B2P extends Board implements Serializable {

    private static final long serialVersionUID = 3553776396475547822L;


    /**
     * Constructor of the board for 2 player
     * Initializes a 9x9 matrix with the X or colored Tiles where needed
     */
    public B2P(){
        matrix = new Tile[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(i==0 || i==8){
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==1 && j<3) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==2 && j<3) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==1 && j>4) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==2 && j>5) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==3 && j<2) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==3 && j==8) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==4 && j==0) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==4 && j==8) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==5 && j==0) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==5 && j>6) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==6 && j<3) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==6 && j>5) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==7 && j<4) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==7 && j>5) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else{
                    Tile tile = bag.randomTiles(i, j);
                    matrix[i][j] = tile;
                }
            }
        }
    }
}