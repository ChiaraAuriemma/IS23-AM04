package it.polimi.it.model;

public enum PossibleColors {
    BLUE("BLUE"),
    CYAN("CYAN"),
    GREEN("GREEN"),
    YELLOW("YELLOW"),
    WHITE("WHITE"),
    PINK("PINK"),

    DEFAULT("DEFAULT"),
    XTILE("XTILE");

    private final String Color;

    PossibleColors(String Color) {
        this.Color = Color;
    }

    public String getColor() {
        return Color;
    }


}
