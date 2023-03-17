package it.polimi.it.model;

import java.util.ArrayList;

import java.util.Optional;
import java.util.List;
import java.util.Random;

public class Game {

    private User Player1;
    private User Player2;
    private Optional<User> Player3;
    private Optional<User> Player4;

    private  List<Integer> Order ;
    private  Integer OrderPointer;

    private  Integer NumPlayer;

    private  Integer EndToken;

    //altri attributi
    private Board board;

    private CommonGoalCard card1;
    private CommonGoalCard card2;

    public Game(Integer NumPlayer){
        this.Order = new ArrayList<Integer>(NumPlayer);
        this.NumPlayer = NumPlayer;

        if(NumPlayer == 2){
            this.board = new B2P();
        }else if(NumPlayer == 3){
            this.board = new B3P();
        }else{
            this.board = new B4P();
        }

        //wait che i giocatori entrino nel gioco
        RandomPlayer();


    }

    private void RandomPlayer () {
        this.OrderPointer = 0;
        Random rdn = new Random();

        boolean[] CherckPlayer;
        CherckPlayer = new boolean[]{false, false, false, false};

        for(int i=this.NumPlayer - 1; i > 0; i--){

            int position = rdn.nextInt(i);
            while(CherckPlayer[position] == false){
                position = rdn.nextInt(i);
            }

            CherckPlayer[position] = true;
            Integer pos = Integer.valueOf(position + 1);
            this.Order.add(i,pos);
        }

        int j = 0;
        while(CherckPlayer[j] == true) j++;

        Integer pos = Integer.valueOf(j+1);
        this.Order.add(0,pos);

    }


    void CallNextPlayer (){
        Integer NextPlayer = this.Order.get(OrderPointer);

        if(NextPlayer == 1){
            Player1.Play();
        }else if (NextPlayer == 2{
            Player2.Play();
        }else if (NextPlayer == 3){
            Player3.Play();
        }else{
            Player4.Play();
        }


        if(OrderPointer == 3){
            OrderPointer = 0;
        }else{
            OrderPointer ++;
        }
    }

    //questa classe sarà chiamta da CreateGame quindi va bene anche privata
    private void DrawCommonCrads(){

        Random rnd = new Random();

        int c1 = rnd.nextInt(12);
        int c2 = rnd.nextInt(12);
        while(c1 == c2){
            int c1 = rnd.nextInt(12);
            int c2 = rnd.nextInt(12);
        }

        // lo faccio io o lo fa la classe commongoalcard
        if()
    }

    //metodi in più
    Board getBoard(){
        return this.board;
    }
}
