package it.polimi.it.model.Board;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.Tiles.TilesBag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

        Tile b = new Tile(PossibleColors.BLUE);
        matrix.matrix[3][3] = new Tile(3, 3, PossibleColors.BLUE);
        assertNotEquals(x.getColor(), matrix.matrix[3][3].getColor());
        assertNotEquals(d.getColor(), matrix.matrix[3][3].getColor());
        assertEquals(b.getColor(), matrix.matrix[3][3].getColor());

        if(matrix.checkRefill()){
            fail("This board doesn't need a refill!");
        }
    }





    @Test
    public void Board2Refill() {
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

        matrix.refill();

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
                    assertNotEquals(x.getColor(), matrix.matrix[i][j].getColor());
                    assertNotEquals(d.getColor(), matrix.matrix[i][j].getColor());
                    assertSame(i, matrix.matrix[i][j].getRow());
                    assertSame(j, matrix.matrix[i][j].getColumn());
                }
                //funziona
            }
        }

    }

    @Test
    public void correctInitialAdjacentNumber() {
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
                    assertNotEquals(x.getColor(), matrix.matrix[i][j].getColor());
                    assertNotEquals(d.getColor(), matrix.matrix[i][j].getColor());
                    assertSame(i, matrix.matrix[i][j].getRow());
                    assertSame(j, matrix.matrix[i][j].getColumn());
                }
            }
        }

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
        //System.out.println(a1);

        if(a1.size()!=20){
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
        //List<List<Tile>> a2 = matrix.choosableTiles(2);
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
        //List<List<Tile>> a3 = matrix.choosableTiles(3);
        if(a3.size()!=4){
            fail("This doesn't have 3 adjacent take-able Tiles!");
        }


        if (matrix.findMaxAdjacent(3)!=3){
            fail("...!");
        }
        if (matrix.findMaxAdjacent(1)!=1){
            fail("...!");
        }
        if (matrix.findMaxAdjacent(2)!=2){
            fail("...!");
        }
    }

    @Test
    public  void deleter(){
        Tile x = new Tile(PossibleColors.XTILE);
        Tile d = new Tile(PossibleColors.DEFAULT);

        assertNotEquals(x.getColor(), matrix.matrix[3][3].getColor());
        assertNotEquals(d.getColor(), matrix.matrix[3][3].getColor());
        assertNotEquals(x.getColor(), matrix.matrix[3][4].getColor());
        assertNotEquals(d.getColor(), matrix.matrix[3][4].getColor());

        List<Tile> toBeRemoved = new ArrayList<>();
        toBeRemoved.add(matrix.matrix[3][3]);
        toBeRemoved.add(matrix.matrix[3][4]);

        for (Tile t: toBeRemoved){
            System.out.println(t.getRow() +" "+ t.getColumn() +" " + t.getColor() + " __ ");
        }

        matrix.removeTiles(toBeRemoved);
        assertEquals(d.getColor(), matrix.matrix[3][3].getColor());
        assertEquals(d.getColor(), matrix.matrix[3][4].getColor());


        if (matrix.checkRefill()){
            fail("shouldn't be refilling!");
        }

        int max = matrix.findMaxAdjacent(3);
        System.out.println(max);
        if (max!=3){
            fail("...");
        }

    }

    @Test
    public  void defaultColumn(){
        for(int i=0; i<9; i++){
            matrix.matrix[i][3] = new Tile(PossibleColors.DEFAULT);
        }
        if(matrix.checkRefill()){
            fail();
        }
        int r = matrix.findMaxAdjacent(3);
        if(r!=3){
            fail();
        }
        List<List<Tile>> a = matrix.choosableTiles(3);
        System.out.println(a.size());
        if(a.size()!=24){
            fail();
        }
        for (List<Tile> inner : a) {
            for (Tile t: inner){
                System.out.print(t.getRow() +" "+ t.getColumn() +" " + " __ ");
            }
            System.out.println();
        }
    }

}
