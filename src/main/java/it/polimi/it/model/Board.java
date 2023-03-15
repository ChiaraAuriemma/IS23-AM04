package it.polimi.it.model;

public abstract class Board {
    //The maximum dimension of the board is a 9x9 matrix


    public Tile[][] Matrix;
    public int NumPlayers;
    public int CommonGoalCard1;
    public int CommonGoalCard2;





    //sto metodo com'è?? friendly/protected/....
    void Refill(){
        for

    }

    private Boolean CheckRefill(){
        // devo refillare se trovo delle Tiles che siano contornate da nessuna tile colorata
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if(Matrix[i][j].Color.getColor.equals("DEFAULT") ){

                }
            }
        }
    }
}
