package it.polimi.it.model.Board;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;

public class B4P extends Board{

    public Tile[][] matrix;

    public B4P(){
        matrix = new Tile[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if (i==0 && j<3) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==0 && j>4) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==8 && j<4) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==8 && j>5) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==1 && j<3) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==2 && j<2) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==1 && j>5) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==2 && j>6) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==3 && j==0) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==5 && j==8) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==6 && j<2) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==6 && j>6) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==7 && j<3) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else if (i==7 && j>5) {
                    matrix[i][j] = new Tile(PossibleColors.XTILE);
                }else{
                    matrix[i][j] = TilesBag.randomTiles(i, j);
                }
            }
        }
    }
}
