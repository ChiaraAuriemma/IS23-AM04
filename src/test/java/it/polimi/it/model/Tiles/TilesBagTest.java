package it.polimi.it.model.Tiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TilesBagTest {


    private TilesBag bag;

    @Before
    public void B2P() {
        this.bag= new TilesBag();
    }

    @After
    public void destroy() {
        bag = null;
    }

    @Test
    public void buildTest(){
        assertEquals(132, bag.getTotRemaining());

        Tile t = bag.randomTiles(1, 2);
        assertEquals(131, bag.getTotRemaining());

        Tile x = new Tile(PossibleColors.XTILE);
        Tile d = new Tile(PossibleColors.DEFAULT);

        assertNotEquals(x.getColor(), t.getColor());
        assertNotEquals(d.getColor(), t.getColor());
        assertEquals(1, t.getRow());
        assertEquals(2, t.getColumn());
    }

    @Test
    public void bagDest(){
        Tile t;

        Tile x = new Tile(PossibleColors.XTILE);
        Tile d = new Tile(PossibleColors.DEFAULT);

        int rem = 132;

        while(bag.getTotRemaining()!=0){
            t = bag.randomTiles(1, 2);
            rem --;
            assertNotEquals(x.getColor(), t.getColor());
            assertNotEquals(d.getColor(), t.getColor());
            assertEquals(1, t.getRow());
            assertEquals(2, t.getColumn());
            assertEquals(rem, bag.getTotRemaining());
        }

        t = bag.randomTiles(1, 2);
        assertEquals(d.getColor(), t.getColor());
        assertEquals(0, bag.getTotRemaining());
    }
}
