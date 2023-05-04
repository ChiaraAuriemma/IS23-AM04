/*package it.polimi.it.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    Integer numplayers;
    User host;
*/

    //levo ????
    /*@BeforeEach
    @ParameterizedTest
    @ValueSource(ints = {2,3,4})
    void setUp(int n) {
        this.host = new User("Giacomo");
        this.numplayers = new Integer(n);
        this.game = new Game(numplayers,host,1);
    }*/

/*
    @DisplayName("testing constructor")
    @ParameterizedTest
    @ValueSource(ints = {2,3,4})
    void testConstructor(int n){

        this.host = new User("Giacomo");
        this.numplayers = new Integer(n);
        this.game = new Game(numplayers,host,1);

        assertEquals(-1,game.getEndToken());
        assertEquals(numplayers, game.getNumplayers());
        assertEquals(1,game.getGameid());

        assertSame(game, host.getGame());


        assertSame(this.host, game.getPlayer(0));

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
        this.game = new Game(numplayers,host,1);

        User joiner1 = new User("Alberto");
        User joiner2 = new User("Chiara");
        User joiner3 = new User("Francesco");

        game.joinGame(joiner1);
        game.joinGame(joiner2);
        game.joinGame(joiner3);


        assertSame(joiner1 , game.getPlayer(1));
        assertSame(joiner2 , game.getPlayer(2));
        assertSame(joiner3 , game.getPlayer(3));
    }


    @Test
    void testRandom(){
        this.host = new User("Giacomo");
        this.numplayers = 4;
        this.game = new Game(numplayers,host,1);

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

    @Test //miglioro
    void testCommonCards(){
        this.host = new User("Giacomo");
        this.numplayers = 4;
        this.game = new Game(numplayers,host,1);

        game.drawCommonCrads();

        assertNotNull(game.getCommonCard1());
        assertNotNull(game.getCommonCard2());
    }

    @ParameterizedTest //miglioro
    @ValueSource(ints = {2,3,4})
    void testPersonalCards(int n){
        this.host = new User("Giacomo");
        this.numplayers = new Integer(n);
        this.game = new Game(numplayers,host,1);

        for(int i=0; i < this.numplayers; i++){
            game.drawPersonalCard();
        }

        for(int i=0; i < this.numplayers; i++){
            assertNotNull(game.getPersonalCard(i));
        }

    }


}
        */