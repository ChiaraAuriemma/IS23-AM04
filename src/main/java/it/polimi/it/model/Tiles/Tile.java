package it.polimi.it.model.Tiles;

public class Tile {
    PossibleColors Color = null;
    int BoardRow;
    int BoardColumn;

    public Tile() {
        Color = PossibleColors.DEFAULT;
        BoardRow = -1;
        BoardColumn = -1;
    }

    public Tile(int a, int b, PossibleColors Color){
        BoardRow = a;
        BoardColumn = b;
        this.Color = Color;
    }
    public Tile(PossibleColors Color){
        BoardRow = -1;
        BoardColumn = -1;
        this.Color = Color;
    }

   public String getColor(){
        return Color.getColor();
    }
}
