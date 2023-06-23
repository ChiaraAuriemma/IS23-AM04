package it.polimi.it.model;

import it.polimi.it.model.Card.CommonGoalCards.CommonDeck;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    Integer numplayers;
    User host;

    VirtualViewStub virtualViewStub;

    @DisplayName("testing constructor")
    @ParameterizedTest
    @ValueSource(ints = {2,3,4})
    void testConstructor(int n){


        this.host = new User("Giacomo");
        this.numplayers = n;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);


        assertEquals(-1,game.getEndToken());
        assertEquals(numplayers, game.getNumplayers());
        assertEquals(1,game.getGameid());
        assertSame(virtualViewStub,game.getVirtualView());

        assertSame(game, host.getGame());


        assertSame(this.host, game.getPlayer(0));

        for(int i=0; i < numplayers; i++){
            assertEquals(0, game.getPoint(i));
        }

        for(int i=0; i < numplayers; i++){
            assertEquals(0, game.getCheckPersonalScore(i));
        }

        /*for(int i=0; i < numplayers; i++){
            assertNotNull(game.getPersonalCard(i));
        }*/

        assertNotNull(game.getBoard());

        //testing common token 1 and common token 2

        assertEquals(8,game.getCommonToken1(0));
        assertEquals(4,game.getCommonToken1(2));

        assertEquals(8,game.getCommonToken2(0));
        assertEquals(4,game.getCommonToken2(2));

        if(numplayers == 2){
            assertEquals(0,game.getCommonToken1(1));
            assertEquals(0,game.getCommonToken1(3));

            assertEquals(0,game.getCommonToken2(1));
            assertEquals(0,game.getCommonToken2(3));
        }else if(numplayers == 3){
            assertEquals(6,game.getCommonToken1(1));
            assertEquals(0,game.getCommonToken1(3));

            assertEquals(6,game.getCommonToken2(1));
            assertEquals(0,game.getCommonToken2(3));
        }else{
            assertEquals(6,game.getCommonToken1(1));
            assertEquals(2,game.getCommonToken1(3));

            assertEquals(6,game.getCommonToken2(1));
            assertEquals(2,game.getCommonToken2(3));
        }

        //testing initialization of points and checkPersonalScore
        for(int i=0; i < game.getNumplayers(); i++){
            assertEquals(0, game.getPoint(i));
            assertEquals(0, game.getCheckPersonalScore(i));
        }

    }


    @Test
    void testJoin(){
        this.host = new User("Giacomo");
        this.numplayers = 4;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);

        User joiner1 = new User("Alberto");
        User joiner2 = new User("Chiara");
        User joiner3 = new User("Francesco");

        game.joinGame(joiner1);
        game.joinGame(joiner2);
        game.joinGame(joiner3);


        assertSame(joiner1 , game.getPlayer(1));
        assertSame(joiner2 , game.getPlayer(2));
        assertSame(joiner3 , game.getPlayer(3));

        assertSame(game,game.getPlayer(1).getGame());
        assertSame(game,game.getPlayer(2).getGame());
        assertSame(game,game.getPlayer(3).getGame());

    }


    @Test
    void testRandom(){
        this.host = new User("Giacomo");
        this.numplayers = 4;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);

        User joiner1 = new User("Alberto");
        User joiner2 = new User("Chiara");
        User joiner3 = new User("Francesco");

        game.joinGame(joiner1);
        game.joinGame(joiner2);
        game.joinGame(joiner3);

        ArrayList<User> order = game.randomPlayers();

        assertEquals(4, order.size());

        assertTrue(order.contains(host));
        assertTrue(order.contains(joiner1));
        assertTrue(order.contains(joiner2));
        assertTrue(order.contains(joiner3));
    }

    @Test
    void testCommonCards(){
        this.host = new User("Giacomo");
        this.numplayers = 4;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);

        User joiner1 = new User("Alberto");
        User joiner2 = new User("Chiara");
        User joiner3 = new User("Francesco");

        game.joinGame(joiner1);
        game.joinGame(joiner2);
        game.joinGame(joiner3);
        game.drawCommonCards();

        assertNotNull(game.getCommonCard1());
        assertNotNull(game.getCommonCard2());
    }

    @ParameterizedTest
    @ValueSource(ints = {2,3,4})
    void testPersonalCards(int n){
        this.host = new User("Giacomo");
        this.numplayers = n;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);

        User joiner1 = new User("Alberto");
        User joiner2 = new User("Chiara");
        User joiner3 = new User("Francesco");

        game.joinGame(joiner1);
        game.joinGame(joiner2);
        game.joinGame(joiner3);

        for(int i=0; i < this.numplayers; i++){
            game.drawPersonalCard();
        }

        for(int i=0; i < this.numplayers; i++){
            assertNotNull(game.getPersonalCard(i));
        }

    }

    @Test
    void testSwapPlayers(){
        this.host = new User("Giacomo");
        this.numplayers = 2;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);

        User joiner1 = new User("Alberto");
        game.joinGame(joiner1);

        User newborn1 = new User("Francesco");
        assertSame(game.getPlayer(0),host);
        game.swapPlayers(host,newborn1);
        assertSame(game.getPlayer(0),newborn1);

        User newborn2 = new User("Chiara");
        assertSame(game.getPlayer(1),joiner1);
        game.swapPlayers(joiner1,newborn2);
        assertSame(game.getPlayer(1),newborn2);
    }

    @Test
    void testEndGame(){
        this.host = new User("Giacomo");
        this.numplayers = 2;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);

        User joiner1 = new User("Alberto");
        game.joinGame(joiner1);

        assertEquals(0,game.getPoint(joiner1));
        game.endGame(joiner1);
        assertEquals(1,game.getPoint(joiner1));
        assertEquals(1,game.getEndToken());
    }

    @Test
    void testPointsFromAdjacent(){
        this.host = new User("Giacomo");
        this.numplayers = 2;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);

        User joiner1 = new User("Alberto");
        game.joinGame(joiner1);

        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[1][0] = new Tile(1,0,PossibleColors.YELLOW);
        matrix[2][0] = new Tile(2,0,PossibleColors.GREEN);
        matrix[3][0] = new Tile(3, 0,PossibleColors.YELLOW);
        matrix[4][0] = new Tile(4,0,PossibleColors.GREEN);
        matrix[5][0] = new Tile(5,0,PossibleColors.YELLOW);

        matrix[0][1] = new Tile(0, 1, PossibleColors.BLUE);
        matrix[1][1] = new Tile(1,1,PossibleColors.BLUE);
        matrix[2][1] = new Tile(2,1,PossibleColors.BLUE);
        matrix[3][1] = new Tile(3, 1,PossibleColors.BLUE);
        matrix[4][1] = new Tile(4,1,PossibleColors.YELLOW);
        matrix[5][1] = new Tile(5,1,PossibleColors.BLUE);

        matrix[0][2] = new Tile(0, 2, PossibleColors.CYAN);
        matrix[1][2] = new Tile(1,2,PossibleColors.GREEN);
        matrix[2][2] = new Tile(2,2,PossibleColors.YELLOW);
        matrix[3][2] = new Tile(3, 2,PossibleColors.WHITE);
        matrix[4][2] = new Tile(4,2,PossibleColors.PINK);
        matrix[5][2] = new Tile(5,2,PossibleColors.PINK);

        matrix[0][3] = new Tile(0, 3, PossibleColors.YELLOW);
        matrix[1][3] = new Tile(1,3,PossibleColors.CYAN);
        matrix[2][3] = new Tile(2,3,PossibleColors.WHITE);
        matrix[3][3] = new Tile(3, 3,PossibleColors.WHITE);
        matrix[4][3] = new Tile(4,3,PossibleColors.WHITE);
        matrix[5][3] = new Tile(5,3,PossibleColors.GREEN);

        matrix[0][4] = new Tile(0, 4, PossibleColors.GREEN);
        matrix[1][4] = new Tile(1,4,PossibleColors.YELLOW);
        matrix[2][4] = new Tile(2,4,PossibleColors.WHITE);
        matrix[3][4] = new Tile(3, 4,PossibleColors.PINK);
        matrix[4][4] = new Tile(4,4,PossibleColors.WHITE);
        matrix[5][4] = new Tile(5,4,PossibleColors.YELLOW);

        game.getPlayer(0).getShelfie().setShelf(matrix);

        Tile[][] matrix1 = new Tile[6][5];

        matrix1[0][0] = new Tile(0, 0, PossibleColors.CYAN);
        matrix1[1][0] = new Tile(1,0,PossibleColors.CYAN);
        matrix1[2][0] = new Tile(2,0,PossibleColors.GREEN);
        matrix1[3][0] = new Tile(3, 0,PossibleColors.BLUE);
        matrix1[4][0] = new Tile(4,0,PossibleColors.BLUE);
        matrix1[5][0] = new Tile(5,0,PossibleColors.BLUE);

        matrix1[0][1] = new Tile(0, 1, PossibleColors.GREEN);
        matrix1[1][1] = new Tile(1,1,PossibleColors.GREEN);
        matrix1[2][1] = new Tile(2,1,PossibleColors.CYAN);
        matrix1[3][1] = new Tile(3, 1,PossibleColors.BLUE);
        matrix1[4][1] = new Tile(4,1,PossibleColors.WHITE);
        matrix1[5][1] = new Tile(5,1,PossibleColors.YELLOW);

        matrix1[0][2] = new Tile(0, 2, PossibleColors.GREEN);
        matrix1[1][2] = new Tile(1,2,PossibleColors.GREEN);
        matrix1[2][2] = new Tile(2,2,PossibleColors.CYAN);
        matrix1[3][2] = new Tile(3, 2,PossibleColors.WHITE);
        matrix1[4][2] = new Tile(4,2,PossibleColors.WHITE);
        matrix1[5][2] = new Tile(5,2,PossibleColors.WHITE);

        matrix1[0][3] = new Tile(0, 3, PossibleColors.CYAN);
        matrix1[1][3] = new Tile(1,3,PossibleColors.YELLOW);
        matrix1[2][3] = new Tile(2,3,PossibleColors.PINK);
        matrix1[3][3] = new Tile(3, 3,PossibleColors.PINK);
        matrix1[4][3] = new Tile(4,3,PossibleColors.PINK);
        matrix1[5][3] = new Tile(5,3,PossibleColors.PINK);

        matrix1[0][4] = new Tile(0, 4, PossibleColors.GREEN);
        matrix1[1][4] = new Tile(1,4,PossibleColors.YELLOW);
        matrix1[2][4] = new Tile(2,4,PossibleColors.YELLOW);
        matrix1[3][4] = new Tile(3, 4,PossibleColors.GREEN);
        matrix1[4][4] = new Tile(4,4,PossibleColors.YELLOW);
        matrix1[5][4] = new Tile(5,4,PossibleColors.YELLOW);

        game.getPlayer(1).getShelfie().setShelf(matrix1);

        game.pointsFromAdjacent();

        assertEquals(11,game.getPoint(host));
        assertEquals(14,game.getPoint(joiner1));
    }

    @Test
    void testPointCount(){
        this.host = new User("Giacomo");
        this.numplayers = 2;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);
        User joiner = new User("Francesco");
        this.game.joinGame(joiner);

        this.game.addPersonalCard(1,0);
        this.game.addPersonalCard(12,1);
        CommonDeck commonDeck = new CommonDeck();
        commonDeck.createCards(5,1);
        this.game.setCard1(commonDeck.getCommonCard1());
        this.game.setCard2(commonDeck.getCommonCard2());

        int column, row;
        Tile [][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = new Tile(row, column,PossibleColors.PINK);
            }
        }

        matrix[5][0] = new Tile(5, 0,PossibleColors.PINK);
        matrix[0][2] = new Tile(0, 2,PossibleColors.YELLOW);
        matrix[2][1] = new Tile(2, 1,PossibleColors.CYAN);
        matrix[5][2] = new Tile(5, 2,PossibleColors.BLUE);
        matrix[3][3] = new Tile(3, 3,PossibleColors.GREEN);
        matrix[4][4] = new Tile(4, 4,PossibleColors.GREEN);

        host.getShelfie().setShelf(matrix);

        game.pointCount(host);
        assertEquals(20,game.getPoint(0));


        Tile [][] matrix1 = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix1[row][column] = new Tile(row, column,PossibleColors.BLUE);
            }
        }

        matrix1[4][1] = new Tile(4, 1,PossibleColors.PINK);
        matrix1[2][3] = new Tile(2, 3,PossibleColors.CYAN);
        matrix1[1][4] = new Tile(1, 4,PossibleColors.YELLOW);
        matrix1[3][2] = new Tile(3, 2,PossibleColors.BLUE);
        matrix1[5][2] = new Tile(5, 2,PossibleColors.WHITE);
        matrix1[0][0] = new Tile(0, 0,PossibleColors.GREEN);


        joiner.getShelfie().setShelf(matrix1);

        game.pointCount(joiner);
        assertEquals(20,game.getPoint(1));
    }

    @Test
    public void testlastmethod(){
        this.host = new User("Giacomo");
        this.numplayers = 2;
        this.virtualViewStub = new VirtualViewStub();
        this.game = new Game(numplayers,host,1, virtualViewStub);
        User joiner = new User("Francesco");
        this.game.joinGame(joiner);

        this.game.addPersonalCard(1,0);
        this.game.addPersonalCard(12,1);

        assertEquals(game.getPersonalCard(0),game.getPersonalCard(host));
        assertEquals(game.getPersonalCard(1),game.getPersonalCard(joiner));
        assertEquals(game.getNumplayers(),game.getCurrentPlayersNum());
    }
}