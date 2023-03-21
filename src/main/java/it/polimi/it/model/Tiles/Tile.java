package it.polimi.it.model.Tiles;

public class Tile {
    PossibleColors color = null;
    int boardRow;
    int boardColumn;

    public Tile() {
        color = PossibleColors.DEFAULT;
        boardRow = -1;
        boardColumn = -1;
    }

    public Tile(int a, int b, PossibleColors Color){
        boardRow = a;
        boardColumn = b;
        this.color = Color;
    }
    public Tile(PossibleColors Color){
        boardRow = -1;
        boardColumn = -1;
        this.color = Color;
    }

   public String getColor(){
        return color.getColor();
    }


    public int getRow(){
        return this.boardRow;
    }
    public int getColumn(){
        return this.boardColumn;
    }
}
