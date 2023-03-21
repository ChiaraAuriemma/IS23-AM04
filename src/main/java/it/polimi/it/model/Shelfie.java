package it.polimi.it.model;

public class Shelfie{

    private String Player;

    private int CommonToken1;

    private int CommonToken2;

    private boolean EndToken1;

    private static Tile[][] Shelf;

    private final int PersonalCardID;

    public Shelfie(int PersonalCardID){

        Shelf = new Tile[6][5];

        for(int row=0; row<6; row++){
            for(int column=0; column<5; column++){
                Shelf[row][column] = new Tile(PossibleColors.DEFAULT);
            }
        }

        this.PersonalCardID = PersonalCardID;

        this.Player = User.getNickname();

        this.EndToken1 = false;

        this.CommonToken1 = 0;

        this.CommonToken2 = 0;

    }


    public String getCell(int column, int row){
        return Shelf[row][column].getColor();
    }

    boolean[] ChooseColumn(int count){

        boolean[] checkColumn = {false, false, false, false, false};
        int numDefault;

        for(int column=0; column<5; column++){
            numDefault = 0;
            for(int row=0; row<6; row++){
                if(Shelf[row][column].getColor().equals("DEFAULT")){
                    numDefault++;
                }
            }
            if(numDefault >= count){
                checkColumn[column] = true;
            }
        }

        return checkColumn;
    }

    void AddTile(int column, String[] order, int count){

        int numTiles = 0;

        for(int row=0; row<6; row++)
        {
            if(numTiles < order.length) {
                if (Shelf[row][column].getColor().equals("DEFAULT")) {
                    Shelf[row][column] = new Tile(PossibleColors.valueOf(order[numTiles]));
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

    int PossibleTiles(){

        int tmp = 0;
        int count;

        for(int column=0; column<5; column++){

            count = 0;

            for(int row=0; row<6; row++){
                if(Shelf[row][column].getColor().equals("DEFAULT")){
                    count ++;
                    if(count >= 3){
                        return 3;
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
