package it.polimi.it.model;

public class Tile {
    PossibleColors Color = null;

    public Tile() {
        Color = PossibleColors.DEFAULT;
    }

    public Tile(PossibleColors Color){
        this.Color = Color;
    }

   String getColor(){
        return Color.getColor();
    }
}
