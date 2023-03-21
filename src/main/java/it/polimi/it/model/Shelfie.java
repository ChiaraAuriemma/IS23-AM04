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

        for(int row=0; row<6; row++){
            for(int column=0; column<5; column++){
                Shelf[row][column] = new Tile(PossibleColors.DEFAULT);
            }
        }

        this.PersonalCardID = PersonalCardID;

        this.Player = Nickname;

        this.EndToken1 = false;

        this.CommonToken1 = 0;

        this.CommonToken2 = 0;

    }


    public String getCell(int row, int column){
        return Shelf[column][row].getColor();
    }

    static int[] ChooseColumn(int count){

        int[] checkColumn = {0,0,0,0,0};

        for(int column=0; column<5; column++){
            int numDefault = 0;
            for(int row=0; row<6; row++){
                if(Shelf[row][column].getColor().equals("DEFAULT")){
                    numDefault++;
                }
            }
            if(numDefault >= count){
                checkColumn[column] = 1;
            }
        }

        return checkColumn;
    }

    static void AddTile(int column, String[] order, int count){

        int numTiles = 0;

        for(int row=0; row<6; row++)
        {
            if(numTiles < count - 1) {
                if (Shelf[row][column].getColor().equals( "DEFAULT")) {
                    Shelf[row][column] = new Tile(PossibleColors.valueOf(order[row]));
                    numTiles++;
                }
            }
        }

    }

    boolean CheckEnd(){

        int count = 0;

        for(int row=0; row<6; row++){
            for(int  column=0; column<5; column++){
                if(Shelf[row][column].getColor().equals("DEFAULT")){
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

        for(int column=0; column<5; column++){
            for(int row=0; row<6; row++){
                if(Shelf[row][column].getColor().equals("DEFAULT")){
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
