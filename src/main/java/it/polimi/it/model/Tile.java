package it.polimi.it.model;

public class Tile {
    private final PossibleColors Color;

    public Tile() {
        Color = PossibleColors.DEFAULT;
    }

    public Tile(PossibleColors a){
        this.Color = a;
    }

    public PossibleColors getColor() {
        return Color;
    }
}
