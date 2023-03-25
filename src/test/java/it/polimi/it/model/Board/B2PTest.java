package it.polimi.it.model.Board;


import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class B2PTest {
    private B2P matrix;

    @Before
    public void B2P() {
        this.matrix = new B2P();
    }

    @After
    public void destroy() {
        matrix = null;
    }


    @Test
    public void correctInitialization() {
        Tile x = new Tile(PossibleColors.XTILE);
        Tile d = new Tile(PossibleColors.DEFAULT);

        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                if(i==0 || i==8){
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==1 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==2 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==1 && j>4) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==2 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==3 && j<2) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==3 && j==8) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==4 && j==0) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==4 && j==8) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==5 && j==0) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==5 && j>6) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==6 && j<3) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==6 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==7 && j<4) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else if (i==7 && j>5) {
                    assertEquals(x.getColor(), matrix.matrix[i][j].getColor());
                }else {
                    assertNotEquals(x.getColor(), matrix.matrix[i][j].getColor());
                    assertNotEquals(d.getColor(), matrix.matrix[i][j].getColor());
                    assertSame(i, matrix.matrix[i][j].getRow());
                    assertSame(j, matrix.matrix[i][j].getColumn());
                }
            }
        }
    }

    @Test
    public void refillChecker(){
        if(matrix.checkRefill() == false){
            fail("This board is full, shouldn't be refilled!");
        }
    }
}
