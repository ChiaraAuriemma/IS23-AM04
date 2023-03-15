package it.polimi.it.model;

public class B2P extends Board{

    public Tile[][] Matrix;

    //constructor
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
                    Matrix[i][j] = TilesBag.RandomTiles();
                }


            }
        }
    }
}
