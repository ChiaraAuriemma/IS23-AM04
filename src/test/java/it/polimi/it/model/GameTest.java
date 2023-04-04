package it.polimi.it.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameTest {
    Game game;
    Integer numplayers;
    User host;

    @BeforeEach
    @ValueSource(ints = {2,3,4})
    void setUp(int n) {
        this.host = new User("Piero");
        int numplayers = n;


        game = new Game(numplayers,host);
    }


    @DisplayName("testing constructor")
    @Test
    void testConstructor(){

        assertEquals(0,game.getOrderPointer());
        assertEquals(-1,game.getEndToken());
        assertEquals(numplayers, game.getNumplayers());

        //assertSame(game, host.getGame());

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

    }


    //toglo i nested
    @Nested
    @DisplayName("tesing initialization of points Arraylist")
    class CreationPointsTest{
        @ParameterizedTest
        @ValueSource(ints = {0,1,2,3})
        void points (int i){
            assertEquals(0,game.getPoint(i));
        }
        @Nested
        @DisplayName("testing inizialization of checkPersonalScore ArrayList")
        class CreationCheckPerTest{
            @ParameterizedTest
            @ValueSource(ints = {0,1,2,3})
            void  personalScore(int j){
                assertEquals(0,game.getCheckPersonalScore(j));
            }
        }
    }


    @Nested
    @DisplayName("testing randomplayers methods")
    class RandomPlayer{//devo rendere pubblic random (non c'è un metodo migliore??????)

    }

}