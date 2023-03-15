package it.polimi.it.model;

public class Tile {
    static PossibleColors Color = null;

    public Tile() {
        Color = PossibleColors.DEFAULT;
    }

    public Tile(PossibleColors a){
        this.Color = a;
    }

   // String getColor(Tile){
     //   return Tile.Color.name();
    //}
}
