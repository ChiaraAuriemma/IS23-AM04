package it.polimi.it.model.Board;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;

import java.util.ArrayList;
import java.util.List;


public abstract class Board {

    public static Tile[][] matrix;


    /**
     * Constructor method of the abstract class
     * Gets specified in the classes: B2P, B3P and B4P
     */
    public Board() {

    }


    /**
     * Checks if a refill of the board is needed calling method checkRefill
     * Eventually refills the board, extracting random tiles from the TilesBag
     */
    public void refill() {
        if (!checkRefill()) {
            return;
        }
        int remainingTiles;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j].getColor().equals("DEFAULT")) {
                    remainingTiles = TilesBag.getTotRemaining();
                    if (remainingTiles > 0) {
                        Tile tile = TilesBag.randomTiles(i, j);
                        matrix[i][j] = tile;
                    }
                }
            }
        }
    }


    /**
     * Given the Board matrix, checks if a refill is needed.
     * @return a boolean value which is true if the boards needs a refill, false otherwise
     */
    private Boolean checkRefill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Tile til = matrix[i][j];
                if (!til.getColor().equals("DEFAULT") && !til.getColor().equals("XTILE")) {
                    if (i < 8) {
                        if (!matrix[i + 1][j].getColor().equals("DEFAULT") && !matrix[i + 1][j].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (i > 0) {
                        if (!matrix[i - 1][j].getColor().equals("DEFAULT") && !matrix[i - 1][j].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (j < 8) {
                        if (!matrix[i][j + 1].getColor().equals("DEFAULT") && !matrix[i][j + 1].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (i > 0) {
                        if (!matrix[i][j - 1].getColor().equals("DEFAULT") && !matrix[i][j - 1].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Finds the maximum number of Tiles that can be taken from the board
     * @param maxFromShelfie is the maximum number of tiles that can be contained in the columns of the player's Shelfie
     * @return the maximum number of Tiles that can be taken from the board, this number can't be higher than maxFromShelfie
     */
    public int findMaxAdjacent(int maxFromShelfie) {
        int max = 0;
        int count;
        int middlecount;
        int[][] visited = new int[9][9];

        for (int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                visited[a][b] = 0;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                count = 0;
                if (!matrix[i][j].getColor().equals("DEFAULT") && !matrix[i][j].getColor().equals("DEFAULT")) {
                    if (i == 0 || i == 8 || j == 0 || j == 8) {
                        count = 1;
                        visited[i][j] = 1;
                    } else {
                        middlecount = 0;
                        if (i >= 1) {
                            if (matrix[i - 1][j].getColor().equals("DEFAULT") || matrix[i - 1][j].getColor().equals("XTILE")) {
                                middlecount++;
                                visited[i - 1][j] = 1;
                            }
                        }
                        if (j >= 1 && middlecount == 0) {
                            if (matrix[i][j - 1].getColor().equals("DEFAULT") || matrix[i][j - 1].getColor().equals("XTILE")) {
                                middlecount++;
                                visited[i][j - 1] = 1;
                            }
                        }
                        if (i <= 7 && middlecount == 0) {
                            if (matrix[i + 1][j].getColor().equals("DEFAULT") || matrix[i + 1][j].getColor().equals("XTILE")) {
                                middlecount++;
                                visited[i + 1][j] = 1;
                            }
                        }
                        if (j <= 7 && middlecount == 0) {
                            if (matrix[i][j + 1].getColor().equals("DEFAULT") || matrix[i][j + 1].getColor().equals("XTILE")) {
                                middlecount++;
                                visited[i][j + 1] = 1;
                            }
                        }
                        if (middlecount > 0) {
                            count = 1;
                            middlecount = 0;
                        }
                    }

                    if (maxFromShelfie == 1) {
                        return 1;
                    }
                    count = count + countAdjacent(i, j, visited);

                    if (count >= 3) {
                        return count;
                    }
                    if (count > max) {
                        max = count;
                    }
                    if (max >= maxFromShelfie) {
                        return maxFromShelfie;
                    }
                    if (max >= 3) {
                        return 3;
                    }
                }
            }
        }
        return max;
    }

    /**
     * Starting from a given position of the matrix, counts how many tiles,
     *      which are adjacent to the tile in the starting position can be taken in a single turn
     * This method eventually calls countAdjacentSecondStage if needed
     * @param i is the starting row
     * @param j is the starting column
     * @param visited is a matrix used to know if a given cell of the matrix has already been explored
     * @return the number of take-able cells adjacent to the starting one
     */
    int countAdjacent(int i, int j, int[][] visited) {
        int counter = 0;
        int upOk = 0;
        int downOk = 0;
        int leftOk = 0;
        int rightOk = 0;
        int rowUp = i - 1;
        int colLeft = j - 1;
        int colRight = j + 1;
        int rowDown = i - 1;

        if (i > 0) {
            if (visited[i - 1][j] == 0 && !matrix[i - 1][j].getColor().equals("DEFAULT") && !matrix[i - 1][j].getColor().equals("XTILE")) {
                int middlecountUp = 0;
                if (rowUp == 0 || rowUp == 8 || j == 0 || j == 8) {
                    middlecountUp = 1;
                    visited[i][rowUp] = 1;
                } else {
                    if (rowUp >= 1 && middlecountUp == 0) {
                        if (matrix[rowUp - 1][j].getColor().equals("DEFAULT") || matrix[rowUp - 1][j].getColor().equals("XTILE")) {
                            if (visited[rowUp - 1][j] == 0) {
                                middlecountUp++;
                                visited[rowUp - 1][j] = 1;
                            }
                        }
                    }
                    if (j >= 1 && middlecountUp == 0) {
                        if (matrix[rowUp][j - 1].getColor().equals("DEFAULT") || matrix[rowUp][j - 1].getColor().equals("XTILE")) {
                            if (visited[rowUp][j - 1] == 0) {
                                middlecountUp++;
                                visited[rowUp][j - 1] = 1;
                            }
                        }
                    }
                    if (rowUp <= 7 && middlecountUp == 0) {
                        if (matrix[rowUp + 1][j].getColor().equals("DEFAULT") || matrix[rowUp + 1][j].getColor().equals("XTILE")) {
                            if (visited[rowUp + 1][j] == 0) {
                                middlecountUp++;
                                visited[rowUp + 1][j] = 1;
                            }
                        }
                    }
                    if (j <= 7 && middlecountUp == 0) {
                        if (matrix[rowUp][j + 1].getColor().equals("DEFAULT") || matrix[rowUp][j + 1].getColor().equals("XTILE")) {
                            if (visited[rowUp][j + 1] == 0) {
                                middlecountUp++;
                                visited[rowUp][j + 1] = 1;
                            }
                        }
                    }
                    if (middlecountUp > 0) {
                        counter++;
                        upOk = 1;
                    }
                }
            }
        }
        if (j > 0) {
            if (visited[i][j - 1] == 0 && !matrix[i][j - 1].getColor().equals("DEFAULT") && !matrix[i][j - 1].getColor().equals("XTILE")) {
                int middlecountLeft = 0;
                if (i == 0 || i == 8 || colLeft == 0 || colLeft == 8) {
                    middlecountLeft = 1;
                    visited[i][colLeft] = 1;
                } else {
                    if (i >= 1 && middlecountLeft == 0) {
                        if (matrix[i - 1][colLeft].getColor().equals("DEFAULT") || matrix[i - 1][colLeft].getColor().equals("XTILE")) {
                            if (visited[i - 1][colLeft] == 0) {
                                middlecountLeft++;
                                visited[i - 1][colLeft] = 1;
                            }
                        }
                    }
                    if (colLeft >= 1 && middlecountLeft == 0) {
                        if (matrix[i][colLeft - 1].getColor().equals("DEFAULT") || matrix[i][colLeft - 1].getColor().equals("XTILE")) {
                            if (visited[i][colLeft - 1] == 0) {
                                middlecountLeft++;
                                visited[i][colLeft - 1] = 1;
                            }
                        }
                    }
                    if (i <= 7 && middlecountLeft == 0) {
                        if (matrix[i + 1][colLeft].getColor().equals("DEFAULT") || matrix[i + 1][colLeft].getColor().equals("XTILE")) {
                            if (visited[i + 1][colLeft] == 0) {
                                middlecountLeft++;
                                visited[i + 1][colLeft] = 1;
                            }
                        }
                    }
                    if (colLeft <= 7 && middlecountLeft == 0) {
                        if (matrix[i][colLeft + 1].getColor().equals("DEFAULT") || matrix[i][colLeft + 1].getColor().equals("XTILE")) {
                            if (visited[i][colLeft + 1] == 0) {
                                middlecountLeft++;
                                visited[i][colLeft + 1] = 1;
                            }
                        }
                    }
                    if (middlecountLeft > 0) {
                        counter++;
                        leftOk = 1;
                    }
                }
            }
        }
        if (counter >= 2) {
            return 2;
        }
        if (j < 8) {
            if (visited[i][j + 1] == 0 && !matrix[i][j + 1].getColor().equals("DEFAULT") && !matrix[i][j + 1].getColor().equals("XTILE")) {
                int middlecountRight = 0;
                if (i == 0 || i == 8 || colRight == 0 || colRight == 8) {
                    middlecountRight = 1;
                    visited[i][colRight] = 1;
                } else {
                    if (i >= 1 && middlecountRight == 0) {
                        if (matrix[i - 1][colRight].getColor().equals("DEFAULT") || matrix[i - 1][colRight].getColor().equals("XTILE")) {
                            if (visited[i - 1][colRight] == 0) {
                                middlecountRight++;
                                visited[i - 1][colRight] = 1;
                            }
                        }
                    }
                    if (colRight >= 1 && middlecountRight == 0) {
                        if (matrix[i][colRight - 1].getColor().equals("DEFAULT") || matrix[i][colRight - 1].getColor().equals("XTILE")) {
                            if (visited[i][colRight - 1] == 0) {
                                middlecountRight++;
                                visited[i][colRight - 1] = 1;
                            }
                        }
                    }
                    if (i <= 7 && middlecountRight == 0) {
                        if (matrix[i + 1][colRight].getColor().equals("DEFAULT") || matrix[i + 1][colRight].getColor().equals("XTILE")) {
                            if (visited[i + 1][colRight] == 0) {
                                middlecountRight++;
                                visited[i + 1][colRight] = 1;
                            }
                        }
                    }
                    if (colRight <= 7 && middlecountRight == 0) {
                        if (matrix[i][colRight + 1].getColor().equals("DEFAULT") || matrix[i][colRight + 1].getColor().equals("XTILE")) {
                            if (visited[i][colRight + 1] == 0) {
                                middlecountRight++;
                                visited[i][colRight + 1] = 1;
                            }
                        }
                    }
                    if (middlecountRight > 0) {
                        counter++;
                        rightOk = 1;
                    }
                }
            }
        }
        if (counter >= 2) {
            return 2;
        }
        if (i < 8) {
            if (visited[i + 1][j] == 0 && !matrix[i + 1][j].getColor().equals("DEFAULT") && !matrix[i + 1][j].getColor().equals("XTILE")) {
                int middlecountDown = 0;
                if (rowDown == 0 || rowDown == 8 || j == 0 || j == 8) {
                    middlecountDown = 1;
                    visited[i][rowDown] = 1;
                } else {
                    if (rowDown >= 1 && middlecountDown == 0) {
                        if (matrix[rowDown - 1][j].getColor().equals("DEFAULT") || matrix[rowDown - 1][j].getColor().equals("XTILE")) {
                            if (visited[rowDown - 1][j] == 0) {
                                middlecountDown++;
                                visited[rowDown - 1][j] = 1;
                            }
                        }
                    }
                    if (j >= 1 && middlecountDown == 0) {
                        if (matrix[rowDown][j - 1].getColor().equals("DEFAULT") || matrix[rowDown][j - 1].getColor().equals("XTILE")) {
                            if (visited[rowDown][j - 1] == 0) {
                                middlecountDown++;
                                visited[rowDown][j - 1] = 1;
                            }
                        }
                    }
                    if (rowDown <= 7 && middlecountDown == 0) {
                        if (matrix[rowDown + 1][j].getColor().equals("DEFAULT") || matrix[rowDown + 1][j].getColor().equals("XTILE")) {
                            if (visited[rowDown + 1][j] == 0) {
                                middlecountDown++;
                                visited[rowDown + 1][j] = 1;
                            }
                        }
                    }
                    if (j <= 7 && middlecountDown == 0) {
                        if (matrix[rowDown][j + 1].getColor().equals("DEFAULT") || matrix[rowDown][j + 1].getColor().equals("XTILE")) {
                            if (visited[rowDown][j + 1] == 0) {
                                middlecountDown++;
                                visited[rowDown][j + 1] = 1;
                            }
                        }
                    }
                    if (middlecountDown > 0) {
                        counter++;
                        downOk = 1;
                    }
                }
            }
        }
        if (counter >= 2) {
            return 2;
        }
        if (upOk == 1) {
            counter = counter + countAdjacentSecondStage(rowUp, j, visited);
        }
        if (counter >= 2) {
            return 2;
        }
        if (downOk == 1) {
            counter = counter + countAdjacentSecondStage(rowDown, j, visited);
        }
        if (counter >= 2) {
            return 2;
        }
        if (leftOk == 1) {
            counter = counter + countAdjacentSecondStage(i, colLeft, visited);
        }
        if (counter >= 2) {
            return 2;
        }
        if (rightOk == 1) {
            counter = counter + countAdjacentSecondStage(i, colRight, visited);
        }
        if (counter >= 2) {
            return 2;
        }
        return counter;
    }


    /**
     * Counts how many Tiles adjacent to the starting cell can be taken in a single turn
     * This is a helper method that might only be called by countAdjacent
     * @param i is the line of the starting cell
     * @param j is the column of the starting cell
     * @param visited is a matrix used to know if a given cell of the matrix has already been explored
     * @return the number of take-able Tiles that are adjacent to the starting one
     */
    int countAdjacentSecondStage(int i, int j, int[][] visited) {
        int rowUp = i - 1;
        int colLeft = j - 1;
        int colRight = j + 1;
        int rowDown = i - 1;

        if (i > 0) {
            if (visited[i - 1][j] == 0 && !matrix[i - 1][j].getColor().equals("DEFAULT") && !matrix[i - 1][j].getColor().equals("XTILE")) {
                if (rowUp == 0 || rowUp == 8 || j == 0 || j == 8) {
                    visited[i][rowUp] = 1;
                } else {
                    if (rowUp >= 1) {
                        if (matrix[rowUp - 1][j].getColor().equals("DEFAULT") || matrix[rowUp - 1][j].getColor().equals("XTILE")) {
                            if (visited[rowUp - 1][j] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (j >= 1) {
                        if (matrix[rowUp][j - 1].getColor().equals("DEFAULT") || matrix[rowUp][j - 1].getColor().equals("XTILE")) {
                            if (visited[rowUp][j - 1] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (rowUp <= 7) {
                        if (matrix[rowUp + 1][j].getColor().equals("DEFAULT") || matrix[rowUp + 1][j].getColor().equals("XTILE")) {
                            if (visited[rowUp + 1][j] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (j <= 7) {
                        if (matrix[rowUp][j + 1].getColor().equals("DEFAULT") || matrix[rowUp][j + 1].getColor().equals("XTILE")) {
                            if (visited[rowUp][j + 1] == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        if (j > 0) {
            if (visited[i][j - 1] == 0 && !matrix[i][j - 1].getColor().equals("DEFAULT") && !matrix[i][j - 1].getColor().equals("XTILE")) {
                if (i == 0 || i == 8 || colLeft == 0 || colLeft == 8) {
                    return 1;
                } else {
                    if (i >= 1) {
                        if (matrix[i - 1][colLeft].getColor().equals("DEFAULT") || matrix[i - 1][colLeft].getColor().equals("XTILE")) {
                            if (visited[i - 1][colLeft] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (colLeft >= 1) {
                        if (matrix[i][colLeft - 1].getColor().equals("DEFAULT") || matrix[i][colLeft - 1].getColor().equals("XTILE")) {
                            if (visited[i][colLeft - 1] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (i <= 7) {
                        if (matrix[i + 1][colLeft].getColor().equals("DEFAULT") || matrix[i + 1][colLeft].getColor().equals("XTILE")) {
                            if (visited[i + 1][colLeft] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (colLeft <= 7) {
                        if (matrix[i][colLeft + 1].getColor().equals("DEFAULT") || matrix[i][colLeft + 1].getColor().equals("XTILE")) {
                            if (visited[i][colLeft + 1] == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        if (j < 8) {
            if (visited[i][j + 1] == 0 && !matrix[i][j + 1].getColor().equals("DEFAULT") && !matrix[i][j + 1].getColor().equals("XTILE")) {
                if (i == 0 || i == 8 || colRight == 0 || colRight == 8) {
                    return 1;
                } else {
                    if (i >= 1) {
                        if (matrix[i - 1][colRight].getColor().equals("DEFAULT") || matrix[i - 1][colRight].getColor().equals("XTILE")) {
                            if (visited[i - 1][colRight] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (colRight >= 1) {
                        if (matrix[i][colRight - 1].getColor().equals("DEFAULT") || matrix[i][colRight - 1].getColor().equals("XTILE")) {
                            if (visited[i][colRight - 1] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (i <= 7) {
                        if (matrix[i + 1][colRight].getColor().equals("DEFAULT") || matrix[i + 1][colRight].getColor().equals("XTILE")) {
                            if (visited[i + 1][colRight] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (colRight <= 7) {
                        if (matrix[i][colRight + 1].getColor().equals("DEFAULT") || matrix[i][colRight + 1].getColor().equals("XTILE")) {
                            if (visited[i][colRight + 1] == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        if (i < 8) {//Riga sotto
            if (visited[i + 1][j] == 0 && !matrix[i + 1][j].getColor().equals("DEFAULT") && !matrix[i + 1][j].getColor().equals("XTILE")) {
                if (rowDown == 0 || rowDown == 8 || j == 0 || j == 8) {
                    return 1;
                } else {
                    if (rowDown >= 1) {
                        if (matrix[rowDown - 1][j].getColor().equals("DEFAULT") || matrix[rowDown - 1][j].getColor().equals("XTILE")) {
                            if (visited[rowDown - 1][j] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (j >= 1) {
                        if (matrix[rowDown][j - 1].getColor().equals("DEFAULT") || matrix[rowDown][j - 1].getColor().equals("XTILE")) {
                            if (visited[rowDown][j - 1] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (rowDown <= 7) {
                        if (matrix[rowDown + 1][j].getColor().equals("DEFAULT") || matrix[rowDown + 1][j].getColor().equals("XTILE")) {
                            if (visited[rowDown + 1][j] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (j <= 7) {
                        if (matrix[rowDown][j + 1].getColor().equals("DEFAULT") || matrix[rowDown][j + 1].getColor().equals("XTILE")) {
                            if (visited[rowDown][j + 1] == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }


    /**
     * Removes the tiles that the player chose to take from the board
     * Given the coordinate (row and column of the Tiles in the board)
     *      Rows and Columns are set to -1 if the player took less than 3 Tiles
     * @param row1 is the row of the first tile
     * @param row2 is the row of the second tile
     * @param row3 is the row of the third tile
     * @param col1 is the column of the first tile
     * @param col2 is the column of the second tile
     * @param col3 is the column of the third tile
     */
    public void RemoveTiles(int row1, int row2, int row3, int col1, int col2, int col3) {
        if (row1 >= 0 && row1 <= 8 && col1 >= 0 && col1 <= 8) {
            matrix[row1][col1] = new Tile(PossibleColors.DEFAULT);
        }
        if (row2 >= 0 && row2 <= 8 && col2 >= 0 && col2 <= 8) {
            matrix[row2][col2] = new Tile(PossibleColors.DEFAULT);
        }
        if (row3 >= 0 && row3 <= 8 && col3 >= 0 && col3 <= 8) {
            matrix[row3][col3] = new Tile(PossibleColors.DEFAULT);
        }
    }


    /**
     * Given the number of Tiles that a player wants to take from the board,
     * Returns every combination of Tiles that can be taken following the rules of the game
     * Eventually calls addTilesToTriplet
     * @param size is the number of Tiles that the player wants to take from the board
     * @return a List that contains a List of Tiles
     */
    public List<List<Tile>> choosableTiles(int size) {
        List<List<Tile>> choosableTilesList = new ArrayList<>();
        int[][] visited = new int[9][9];

        for (int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                visited[a][b] = 0;
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!matrix[i][j].getColor().equals("DEFAULT") && !matrix[i][j].getColor().equals("XTILE") && visited[i][j] == 0) {
                    List<Tile> triplet = new ArrayList<>();
                    addTilesTotriplet(visited, i, j, triplet, size);

                    if (triplet.size() == 1 && size == 1) {
                        choosableTilesList.add(triplet);
                    } else if (triplet.size() == size) {
                        choosableTilesList.add(triplet);
                    }
                }
            }
        }
        return choosableTilesList;
    }


    /**
     * Helper method that can be called only by choosableTiles
     * Adds to the List of tiles 'triplet' the take-able tiles following the rules of the game
     * This method is recursive and calls itself on every direction departing from the starting cell, if the triplet isn't completed
     * @param visited is a matrix used to know if a given cell had already been explored
     * @param i is the row of the starting Tile
     * @param j is the column of the starting Tile
     * @param triplet is the List in which a Tile is appended if is take-able
     * @param size is the maximum size of the List 'triplet', once is reached the List can't be expanded and the traversing of the board stops
     */
    private void addTilesTotriplet(int[][] visited, int i, int j, List<Tile> triplet, int size) {
        int ok = 0;
        if (i < 0 || i > 8 || j < 0 || j > 8 || visited[i][j] == 1 || matrix[i][j].getColor().equals("DEFAULT") || matrix[i][j].getColor().equals("XTILE")) {
            return;
        }
        visited[i][j] = 1;
        if (i == 0 || j == 0 || i == 8 || j == 8) {
            ok = 1;
        } else if (i > 1 && ok == 0) {
            if (matrix[i - 1][j].getColor().equals("DEFAULT") || matrix[i - 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (j > 1 && ok == 0) {
            if (matrix[i][j - 1].getColor().equals("DEFAULT") || matrix[i][j - 1].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (i < 8 && ok == 0) {
            if (matrix[i + 1][j].getColor().equals("DEFAULT") || matrix[i + 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (j < 8 && ok == 0) {
            if (matrix[i][j + 1].getColor().equals("DEFAULT") || matrix[i][j + 1].getColor().equals("XTILE")) {
                ok = 1;
            }
        }
        if (ok == 0) {
            return;
        }
        switch (matrix[i][j].getColor()) {
            case "BLUE":
                triplet.add(new Tile(i, j, PossibleColors.BLUE));
                break;
            case "GREEN":
                triplet.add(new Tile(i, j, PossibleColors.GREEN));
                break;
            case "CYAN":
                triplet.add(new Tile(i, j, PossibleColors.CYAN));
                break;
            case "YELLOW":
                triplet.add(new Tile(i, j, PossibleColors.YELLOW));
                break;
            case "WHITE":
                triplet.add(new Tile(i, j, PossibleColors.WHITE));
                break;
            case "PINK":
                triplet.add(new Tile(i, j, PossibleColors.PINK));
                break;
        }
        if (triplet.size() < size) {
            addTilesTotriplet(visited, i - 1, j, triplet, size);
            addTilesTotriplet(visited, i + 1, j, triplet, size);
            addTilesTotriplet(visited, i, j - 1, triplet, size);
            addTilesTotriplet(visited, i, j + 1, triplet, size);
        }
    }
}