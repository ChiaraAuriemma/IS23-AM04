package it.polimi.it.model.Tiles;

/**
 * Enumeration used to define all possible colors of the tiles
 */
public enum PossibleColors {
    BLUE("BLUE"),
    CYAN("CYAN"),
    GREEN("GREEN"),
    YELLOW("YELLOW"),
    WHITE("WHITE"),
    PINK("PINK"),
    DEFAULT("DEFAULT"),
    XTILE("XTILE");

    private final String color;


    PossibleColors(String color) {
        this.color = color;
    }


    /**
     * Getter method
     * @return the color specified by the enumeration
     */
    public String getColor() {
        return color;
    }
}