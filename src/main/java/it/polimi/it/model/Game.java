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
    private Integer orderPointer;


    private ArrayList<Integer> points;
    private List<PersonalGoalCard> cards;
    private CommonGoalCard card1;
    private CommonGoalCard card2;


    private List<Integer> checkPersonalScore;
    private List<Integer> commonToken1;
    private List<Integer> commonToken2;
    private  Integer endToken;
    private Integer gameID;




    public Game(Integer numplayers, User host, int gameID){
        this.order = new ArrayList<Integer>(numplayers);
        this.orderPointer = 0;
        this.endToken = -1;
        this.numplayers = numplayers;
        this.gameID = gameID;

        host.setGame(this);
        this.players = new ArrayList<>(numplayers);
        this.players.add(host); // controllo se è empty ???

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
            this.cards = new ArrayList<>(2);

        }else if(numplayers == 3){
            this.board = new B3P();

            this.commonToken1.set(1,6);
            this.commonToken1.set(3,0);
            this.commonToken2.set(1,6);
            this.commonToken2.set(3,0);


            this.cards = new ArrayList<>(3);

        }else{
            this.board = new B4P();

            this.commonToken1.set(1,6);
            this.commonToken1.set(3,2);
            this.commonToken2.set(1,6);
            this.commonToken2.set(3,2);


            this.cards = new ArrayList<>(4);

        }

        //view: mostro alla view

    }

    public List<User> getPlayerList() {
        List<User> tmpOrder = new ArrayList<>(numplayers);
        randomPlayers();
        for (int i=0; i<order.size(); i++){
            tmpOrder.add(players.get(order.get(i)));
        }
        return tmpOrder;
    }

    private void randomPlayers () {
        this.orderPointer = 0;
        Random rdn = new Random();

        boolean[] checkplayers;
        checkplayers = new boolean[]{false, false, false, false};

        for(int i=this.numplayers - 1; i > 0; i--){
            int position;
            do{position = rdn.nextInt(i);
            }while(!checkplayers[position]);

            checkplayers[position] = true;
            Integer pos = position;
            this.order.add(i,pos);
        }
        int j = 0;
        while(checkplayers[j]) j++;

        Integer pos = j;
        this.order.add(0,pos);
    }


    public void callNextPlayers(){
        Integer nextplayer = this.order.get(orderPointer);
        int maxTiles;

        if(this.endToken != -1 && this.orderPointer == 0){

            //points from adjacent tiles with same color
            for(int i=0; i < this.numplayers; i++){
                this.points.set(i, this.points.get(i) + this.players.get(i).getShelfie().checkAdjacentsPoints());
            }
            //view: game finisce mostro a video la classifica finale
        }else{

            maxTiles = players.get(nextplayer).maxValueOfTiles();
            //view: gestisco l'avvio del turno (notifico alla view)
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
            //points from endToken
            this.points.add(3, this.points.get(3) + 1);
        }else{
            this.endToken = orderPointer - 1;
            //points from endToken
            this.points.set(orderPointer - 1, this.points.get(orderPointer - 1) + 1);
        }
        //view: mostro alla view chi ha finito per primo
        //view: mostro alla view anche che è stato messo il punto in più

        callNextPlayers();
    }

    public void drawCommonCrads(){

        Random rnd = new Random();

        CommonDeck deck = new CommonDeck();

        int c1;
        int c2;

        do{
            c1 = rnd.nextInt(12) + 1;
            c2 = rnd.nextInt(12) + 1;
        }while(c1 == c2);

        deck.createCards(c1,c2);
        this.card1 = deck.getCommonCard1();
        this.card2 = deck.getCommonCard2();
    }

    Board getBoard(){
        return this.board;
    }

    public void joinGame (User joiner ){

        joiner.setGame(this);
        this.players.add(joiner);
    }

    public int getCurrentPlayersNum(){
        return this.players.size();
    }


    public List<Integer> getOrder() {
        return order;
    }


    public void drawPersonalCard (){

        PersonalGoalCard card;
        Random rnd = new Random();
        Integer id;


        do{
            id = rnd.nextInt(12) + 1;
            card  = new PersonalGoalCard(id);
        }while(!this.cards.contains(card));

        this.cards.add(card);
        //return card;
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
        if(player.getShelfie().getCommonToken1() == 0 && card1.checkGoal(shelfie) ){

            int j=0;
            while(this.commonToken1.get(j) == 0){
                j++;
            }

            player.getShelfie().setCommonToken1(this.commonToken1.get(i));
            this.points.set(i, this.points.get(i) + this.commonToken1.get(i));
            this.commonToken1.set(i, 0);
        }

        //points from common card 2
        if(player.getShelfie().getCommonToken2() == 0 && card2.checkGoal(shelfie) ){

            int j=0;
            while(this.commonToken2.get(j) == 0){
                j++;
            }

            player.getShelfie().setCommonToken2(this.commonToken2.get(i));
            this.points.set(i, this.points.get(i) + this.commonToken2.get(i));
            this.commonToken2.set(i, 0);
        }



        //view: mostro alla view
    }


    //methods for testing----------------------------------------------------

    public Integer getOrderPointer(){
        return this.orderPointer;
    }

    public Integer getEndToken(){
        return this.endToken;
    }

    public Integer getNumplayers(){
        return this.numplayers;
    }

    public User getPlayer(int i){
        return this.players.get(i);
    }

    public Integer getPoint(int i){
        return this.points.get(i);
    }

    public Integer getCheckPersonalScore (int i){
        return this.checkPersonalScore.get(i);
    }

    public Integer getCommonToken1(int i){
        return this.commonToken1.get(i);
    }

    public Integer getCommonToken2(int i){
        return  this.commonToken2.get(i);
    }

    public int getGameid(){
        return this.gameID;
    }


}