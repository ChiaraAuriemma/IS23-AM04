package it.polimi.it.model.Tiles;

import java.util.Objects;

public class Tile {

    /**
     * The color of the tile, taken from the Enumeration PossibleColors
     */
    private PossibleColors color;

    /**
     * Stores the row in which the Tile is positioned on the Board
     */
    private int boardRow;

    /**
     * Stores the column in which the Tile is positioned on the Board
     */
    private int boardColumn;


    /**
     * Default constructor method
     */
    public Tile() {
        color = PossibleColors.DEFAULT;
        boardRow = -1;
        boardColumn = -1;
    }


    /**
     * Constructor method
     * @param a represents the row in which the tile is located on the board
     * @param b represents the column in which the tile is located on the board
     * @param Color is the color to be assigned to the tile
     */
    public Tile(int a, int b, PossibleColors Color){
        boardRow = a;
        boardColumn = b;
        this.color = Color;
    }


    /**
     * Constructor method that sets row and column to a default position (-1)
     * @param Color is the color to be assigned to the tile
     */
    public Tile(PossibleColors Color){
        boardRow = -1;
        boardColumn = -1;
        this.color = Color;
    }


    /**
     * Getter method
     * @return the color of the tile as a string
     */
   public String getColor(){
        return color.getColor();
    }


    /**
     * Getter method
     * @return the number of the row in which the tile is located on the board
     */
    public int getRow(){
        return this.boardRow;
    }


    /**
     * Getter method
     * @return the number of the column in which the tile is located on the board
     */
    public int getColumn(){
        return this.boardColumn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return boardRow == tile.boardRow && boardColumn == tile.boardColumn && color == tile.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, boardRow, boardColumn);
    }
}