package it.polimi.it.model.Board;
import it.polimi.it.model.PossibleColors;
import it.polimi.it.model.Tile;
import it.polimi.it.model.TilesBag;

import java.util.ArrayList;
import java.util.List;


public abstract class Board {
    //The maximum dimension of the board is a 9x9 matrix
    public static Tile[][] Matrix;

    public Board() {

    }


    /**
     * Checks if a refill of the board is needed calling method CheckRefill
     * eventually refills the board, extracting random tiles from the TilesBag
     */
    public void Refill() {
        if (!CheckRefill()) {
            return;
        }
        int NremainingTiles = 132;
        NremainingTiles = TilesBag.getTotRemaining();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (Matrix[i][j].getColor().equals("DEFAULT")) {
                    NremainingTiles = TilesBag.getTotRemaining();
                    if (NremainingTiles > 0) {
                        Tile tile = TilesBag.RandomTiles(i, j);
                        Matrix[i][j] = tile;
                    }
                }
            }
        }
        return;
    }

    /**
     * Given the Board matrix, checks if a refill is needed.
     *
     * @return a boolean value which is true if the boards needs a refill, false otherwise
     */
    private Boolean CheckRefill() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Tile til = Matrix[i][j];
                if (!til.getColor().equals("DEFAULT") && !til.getColor().equals("XTILE")) {
                    //you get in this branch if the tile in position i;j is a 'Color' tile;
                    if (i < 8) {
                        if (!Matrix[i + 1][j].getColor().equals("DEFAULT") && !Matrix[i + 1][j].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (i > 0) {
                        if (!Matrix[i - 1][j].getColor().equals("DEFAULT") && !Matrix[i - 1][j].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (j < 8) {
                        if (!Matrix[i][j + 1].getColor().equals("DEFAULT") && !Matrix[i][j + 1].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                    if (i > 0) {
                        if (!Matrix[i][j - 1].getColor().equals("DEFAULT") && !Matrix[i][j - 1].getColor().equals("XTILE")) {
                            return false;
                        }
                    }
                }
            }
        }
        //you get to this point of the method only if all the 'color' tiles don't have any adjacent
        return true;

    }

    /**
     * Finds the maximum number of Tiles that can be taken from the board
     *
     * @param MaxFromShelfie is the maximum number of tiles that can be contained in the columns of the player's Shelfie
     * @return the maximum number of Tiles that can be taken from the board, this number can't be higher than MaxFromShelfie
     */
    public int FindMaxAdjacent(int MaxFromShelfie) {
        int Max = 0;
        int Count = 0;
        int MiddleCount = 0;
        int[][] Visited = new int[9][9];

        //Initialize the 'Visited' Matrix to all 0
        for (int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                Visited[a][b] = 0;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Count = 0;

                if (!Matrix[i][j].getColor().equals("DEFAULT") && !Matrix[i][j].getColor().equals("DEFAULT")) {//se la cella è colorata
                    if (i == 0 || i == 8 || j == 0 || j == 8) {//se la cella è esterna
                        Count = 1;
                        Visited[i][j] = 1;
                    } else {//se la cella è interna, controllo che almeno una delle tile confinanti siano incolore
                        MiddleCount = 0;
                        if (i >= 1 && MiddleCount == 0) {
                            if (Matrix[i - 1][j].getColor().equals("DEFAULT") || Matrix[i - 1][j].getColor().equals("XTILE")) {
                                MiddleCount++;
                                Visited[i - 1][j] = 1;
                            }
                        }
                        if (j >= 1 && MiddleCount == 0) {
                            if (Matrix[i][j - 1].getColor().equals("DEFAULT") || Matrix[i][j - 1].getColor().equals("XTILE")) {
                                MiddleCount++;
                                Visited[i][j - 1] = 1;
                            }
                        }
                        if (i <= 7 && MiddleCount == 0) {
                            if (Matrix[i + 1][j].getColor().equals("DEFAULT") || Matrix[i + 1][j].getColor().equals("XTILE")) {
                                MiddleCount++;
                                Visited[i + 1][j] = 1;
                            }
                        }
                        if (j <= 7 && MiddleCount == 0) {
                            if (Matrix[i][j + 1].getColor().equals("DEFAULT") || Matrix[i][j + 1].getColor().equals("XTILE")) {
                                MiddleCount++;
                                Visited[i][j + 1] = 1;
                            }
                        }
                        if (MiddleCount > 0) {//porto ad 1 il counter solo se la casella è a tutti gli effetti prendibile
                            Count = 1;
                            MiddleCount = 0;
                        }
                    }// A questo punto count è 1 se la tile può essere presa

                    //controllo se posso andare oltre e aumentare il counter a 2 o 3,
                    //ma prima verifico che effettivamente mi serva a qualcosa:
                    if (MaxFromShelfie == 1) {
                        return 1;
                    }
                    Count = Count + CountAdjacent(i, j, Visited);

                    if (Count >= 3) {
                        return Count;
                    }
                    if (Count > Max) {
                        Max = Count;
                    }
                    if (Max >= MaxFromShelfie) {
                        return MaxFromShelfie;
                    }
                    if (Max >= 3) {
                        return 3;
                    }
                }
            }
        }
        return Max;
    }

    int CountAdjacent(int i, int j, int[][] Visited) {//parte da una posizione dalla matrice che è sicuramente prendibile
        int counter = 0;                                //i e j sono esattamente quelle usate nel metodo chiamante, da cui partire
        int upOk = 0;
        int downOk = 0;
        int LeftOk = 0;
        int RightOk = 0;
        int rowUp = i - 1;
        int ColLeft = j - 1;
        int ColRight = j + 1;
        int rowDown = i - 1;

        if (i > 0) {
            if (Visited[i - 1][j] == 0 && !Matrix[i - 1][j].getColor().equals("DEFAULT") && !Matrix[i - 1][j].getColor().equals("XTILE")) {
                //int up = 0;
                int MiddleCountUp = 0;
                if (rowUp == 0 || rowUp == 8 || j == 0 || j == 8) {
                    MiddleCountUp = 1;
                    Visited[i][rowUp] = 1;
                } else {//controllo che la casella sopra abbia almeno una confinante con un void
                    if (rowUp >= 1 && MiddleCountUp == 0) {
                        if (Matrix[rowUp - 1][j].getColor().equals("DEFAULT") || Matrix[rowUp - 1][j].getColor().equals("XTILE")) {
                            if (Visited[rowUp - 1][j] == 0) {
                                MiddleCountUp++;
                                Visited[rowUp - 1][j] = 1;
                            }
                        }
                    }
                    if (j >= 1 && MiddleCountUp == 0) {
                        if (Matrix[rowUp][j - 1].getColor().equals("DEFAULT") || Matrix[rowUp][j - 1].getColor().equals("XTILE")) {
                            if (Visited[rowUp][j - 1] == 0) {
                                MiddleCountUp++;
                                Visited[rowUp][j - 1] = 1;
                            }
                        }
                    }
                    if (rowUp <= 7 && MiddleCountUp == 0) {
                        if (Matrix[rowUp + 1][j].getColor().equals("DEFAULT") || Matrix[rowUp + 1][j].getColor().equals("XTILE")) {
                            if (Visited[rowUp + 1][j] == 0) {
                                MiddleCountUp++;
                                Visited[rowUp + 1][j] = 1;
                            }
                        }
                    }
                    if (j <= 7 && MiddleCountUp == 0) {
                        if (Matrix[rowUp][j + 1].getColor().equals("DEFAULT") || Matrix[rowUp][j + 1].getColor().equals("XTILE")) {
                            if (Visited[rowUp][j + 1] == 0) {
                                MiddleCountUp++;
                                Visited[rowUp][j + 1] = 1;
                            }
                        }
                    }
                    if (MiddleCountUp > 0) {
                        counter++;
                        upOk = 1;
                    }
                }
            }
        }

        if (j > 0) {//controllo relativo alla casella a sinistra di quella data
            if (Visited[i][j - 1] == 0 && !Matrix[i][j - 1].getColor().equals("DEFAULT") && !Matrix[i][j - 1].getColor().equals("XTILE")) {
                int MiddleCountLeft = 0;
                if (i == 0 || i == 8 || ColLeft == 0 || ColLeft == 8) {
                    MiddleCountLeft = 1;
                    Visited[i][ColLeft] = 1;
                } else {//controllo che la casella sopra abbia almeno una confinante con un void
                    if (i >= 1 && MiddleCountLeft == 0) {
                        if (Matrix[i - 1][ColLeft].getColor().equals("DEFAULT") || Matrix[i - 1][ColLeft].getColor().equals("XTILE")) {
                            if (Visited[i - 1][ColLeft] == 0) {
                                MiddleCountLeft++;
                                Visited[i - 1][ColLeft] = 1;
                            }
                        }
                    }
                    if (ColLeft >= 1 && MiddleCountLeft == 0) {
                        if (Matrix[i][ColLeft - 1].getColor().equals("DEFAULT") || Matrix[i][ColLeft - 1].getColor().equals("XTILE")) {
                            if (Visited[i][ColLeft - 1] == 0) {
                                MiddleCountLeft++;
                                Visited[i][ColLeft - 1] = 1;
                            }
                        }
                    }
                    if (i <= 7 && MiddleCountLeft == 0) {
                        if (Matrix[i + 1][ColLeft].getColor().equals("DEFAULT") || Matrix[i + 1][ColLeft].getColor().equals("XTILE")) {
                            if (Visited[i + 1][ColLeft] == 0) {
                                MiddleCountLeft++;
                                Visited[i + 1][ColLeft] = 1;
                            }
                        }
                    }
                    if (ColLeft <= 7 && MiddleCountLeft == 0) {
                        if (Matrix[i][ColLeft + 1].getColor().equals("DEFAULT") || Matrix[i][ColLeft + 1].getColor().equals("XTILE")) {
                            if (Visited[i][ColLeft + 1] == 0) {
                                MiddleCountLeft++;
                                Visited[i][ColLeft + 1] = 1;
                            }
                        }
                    }
                    if (MiddleCountLeft > 0) {
                        counter++;
                        LeftOk = 1;
                    }
                }
            }
        }

        if (counter >= 2) {
            return 2;
        }
        if (j < 8) {//controllo relativo alla casella a destra di quella data
            if (Visited[i][j + 1] == 0 && !Matrix[i][j + 1].getColor().equals("DEFAULT") && !Matrix[i][j + 1].getColor().equals("XTILE")) {
                int MiddleCountRight = 0;
                if (i == 0 || i == 8 || ColRight == 0 || ColRight == 8) {
                    MiddleCountRight = 1;
                    Visited[i][ColRight] = 1;
                } else {//controllo che la casella sopra abbia almeno una confinante con un void
                    if (i >= 1 && MiddleCountRight == 0) {
                        if (Matrix[i - 1][ColRight].getColor().equals("DEFAULT") || Matrix[i - 1][ColRight].getColor().equals("XTILE")) {
                            if (Visited[i - 1][ColRight] == 0) {
                                MiddleCountRight++;
                                Visited[i - 1][ColRight] = 1;
                            }
                        }
                    }
                    if (ColRight >= 1 && MiddleCountRight == 0) {
                        if (Matrix[i][ColRight - 1].getColor().equals("DEFAULT") || Matrix[i][ColRight - 1].getColor().equals("XTILE")) {
                            if (Visited[i][ColRight - 1] == 0) {
                                MiddleCountRight++;
                                Visited[i][ColRight - 1] = 1;
                            }
                        }
                    }
                    if (i <= 7 && MiddleCountRight == 0) {
                        if (Matrix[i + 1][ColRight].getColor().equals("DEFAULT") || Matrix[i + 1][ColRight].getColor().equals("XTILE")) {
                            if (Visited[i + 1][ColRight] == 0) {
                                MiddleCountRight++;
                                Visited[i + 1][ColRight] = 1;
                            }

                        }
                    }
                    if (ColRight <= 7 && MiddleCountRight == 0) {
                        if (Matrix[i][ColRight + 1].getColor().equals("DEFAULT") || Matrix[i][ColRight + 1].getColor().equals("XTILE")) {
                            if (Visited[i][ColRight + 1] == 0) {
                                MiddleCountRight++;
                                Visited[i][ColRight + 1] = 1;
                            }

                        }
                    }
                    if (MiddleCountRight > 0) {
                        counter++;
                        RightOk = 1;
                    }
                }
            }
        }

        if (counter >= 2) {
            return 2;
        }

        if (i < 8) {//Riga sotto
            if (Visited[i + 1][j] == 0 && !Matrix[i + 1][j].getColor().equals("DEFAULT") && !Matrix[i + 1][j].getColor().equals("XTILE")) {
                //int Down = 0;
                int MiddleCountDown = 0;
                if (rowDown == 0 || rowDown == 8 || j == 0 || j == 8) {
                    MiddleCountDown = 1;
                    Visited[i][rowDown] = 1;
                } else {//controllo che la casella sopra abbia almeno una confinante con un void
                    if (rowDown >= 1 && MiddleCountDown == 0) {
                        if (Matrix[rowDown - 1][j].getColor().equals("DEFAULT") || Matrix[rowDown - 1][j].getColor().equals("XTILE")) {
                            if (Visited[rowDown - 1][j] == 0) {
                                MiddleCountDown++;
                                Visited[rowDown - 1][j] = 1;
                            }
                        }
                    }
                    if (j >= 1 && MiddleCountDown == 0) {
                        if (Matrix[rowDown][j - 1].getColor().equals("DEFAULT") || Matrix[rowDown][j - 1].getColor().equals("XTILE")) {
                            if (Visited[rowDown][j - 1] == 0) {
                                MiddleCountDown++;
                                Visited[rowDown][j - 1] = 1;
                            }
                        }
                    }
                    if (rowDown <= 7 && MiddleCountDown == 0) {
                        if (Matrix[rowDown + 1][j].getColor().equals("DEFAULT") || Matrix[rowDown + 1][j].getColor().equals("XTILE")) {
                            if (Visited[rowDown + 1][j] == 0) {
                                MiddleCountDown++;
                                Visited[rowDown + 1][j] = 1;
                            }
                        }
                    }
                    if (j <= 7 && MiddleCountDown == 0) {
                        if (Matrix[rowDown][j + 1].getColor().equals("DEFAULT") || Matrix[rowDown][j + 1].getColor().equals("XTILE")) {
                            if (Visited[rowDown][j + 1] == 0) {
                                MiddleCountDown++;
                                Visited[rowDown][j + 1] = 1;
                            }
                        }
                    }
                    if (MiddleCountDown > 0) {
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
            counter = counter + CountAdjacentSecondStage(rowUp, j, Visited);
        }
        if (counter >= 2) {
            return 2;
        }
        if (downOk == 1) {
            counter = counter + CountAdjacentSecondStage(rowDown, j, Visited);
        }
        if (counter >= 2) {
            return 2;
        }
        if (LeftOk == 1) {
            counter = counter + CountAdjacentSecondStage(i, ColLeft, Visited);
        }
        if (counter >= 2) {
            return 2;
        }
        if (RightOk == 1) {
            counter = counter + CountAdjacentSecondStage(i, ColRight, Visited);
        }
        if (counter >= 2) {
            return 2;
        }
        return counter;
    }

    int CountAdjacentSecondStage(int i, int j, int[][] Visited) {
        int rowUp = i - 1;
        int ColLeft = j - 1;
        int ColRight = j + 1;
        int rowDown = i - 1;

        if (i > 0) {
            if (Visited[i - 1][j] == 0 && !Matrix[i - 1][j].getColor().equals("DEFAULT") && !Matrix[i - 1][j].getColor().equals("XTILE")) {
                //int up = 0;
                if (rowUp == 0 || rowUp == 8 || j == 0 || j == 8) {
                    Visited[i][rowUp] = 1;
                } else {//controllo che la casella sopra abbia almeno una confinante con un void
                    if (rowUp >= 1) {
                        if (Matrix[rowUp - 1][j].getColor().equals("DEFAULT") || Matrix[rowUp - 1][j].getColor().equals("XTILE")) {
                            if (Visited[rowUp - 1][j] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (j >= 1) {
                        if (Matrix[rowUp][j - 1].getColor().equals("DEFAULT") || Matrix[rowUp][j - 1].getColor().equals("XTILE")) {
                            if (Visited[rowUp][j - 1] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (rowUp <= 7) {
                        if (Matrix[rowUp + 1][j].getColor().equals("DEFAULT") || Matrix[rowUp + 1][j].getColor().equals("XTILE")) {
                            if (Visited[rowUp + 1][j] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (j <= 7) {
                        if (Matrix[rowUp][j + 1].getColor().equals("DEFAULT") || Matrix[rowUp][j + 1].getColor().equals("XTILE")) {
                            if (Visited[rowUp][j + 1] == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }

        if (j > 0) {//controllo relativo alla casella a sinistra di quella data
            if (Visited[i][j - 1] == 0 && !Matrix[i][j - 1].getColor().equals("DEFAULT") && !Matrix[i][j - 1].getColor().equals("XTILE")) {
                if (i == 0 || i == 8 || ColLeft == 0 || ColLeft == 8) {
                    return 1;
                } else {//controllo che la casella sopra abbia almeno una confinante con un void
                    if (i >= 1) {
                        if (Matrix[i - 1][ColLeft].getColor().equals("DEFAULT") || Matrix[i - 1][ColLeft].getColor().equals("XTILE")) {
                            if (Visited[i - 1][ColLeft] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (ColLeft >= 1) {
                        if (Matrix[i][ColLeft - 1].getColor().equals("DEFAULT") || Matrix[i][ColLeft - 1].getColor().equals("XTILE")) {
                            if (Visited[i][ColLeft - 1] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (i <= 7) {
                        if (Matrix[i + 1][ColLeft].getColor().equals("DEFAULT") || Matrix[i + 1][ColLeft].getColor().equals("XTILE")) {
                            if (Visited[i + 1][ColLeft] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (ColLeft <= 7) {
                        if (Matrix[i][ColLeft + 1].getColor().equals("DEFAULT") || Matrix[i][ColLeft + 1].getColor().equals("XTILE")) {
                            if (Visited[i][ColLeft + 1] == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        if (j < 8) {//controllo relativo alla casella a destra di quella data
            if (Visited[i][j + 1] == 0 && !Matrix[i][j + 1].getColor().equals("DEFAULT") && !Matrix[i][j + 1].getColor().equals("XTILE")) {
                if (i == 0 || i == 8 || ColRight == 0 || ColRight == 8) {
                    return 1;
                } else {//controllo che la casella sopra abbia almeno una confinante con un void
                    if (i >= 1) {
                        if (Matrix[i - 1][ColRight].getColor().equals("DEFAULT") || Matrix[i - 1][ColRight].getColor().equals("XTILE")) {
                            if (Visited[i - 1][ColRight] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (ColRight >= 1) {
                        if (Matrix[i][ColRight - 1].getColor().equals("DEFAULT") || Matrix[i][ColRight - 1].getColor().equals("XTILE")) {
                            if (Visited[i][ColRight - 1] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (i <= 7) {
                        if (Matrix[i + 1][ColRight].getColor().equals("DEFAULT") || Matrix[i + 1][ColRight].getColor().equals("XTILE")) {
                            if (Visited[i + 1][ColRight] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (ColRight <= 7) {
                        if (Matrix[i][ColRight + 1].getColor().equals("DEFAULT") || Matrix[i][ColRight + 1].getColor().equals("XTILE")) {
                            if (Visited[i][ColRight + 1] == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        if (i < 8) {//Riga sotto
            if (Visited[i + 1][j] == 0 && !Matrix[i + 1][j].getColor().equals("DEFAULT") && !Matrix[i + 1][j].getColor().equals("XTILE")) {
                if (rowDown == 0 || rowDown == 8 || j == 0 || j == 8) {
                    return 1;
                } else {//controllo che la casella sopra abbia almeno una confinante con un void
                    if (rowDown >= 1) {
                        if (Matrix[rowDown - 1][j].getColor().equals("DEFAULT") || Matrix[rowDown - 1][j].getColor().equals("XTILE")) {
                            if (Visited[rowDown - 1][j] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (j >= 1) {
                        if (Matrix[rowDown][j - 1].getColor().equals("DEFAULT") || Matrix[rowDown][j - 1].getColor().equals("XTILE")) {
                            if (Visited[rowDown][j - 1] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (rowDown <= 7) {
                        if (Matrix[rowDown + 1][j].getColor().equals("DEFAULT") || Matrix[rowDown + 1][j].getColor().equals("XTILE")) {
                            if (Visited[rowDown + 1][j] == 0) {
                                return 1;
                            }
                        }
                    }
                    if (j <= 7) {
                        if (Matrix[rowDown][j + 1].getColor().equals("DEFAULT") || Matrix[rowDown][j + 1].getColor().equals("XTILE")) {
                            if (Visited[rowDown][j + 1] == 0) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }


    public void RemoveTiles(int row1, int row2, int row3, int col1, int col2, int col3) {
        if (row1 >= 0 && row1 <= 8 && col1 >= 0 && col1 <= 8) {
            Matrix[row1][col1] = new Tile(PossibleColors.DEFAULT);
        }
        if (row2 >= 0 && row2 <= 8 && col2 >= 0 && col2 <= 8) {
            Matrix[row2][col2] = new Tile(PossibleColors.DEFAULT);
        }
        if (row3 >= 0 && row3 <= 8 && col3 >= 0 && col3 <= 8) {
            Matrix[row3][col3] = new Tile(PossibleColors.DEFAULT);
        }
    }


    public List<List<Tile>> ChoosableTiles(int size) {
        List<List<Tile>> ChoosableTilesList = new ArrayList<>();
        int[][] visited = new int[9][9];

        for (int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                visited[a][b] = 0;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!Matrix[i][j].getColor().equals("DEFAULT") && !Matrix[i][j].getColor().equals("XTILE") && visited[i][j] == 0) {

                    List<Tile> Triplet = new ArrayList<>();
                    AddTilesToTriplet(visited, i, j, Triplet, size);

                    if (Triplet.size() == 1 && size == 1) {
                        ChoosableTilesList.add(Triplet);
                    } else if (Triplet.size() == size) {
                        ChoosableTilesList.add(Triplet);
                    }
                }
            }
        }
        return ChoosableTilesList;
    }

    private void AddTilesToTriplet(int[][] visited, int i, int j, List<Tile> Triplet, int size) {

        int ok = 0;

        if (i < 0 || i > 8 || j < 0 || j > 8 || visited[i][j] == 1 || Matrix[i][j].getColor().equals("DEFAULT") || Matrix[i][j].getColor().equals("XTILE")) {
            return;
        }
        visited[i][j] = 1;
        if (i == 0 || j == 0 || i == 8 || j == 8) {
            ok = 1;
        } else if (i > 1 && ok == 0) {
            if (Matrix[i - 1][j].getColor().equals("DEFAULT") || Matrix[i - 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (j > 1 && ok == 0) {
            if (Matrix[i][j - 1].getColor().equals("DEFAULT") || Matrix[i][j - 1].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (i < 8 && ok == 0) {
            if (Matrix[i + 1][j].getColor().equals("DEFAULT") || Matrix[i + 1][j].getColor().equals("XTILE")) {
                ok = 1;
            }
        } else if (j < 8 && ok == 0) {
            if (Matrix[i][j + 1].getColor().equals("DEFAULT") || Matrix[i][j + 1].getColor().equals("XTILE")) {
                ok = 1;
            }
        }

        if (ok == 0) {
            return;
        }

        switch (Matrix[i][j].getColor()) {
            case "BLUE":
                Triplet.add(new Tile(i, j, PossibleColors.BLUE));
                break;
            case "GREEN":
                Triplet.add(new Tile(i, j, PossibleColors.GREEN));
                break;
            case "CYAN":
                Triplet.add(new Tile(i, j, PossibleColors.CYAN));
                break;
            case "YELLOW":
                Triplet.add(new Tile(i, j, PossibleColors.YELLOW));
                break;
            case "WHITE":
                Triplet.add(new Tile(i, j, PossibleColors.WHITE));
                break;
            case "PINK":
                Triplet.add(new Tile(i, j, PossibleColors.PINK));
                break;
        }

        if (Triplet.size() < size) {

            AddTilesToTriplet(visited, i - 1, j, Triplet, size);
            AddTilesToTriplet(visited, i + 1, j, Triplet, size);
            AddTilesToTriplet(visited, i, j - 1, Triplet, size);
            AddTilesToTriplet(visited, i, j + 1, Triplet, size);
        }
    }
}