package it.polimi.it.model.Board;


import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class B2PTest {
    private B2P matrix;
    private TilesBag bag;

    @Before
    public void B2P() {
        this.matrix = new B2P();
        this.bag= new TilesBag();
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
        if(matrix.checkRefill()){
            fail("This board is full, shouldn't be refilled!");
        }
    }



    @Test
    public void count(){
        int max = matrix.findMaxAdjacent(3);
        assertEquals(3, max);

        List<List<Tile>> a1 = matrix.choosableTiles(1);
        System.out.println("Versione gruppi da 1");
        for (List<Tile> inner : a1) {
            for (Tile t: inner){
                System.out.print(t.getRow() +" "+ t.getColumn() +" " + " __ ");
            }
            System.out.println();
        }
        if(a1.size()!=16){
            fail("This doesn't have 1 adjacent take-able Tiles!");
        }

        List<List<Tile>> a2 = matrix.choosableTiles(2);
        System.out.println("Versione gruppi da 2");
        for (List<Tile> inner : a2) {
            for (Tile t: inner){
                System.out.print(t.getRow() +" "+ t.getColumn() +" " + " __ ");
            }
            System.out.println();
        }
        if(a2.size()!=8){
            fail("This doesn't have 2 adjacent take-able Tiles!");
        }

        List<List<Tile>> a3 = matrix.choosableTiles(3);
        System.out.println("Versione gruppi da 3");
        System.out.println("Size lista gruppi da 3: " + a3.size());
        for (List<Tile> inner : a3) {
            for (Tile t: inner){
                System.out.print(t.getRow() +" "+ t.getColumn() +" " + " __ ");
            }
            System.out.println();
        }
        if(a3.size()!=4){
            fail("This doesn't have 3 adjacent take-able Tiles!");
        }
    }
}
