package it.polimi.it.model;

public class Shelfie{

    private String Player;

    private int CommonToken1;

    private int CommonToken2;

    private boolean EndToken1;

    private static Tile[][] Shelf;

    private int PersonalCardID;


    public Shelfie(String Nickname, int PersonalCardID){

        Shelf = new Tile[6][5];

        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                Shelf[i][j] = new Tile(PossibleColors.DEFAULT);
            }
        }

        this.PersonalCardID = PersonalCardID;

        this.Player = Nickname;

        this.EndToken1 = false;

        this.CommonToken1 = 0;

        this.CommonToken2 = 0;

    }


    String getCell(int i, int j){
        return Shelf[j][i].getColor();
    }

    static int[] ChooseColumn(int count){

        int[] checkColumn = {0,0,0,0,0};

        for(int j=0; j<5; j++){
            int numDefault = 0;
            for(int i=0; i<6; i++){
                if(Shelf[i][j].getColor().equals("DEFAULT")){
                    numDefault++;
                }
            }
            if(numDefault >= count){
                checkColumn[j] = 1;
            }
        }

        return checkColumn;
    }

    static void AddTile(int column, String[] order, int count){

        int numTiles = 0;

        for(int i=0; i<6;i++)
        {
            if(numTiles < count - 1) {
                if (Shelf[i][column].getColor().equals( "DEFAULT")) {
                    Shelf[i][column] = new Tile(PossibleColors.valueOf(order[i]));
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

    static int PossibleTiles(){
        int tmp = 0;
        int count = 0;

        for(int j=0; j<5; j++){
            for(int i=0; i<5; i++){
                if(Shelf[i][j].getColor().equals("DEFAULT")){
                    count ++;
                    if(count >= 3){
                        return count;
                    }
                }
            }
            if(count > tmp){
                tmp = count;
            }
        }


        return tmp;
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
