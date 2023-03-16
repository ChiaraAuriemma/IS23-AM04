package it.polimi.it.model;

public class Shelfie{

    private String Player;

    private int CommonToken1;

    private int CommonToken2;

    private boolean EndToken1;

    private Tile[][] Shelf;

    private int PersonalCardID;


    public Shelfie(String Nickname, int PersonalCardID){

        Shelf = new Tile[6][5];

        for(int i=0; i<6; i++){
            for(int j=0; i<5; i++){
                Shelf[i][j] = new Tile(PossibleColors.DEFAULT);
            }
        }

        this.PersonalCardID = PersonalCardID;

        this.Player = Nickname;

        this.EndToken1 = false;

        this.CommonToken1 = 0;

        this.CommonToken2 = 0;

    }


    PossibleColors getCell(int i, int j){
        return Shelf[i][j].getColor();
    }

    int ChooseColumn(){

        int count = User.getCount();

        int column;

        int[] checkColumn = {0,0,0,0,0};

        for(int j=0; i<6; i++){
            int numDefault = 0;
            for(int i=0; i<5; i++){
                if(Shelf[i][j].getColor()=="DEFAULT"){
                    numDefault++;
                }
            }
            if(numDefault >= count){
                checkColumn[j] = 1;
            }
        }

            column = 5; //settare valore in base a scelta utente
        //far selezionare a player le colonne

        return column;
    }

    void AddTile(int column){

        boolean check = false;
        int count = User.getCount();

        while(check = false) {

            System.out.println("Inserisci l'ordine in cui vuoi inserire le tessere: ");

            //if valori non vanno bene fai ripartire

            //salvare ordine
        }

        Tile[] ChoosenTiles = Board.getChoosenTiles();

        int numTiles = 0;

        for(int i=0; i<6;i++)
        {
            if(numTiles < count - 1) {
                if (Shelf[i][column] == "DEFAULT") {
                    Shelf[i][column] = ChoosenTiles[numTiles].getColor();
                    numTiles++;
                }
            }
        }

    }

    boolean CheckEnd(){

        int count = 0;

        for(int i=0; i<6; i++){
            for(int j=0; i<5; i++){
                if(Shelf[i][j].getColor().equals("DEFAULT")){
                    count ++;
                }
            }
        }
        if(count == 0){
            EndToken1 = true;
        }

        return EndToken1;

    }

    int getPersonalCardID(){
        return PersonalCardID;
    }

    void setCommonToken1(int val){
        CommonToken1 = val;
    }

    void setCommonToken2(int val){
        CommonToken2 = val;
    }

    int getCommonToken1(){
        return CommonToken1;
    }

    int getCommonToken2(){
        return CommonToken2;
    }
}
