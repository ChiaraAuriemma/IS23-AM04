package it.polimi.it.model;

import java.util.Scanner;

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
        return Shelf[i][j].getColor();
    }

    static int ChooseColumn(int count){

        int column;

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


        System.out.println("Scegli in che colonna inserire le tessere");
        Scanner s = new Scanner(System.in);
        column = s.nextInt();

        return column;
    }

    static void AddTile(int column, Tile[] ChoosenTiles, int count) throws InvalidOrderException{

        boolean check = false;

        while(!check) {

            int Number1;
            int Number2;
            int Number3;

            System.out.println("Inserisci l'ordine in cui vuoi inserire le tessere: ");

            switch (count){
                case 1:
                    Scanner s = new Scanner(System.in);
                    Number1 = s.nextInt();

                    //fare eccezione per valori sbagliati nell'ordine
                    check=true;
                    break;
                case 2:
                    Scanner s1 = new Scanner(System.in);
                    Number1 = s1.nextInt();

                    //fare eccezione per valori sbagliati nell'ordine
                    Scanner s2 = new Scanner(System.in);
                    Number2 = s2.nextInt();

                    //fare eccezione per valori sbagliati nell'ordine
                    check=true;
                    break;
                case 3:
                    Scanner s3 = new Scanner(System.in);
                    Number1 = s3.nextInt();

                    //fare eccezione per valori sbagliati nell'ordine

                    Scanner s4 = new Scanner(System.in);
                    Number2 = s4.nextInt();

                    //fare eccezione per valori sbagliati nell'ordine

                    Scanner s5 = new Scanner(System.in);
                    Number3 = s5.nextInt();

                    //fare eccezione per valori sbagliati nell'ordine

                    check=true;
                    break;

                default:
                    //eccezione per numero sbagliato di count

            }


        }

        int numTiles = 0;

        for(int i=0; i<6;i++)
        {
            if(numTiles < count - 1) {
                if (Shelf[i][column].getColor().equals( "DEFAULT")) {
                    Shelf[i][column] = new Tile(PossibleColors.ChoosenTiles[numTiles].getColor());
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
