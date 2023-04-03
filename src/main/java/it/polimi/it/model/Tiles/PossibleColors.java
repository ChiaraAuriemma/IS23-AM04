package it.polimi.it.model.Tiles;

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


    //bro è un costruttore
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
}// si potrebbe usare tis.name(); che dovrebbe ritornare la stessa stringa col nome del colore
    // così potrebbe non servire il parametro stringa!