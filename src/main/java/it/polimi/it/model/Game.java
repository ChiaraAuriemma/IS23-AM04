package it.polimi.it.model;

import it.polimi.it.model.Board.B2P;
import it.polimi.it.model.Board.B3P;
import it.polimi.it.model.Board.B4P;
import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Card.CommonGoalCards.*;
import it.polimi.it.model.Card.PersonalGoalCards.*;

import java.util.*;

public class Game {

    private List<User> players;
    private  Integer numplayers;
    private Board board;


    private  List<Integer> order ;
    private  Integer orderPointer;


    private ArrayList<Integer> points;
    private List<PersonalGoalCard> cards;
    private CommonGoalCard card1;
    private CommonGoalCard card2;


    private List<Integer> checkPersonalScore;
    private List<Integer> commonToken1;
    private List<Integer> commonToken2;
    private  Integer endToken;




    public Game(Integer numplayers, User host){
        this.order = new ArrayList<Integer>(numplayers);
        this.orderPointer = 0;
        this.endToken = -1;
        this.numplayers = numplayers;
        host.setGame(this);
        this.players = new ArrayList<>(numplayers);
        this.players.add(0,host); // controllo se è empty ???

        //start all the player points to zero
        this.points = new ArrayList<>(Collections.nCopies(numplayers, 0));
        //start all the players score from personal cards to zero
        this.checkPersonalScore = new ArrayList<>(Collections.nCopies(numplayers,0));


        this.commonToken1 = new ArrayList<>(numplayers);
        this.commonToken2 = new ArrayList<>(numplayers);


        this.commonToken1.add(0,8);
        this.commonToken1.add(1,0);
        this.commonToken1.add(2,4);
        this.commonToken1.add(3,0);

        this.commonToken2.add(0,8);
        this.commonToken2.add(1,0);
        this.commonToken2.add(2,4);
        this.commonToken2.add(3,0);

        if(numplayers == 2){
            this.board = new B2P();

        }else if(numplayers == 3){
            this.board = new B3P();

            this.commonToken1.set(1,6);
            this.commonToken1.set(3,0);
            this.commonToken2.set(1,6);
            this.commonToken2.set(3,0);

        }else{
            this.board = new B4P();

            this.commonToken1.set(1,6);
            this.commonToken1.set(3,2);
            this.commonToken2.set(1,6);
            this.commonToken2.set(3,2);

        }

        //mostro alla view

    }

    private void randomplayers () {
        this.orderPointer = 0;
        Random rdn = new Random();

        boolean[] checkplayers;
        checkplayers = new boolean[]{false, false, false, false};

        for(int i=this.numplayers - 1; i > 0; i--){

            int position = rdn.nextInt(i);
            while(checkplayers[position] == false){
                position = rdn.nextInt(i);
            }

            checkplayers[position] = true;
            Integer pos = Integer.valueOf(position);
            this.order.add(i,pos);
        }

        int j = 0;
        while(checkplayers[j] == true) j++;

        Integer pos = Integer.valueOf(j);
        this.order.add(0,pos);

    }


    void callNextplayers (){
        Integer nextplayer = this.order.get(orderPointer);
        int maxTiles;

        if(this.endToken != -1 && this.orderPointer == 0){
            //game finisce mostro a video la classifica finale
        }else{

            maxTiles = players.get(nextplayer).maxValueOfTiles();
            //gestisco l'avvio del turno (notifico alla view)
            //tipo con:
            //virtualView.play(player.get(nextplayer), maxTiles);

            if(orderPointer == 3){
                orderPointer = 0;
            }else{
                orderPointer ++;
            }
        }

    }

    void endGame (){
        if (orderPointer == 0){
            this.endToken = 3;
            //aggiungo il punto di end
            this.points.add(3, this.points.get(3) + 1);
        }else{
            this.endToken = orderPointer - 1;
            //aggiungo il punto di end
            this.points.set(orderPointer - 1, this.points.get(orderPointer - 1) + 1);
        }
        //mostro alla view chi ha finito per primo
        //mostro alla view anche che è stato messo il punto in più

        callNextplayers();
    }

    private void drawCommonCrads(){

        Random rnd = new Random();

        int c1 = rnd.nextInt(12) + 1;
        int c2 = rnd.nextInt(12) + 1;
        while(c1 == c2){
            c1 = rnd.nextInt(12) + 1;
            c2 = rnd.nextInt(12) + 1;
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

    void joinGame (User joiner ){

        joiner.setGame(this);
        this.players.add(joiner);
        //mostro alla view
        if(this.players.size() == this.numplayers){

            randomplayers();
            this.cards = new ArrayList<>();
            for(int i=0; i < this.numplayers; i++){
                this.cards.add(i,drawPersonalCard());
                //come passo a user la sua carta ???? parlo con bertossss
                this.players.get(i).createShelfie();
            }

            drawCommonCrads();
            //mostro alla view
            //mostro alla view chi ha il sofà d'inizio
            callNextplayers();

        }

        return;
    }

    private PersonalGoalCard drawPersonalCard (){

        PersonalGoalCard card;
        Random rnd = new Random();
        Integer id;


        do{
            id = Integer.valueOf(rnd.nextInt(12) + 1);
            card  = new PersonalGoalCard(id);
        }while(!this.cards.contains(card));

        return card;
    }

    private void pointCount(){
        Integer i = this.order.get(orderPointer - 1);
        User player = this.players.get(i);
        PersonalGoalCard persCard = this.cards.get(i);

        Shelfie shelfie = player.getShelfie();

        //points from personal cards
        Integer personalScore = persCard.checkScore(shelfie);
        while(this.checkPersonalScore.get(i) < personalScore){
            this.checkPersonalScore.set(i,this.checkPersonalScore.get(i) + 1);//va in loop?

            if(this.checkPersonalScore.get(i) <= 2){
                this.points.set(i, this.points.get(i) + 1);
            }else if(this.checkPersonalScore.get(i) >= 3 && this.checkPersonalScore.get(i) <= 4){
                this.points.set(i, this.points.get(i) + 2);
            }else {
                this.points.set(i, this.points.get(i) + 3);
            }
        }
        //i punti dell'endtoken sono dati dal metodo end game

        //points from common card 1
        if(player.getCommonToken1() == 0 && card1.CheckGoal(shelfie) ){

            int i=0;
            while(this.commonToken1.get(i) == 0){
                i++;
            }

            player.setCommonToken1(this.commonToken1.get(i));
            this.points.set(i, this.points.get(i) + this.commonToken1.get(i));
            this.commonToken1.set(i, 0);
        }

        //points from common card 2
        if(player.getCommonToken2() == 0 && card2.CheckGoal(shelfie) ){

            int i=0;
            while(this.commonToken2.get(i) == 0){
                i++;
            }

            player.setCommonToken2(this.commonToken2.get(i));
            this.points.set(i, this.points.get(i) + this.commonToken2.get(i));
            this.commonToken2.set(i, 0);
        }


        //punti per le caselle adiacenti ?? chiedo cosa ha fatto albertone
        //mostro alla view
    }
    // IMPOARTE :     checkAdjacentsPoints() di player

}
