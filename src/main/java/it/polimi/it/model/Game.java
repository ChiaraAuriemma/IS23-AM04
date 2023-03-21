package it.polimi.it.model;

import it.polimi.it.model.Board.B2P;
import it.polimi.it.model.Board.B3P;
import it.polimi.it.model.Board.B4P;
import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;

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
        DrawCommonCrads();
        this.OrderPointer=0;
        while(///end game????)



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

        int c1 = rnd.nextInt(11) + 1;
        int c2 = rnd.nextInt(11) + 1;
        while(c1 == c2){
            c1 = rnd.nextInt(11) + 1;
            c2 = rnd.nextInt(11) + 1;
        }

        //instance of common card 1
        if(c1 == 1) {
            card1 = new CommonGoalCard1();
        }else if (c1 == 2){
            card1 = new CommonGoalCard2();
        }else if (c1 == 3){
            card1 = new CommonGoalCard3();
        }else if (c1 == 4){
            card1 = new CommonGoalCard4();
        }else if (c1 == 5){
            card1 = new CommonGoalCard5();
        }else if (c1 == 6){
            card1 = new CommonGoalCard6();
        }else if (c1 == 7){
            card1 = new CommonGoalCard7();
        }else if (c1 == 8){
            card1 = new CommonGoalCard8();
        }else if (c1 == 9){
            card1 = new CommonGoalCard9();
        }else if (c1 == 10){
            card1 = new CommonGoalCard10();
        }else if (c1 == 11){
            card1 = new CommonGoalCard11();
        }else if (c1 == 12){
            card1 = new CommonGoalCard12();
        }

        //instance of common card 2
        if(c2 == 1) {
            card2 = new CommonGoalCard1();
        }else if (c2 == 2){
            card2 = new CommonGoalCard2();
        }else if (c2 == 3){
            card2 = new CommonGoalCard3();
        }else if (c2 == 4){
            card2 = new CommonGoalCard4();
        }else if (c2 == 5){
            card2 = new CommonGoalCard5();
        }else if (c2 == 6){
            card2 = new CommonGoalCard6();
        }else if (c2 == 7){
            card2 = new CommonGoalCard7();
        }else if (c2 == 8){
            card2 = new CommonGoalCard8();
        }else if (c2 == 9){
            card2 = new CommonGoalCard9();
        }else if (c2 == 10){
            card2 = new CommonGoalCard10();
        }else if (c2 == 11){
            card2 = new CommonGoalCard11();
        }else if (c2 == 12){
            card2 = new CommonGoalCard12();
        }

    }

    //metodi in più
    Board getBoard(){
        return this.board;
    }
}
