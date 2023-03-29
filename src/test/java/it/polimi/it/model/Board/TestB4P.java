package it.polimi.it.model.Board;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestB4P {

    private B4P matrix;
    private TilesBag bag;
    @Before
    public void B2P() {
        //this.matrix = new B4P();
        this.bag= new TilesBag();
        this.matrix = new B4P();
    }

    @After
    public void destroy() {
        matrix = null;
    }

/*
    @Test
    public void shouldBeRefilling(){

    }
*/


    @Test
    public void emptyBoardRefill() {
        Tile x = new Tile(PossibleColors.XTILE);
        Tile d = new Tile(PossibleColors.DEFAULT);

        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (i==0 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==0 && j>4) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==8 && j<4) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==8 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==1 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==2 && j<2) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==1 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==2 && j>6) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==3 && j==0) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==5 && j==8) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==6 && j<2) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==6 && j>6) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==7 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==7 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else{
                    matrix.matrix[i][j] = new Tile(i, j, PossibleColors.DEFAULT);
                    assertEquals(d.getColor(), matrix.matrix[i][j].getColor());
                    assertSame(i, matrix.matrix[i][j].getRow());
                    assertSame(j, matrix.matrix[i][j].getColumn());
                }
            }
        }

        if(!matrix.checkRefill()){
            fail("This board is empty, should be refilled!");
        }
    }



    @Test
    public void Board1Refill() {
        Tile x = new Tile(PossibleColors.XTILE);
        Tile d = new Tile(PossibleColors.DEFAULT);

        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if (i==0 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==0 && j>4) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==8 && j<4) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==8 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==1 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==2 && j<2) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==1 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==2 && j>6) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==3 && j==0) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==5 && j==8) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==6 && j<2) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==6 && j>6) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==7 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==7 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else{
                    matrix.matrix[i][j] = new Tile(i, j, PossibleColors.DEFAULT);
                    assertEquals(d.getColor(), matrix.matrix[i][j].getColor());
                    assertSame(i, matrix.matrix[i][j].getRow());
                    assertSame(j, matrix.matrix[i][j].getColumn());
                }
            }
        }

        if(!matrix.checkRefill()){
            fail("This board is empty, should be refilled!");
        }





        Tile g = new Tile(PossibleColors.GREEN);
        Tile p = new Tile(PossibleColors.PINK);
        Tile c = new Tile(PossibleColors.CYAN);
        Tile w = new Tile(PossibleColors.WHITE);


        matrix.matrix[3][2] = new Tile(3, 2, PossibleColors.GREEN);
        matrix.matrix[2][5] = new Tile(2, 5, PossibleColors.PINK);
        matrix.matrix[5][6] = new Tile(5, 6, PossibleColors.CYAN);
        matrix.matrix[6][3] = new Tile(6, 3, PossibleColors.WHITE);


        assertNotEquals(x.getColor(), matrix.matrix[3][2].getColor());
        assertNotEquals(d.getColor(), matrix.matrix[3][2].getColor());
        assertEquals(g.getColor(), matrix.matrix[3][2].getColor());

        assertNotEquals(x.getColor(), matrix.matrix[2][5].getColor());
        assertNotEquals(d.getColor(), matrix.matrix[2][5].getColor());
        assertEquals(p.getColor(), matrix.matrix[2][5].getColor());

        assertNotEquals(x.getColor(), matrix.matrix[5][6].getColor());
        assertNotEquals(d.getColor(), matrix.matrix[5][6].getColor());
        assertEquals(c.getColor(), matrix.matrix[5][6].getColor());

        assertNotEquals(x.getColor(), matrix.matrix[6][3].getColor());
        assertNotEquals(d.getColor(), matrix.matrix[6][3].getColor());
        assertEquals(w.getColor(), matrix.matrix[6][3].getColor());

        if(!matrix.checkRefill()){
            fail("This board needs a refill!");
        }

        Tile b = new Tile(PossibleColors.GREEN);
        matrix.matrix[3][3] = new Tile(3, 3, PossibleColors.BLUE);
        assertNotEquals(x.getColor(), matrix.matrix[3][3].getColor());
        assertNotEquals(d.getColor(), matrix.matrix[3][3].getColor());
        assertEquals(b.getColor(), matrix.matrix[3][3].getColor());

        if(!matrix.checkRefill()){
            fail("This board doesn't need a refill!");
        }
    }

}
