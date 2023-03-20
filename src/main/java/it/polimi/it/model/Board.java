package it.polimi.it.model;
import java.util.Scanner;


public abstract class Board {
    //The maximum dimension of the board is a 9x9 matrix
    public static Tile[][] Matrix;

    public Board(){

    }

    //Checks if a refill of the board is needed; eventually refills the board
    static void Refill(){
        if(!CheckRefill()){
            return;
        }
        int NremainingTiles=132;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(Matrix[i][j].getColor().equals("DEFAULT")){
                    NremainingTiles=TilesBag.getTotRemaining();
                    if(NremainingTiles>0) {
                        Tile tile = TilesBag.RandomTiles();
                        Matrix[i][j] = tile;
                    }
                }
            }
        }
        return;
    }

    private static Boolean CheckRefill(){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                Tile til = Matrix[i][j];
                if(!til.getColor().equals("DEFAULT") && !til.getColor().equals("XTILE")){
                    //you get in this branch if the tile in position i;j is a 'Color' tile;
                    if(i<8){
                        if(!Matrix[i+1][j].getColor().equals("DEFAULT") && !Matrix[i+1][j].getColor().equals("XTILE")){
                            return false;
                        }
                    }
                    if (i>0){
                        if(!Matrix[i-1][j].getColor().equals("DEFAULT") && !Matrix[i-1][j].getColor().equals("XTILE")){
                            return false;
                        }
                    }
                    if (j<8){
                        if(!Matrix[i][j+1].getColor().equals("DEFAULT") && !Matrix[i][j+1].getColor().equals("XTILE")){
                            return false;
                        }
                    }
                    if(i>0){
                        if(!Matrix[i][j-1].getColor().equals("DEFAULT") && !Matrix[i][j-1].getColor().equals("XTILE")){
                            return false;
                        }
                    }
                }
            }
        }
        //you get to this point of the method only if all the 'color' tiles don't have any adjacent
        return true;

    }

    /*static Tile[] ChooseTiles(int MaxFromShelfie){
        Tile[] ChosenTiles;
        ChosenTiles = new Tile[4];
        int Number=0;
        int MaxRemainingOnBoard=0;

        MaxRemainingOnBoard = FindMaxAdjacent();

        System.out.println("How many tiles would you like to get? \nYour Shelfie can contain a maximum of " + MaxFromShelfie + " new tiles!");
        Scanner s = new Scanner(System.in);
        Number = s.nextInt();
        while(Number<=0 || Number>=4 || Number > MaxFromShelfie){//Checks that the number of tiles is ok with the parameters of both the shelf and the board
            Number = s.nextInt();
            if (Number<=0 || Number>=4 || Number > MaxFromShelfie){
                System.out.println("Retry!");
            }
        }

        if (Number>0 && Number<4){
            System.out.println("Now choose " + Number + " adjacent tiles from the board!");
        }

        for (int i=3; i>3-Number; i--){
            ChosenTiles[i] = new Tile(PossibleColors.DEFAULT);
        }
        //Parte per la vera scelta:
        //individuare a seconda di quante tiles si vuole prendere in totale, le "prendibili"
        //cioè tutte quelle (coppie\triplette\singole tile) che sono tra loro adiacenti e con almeno un lato libero

        return ChosenTiles;
    }
*/
    static int FindMaxAdjacent(int MaxFromShelfie){
        int Max=0;
        int Count=0;
        int MiddleCount=0;
        int[][] Visited = new int[9][9];

        //Initialize the 'Visited' Matrix to all 0
        for(int a=0; a<9; a++){
            for(int b=0; b<9; b++){
                Visited[a][b]=0;
            }
        }

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Count = 0;

                if (!Matrix[i][j].getColor().equals("DEFAULT") && !Matrix[i][j].getColor().equals("DEFAULT")){//se la cella è colorata

                    if(i==0 || i==8 || j==0 || j==8){//se la cella è esterna
                        Count = 1;
                        Visited[i][j]=1;
                    }else{//se la cella è interna, controllo che almeno una delle tile confinanti siano incolore
                        MiddleCount=0;
                        if (i>=1 && MiddleCount==0){
                            if(Matrix[i-1][j].getColor().equals("DEFAULT") || Matrix[i-1][j].getColor().equals("XTILE")){
                                MiddleCount++;
                                Visited[i-1][j]=1;
                            }
                        }
                        if (j>=1 && MiddleCount==0){
                            if(Matrix[i][j-1].getColor().equals("DEFAULT") || Matrix[i][j-1].getColor().equals("XTILE")){
                                MiddleCount++;
                                Visited[i][j-1]=1;
                            }
                        }
                        if (i<=7 && MiddleCount==0){
                            if(Matrix[i+1][j].getColor().equals("DEFAULT") || Matrix[i+1][j].getColor().equals("XTILE")){
                                MiddleCount++;
                                Visited[i+1][j]=1;
                            }
                        }
                        if (j<=7 && MiddleCount==0){
                            if(Matrix[i][j+1].getColor().equals("DEFAULT") || Matrix[i][j+1].getColor().equals("XTILE")){
                                MiddleCount++;
                                Visited[i][j+1]=1;
                            }
                        }

                        if(MiddleCount>0){//porto ad 1 il counter solo se la casella è a tutti gli effetti prendibile
                            Count=1;
                            MiddleCount=0;
                        }
                    }// A questo punto count è 1 se la tile può essere presa

                    //controllo se posso andare oltre e aumentare il counter a 2 o 3,
                    //ma prima verifico che effettivamente mi serva a qualcosa:

                    if (MaxFromShelfie == 1){
                        return 1;
                    }

                    Count = Count + CountAdjacent(i, j, Visited);

                    if (Count > Max){
                        Max=Count;
                    }
                    if(Max>=MaxFromShelfie){
                        return MaxFromShelfie;
                    }
                    if(Max>=3) {
                        return 3;
                    }
                }
            }
        }
        return Max;
    }

    static int CountAdjacent(int i, int j, int[][] Visited){//parte da una posizione dalla matrice che è sicuramente prendibile
        int counter = 0;                                //i e j sono esattamente quelle usate nel metodo chiamante, da cui partire

        if(i>0){
            if(Visited[i-1][j] == 0 && !Matrix[i-1][j].getColor().equals("DEFAULT") && !Matrix[i-1][j].getColor().equals("XTILE")){
                //int up = 0;
                int rowUp=i-1;
                int MiddleCountUp=0;
                if(rowUp==0 || rowUp==8 || j==0 || j==8){
                    MiddleCountUp=1;
                }else{//controllo che la casella sopra abbia almeno una confinante con un void
                    if (rowUp>=1 && MiddleCountUp==0){
                        if(Matrix[rowUp-1][j].getColor().equals("DEFAULT") || Matrix[rowUp-1][j].getColor().equals("XTILE")){
                            MiddleCountUp++;
                            Visited[rowUp-1][j]=1;
                        }
                    }
                    if (j>=1 && MiddleCountUp==0){
                        if(Matrix[rowUp][j-1].getColor().equals("DEFAULT") || Matrix[rowUp][j-1].getColor().equals("XTILE")){
                            MiddleCountUp++;
                            Visited[rowUp][j-1]=1;
                        }
                    }
                    if (rowUp<=7 && MiddleCountUp==0){
                        if(Matrix[rowUp+1][j].getColor().equals("DEFAULT") || Matrix[rowUp+1][j].getColor().equals("XTILE")){
                            MiddleCountUp++;
                            Visited[rowUp+1][j]=1;
                        }
                    }
                    if (j<=7 && MiddleCountUp==0){
                        if(Matrix[rowUp][j+1].getColor().equals("DEFAULT") || Matrix[rowUp][j+1].getColor().equals("XTILE")){
                            MiddleCountUp++;
                            Visited[rowUp][j+1]=1;
                        }
                    }

                    if(MiddleCountUp>0){
                        counter++;
                    }
                }
            }


            Visited[i-1][j]=1;
            counter = counter + CountAdjacent(i+i, j, Visited)
        }

        return counter;
    }
