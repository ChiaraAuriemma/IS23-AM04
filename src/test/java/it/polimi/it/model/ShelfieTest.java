package it.polimi.it.model;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShelfieTest {
    private Shelfie shelf;

    private List<Tile> chosen;

    private User u = new User(" ");

    @Before
    public void Shelf() {
        this.shelf = new Shelfie(u);
    }

    @After
    public void destroy() {
        this.shelf = null;
    }

    @Test
    public void correctInitialization() {
        Tile d = new Tile(PossibleColors.DEFAULT);

        for(int row = 0; row < 6; row++){
            for(int column = 0; column < 5; column++){
                //assertEquals(d.getColor(), shelf.getCell(column, row).getColor());
            }
        }
    }
    @Test
    public void AddTest(){

        chosen = new ArrayList<>(3);

        for(int i=0; i < 3; i++){
            chosen.add(i, new Tile(PossibleColors.BLUE));
        }

        shelf.addTile(3, chosen);
        assertEquals("BLUE", shelf.getCell(3, 0).getColor());
        assertEquals("BLUE", shelf.getCell(3, 1).getColor());
        assertEquals("BLUE", shelf.getCell(3, 2).getColor());

        for(int i=0; i < 2; i++){
            chosen.set(i, new Tile(PossibleColors.GREEN));
        }

        chosen.remove(2);

        shelf.addTile(3, chosen);
        assertEquals("GREEN", shelf.getCell(3, 3).getColor());
        assertEquals("GREEN", shelf.getCell(3, 4).getColor());
        assertEquals("DEFAULT", shelf.getCell(3,5).getColor());

    }
    @Test
    public void ChooseColumnTest(){
        chosen = new ArrayList<>(3);

        boolean[] testColumn = {true, true, true, false, true};

        for(int i=0; i < 3; i++){
            chosen.add(i, new Tile(PossibleColors.BLUE));
        }

        shelf.addTile(3, chosen);

        for(int i=0; i < 2; i++){
            chosen.set(i, new Tile(PossibleColors.GREEN));
        }

        chosen.remove(2);

        shelf.addTile(3, chosen);

        boolean[] check = shelf.chooseColumn(2);

        for(int i=0; i<5; i++) {
            assertEquals(testColumn[i], check[i]);
        }

    }
    @Test
    public void PossibleTilesTest(){
        chosen = new ArrayList<>(3);

        for(int i=0; i < 3; i++){
            chosen.add(i, new Tile(PossibleColors.BLUE));
        }

        shelf.addTile(3, chosen);
        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);

        for(int i=0; i < 2; i++){
            chosen.set(i, new Tile(PossibleColors.GREEN));
        }

        chosen.remove(2);

        shelf.addTile(3, chosen);
        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);

        chosen.remove(1);

        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);
        shelf.addTile(0, chosen);

        int check = shelf.possibleTiles();

        assertEquals(check, 1);

    }

    @Test
    public void PossibleTilesTestTot3(){
        chosen = new ArrayList<>(3);

        for(int i=0; i < 3; i++){
            chosen.add(i, new Tile(PossibleColors.BLUE));
        }

        shelf.addTile(3, chosen);
        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);

        for(int i=0; i < 2; i++){
            chosen.set(i, new Tile(PossibleColors.GREEN));
        }

        chosen.remove(2);

        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);

        chosen.remove(1);

        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);
        shelf.addTile(0, chosen);

        int check = shelf.possibleTiles();

        assertEquals(check, 3);

    }

    @Test
    public void CheckEndTrueTest(){
        chosen = new ArrayList<>(3);

        for(int i=0; i < 3; i++){
            chosen.add(i, new Tile(PossibleColors.BLUE));
        }

        shelf.addTile(3, chosen);
        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);

        for(int i=0; i < 3; i++){
            chosen.set(i, new Tile(PossibleColors.GREEN));
        }

        shelf.addTile(3, chosen);
        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);

        boolean check = shelf.checkEnd();

        assertTrue(check);
    }

    @Test
    public void CheckEndFalseTest(){
        chosen = new ArrayList<>(3);

        for(int i=0; i < 3; i++){
            chosen.add(i, new Tile(PossibleColors.BLUE));
        }

        shelf.addTile(3, chosen);
        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(4, chosen);

        for(int i=0; i < 3; i++){
            chosen.set(i, new Tile(PossibleColors.GREEN));
        }

        shelf.addTile(3, chosen);
        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);

        boolean check = shelf.checkEnd();

        assertFalse(check);
    }

    @Test
    public void checkAdjacentsPointsTestAllBlue(){

        chosen = new ArrayList<>(3);

        int total;

        for(int i=0; i < 3; i++){
            chosen.add(i, new Tile(PossibleColors.BLUE));
        }

        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(3, chosen);
        shelf.addTile(4, chosen);
        shelf.addTile(0, chosen);
        shelf.addTile(1, chosen);
        shelf.addTile(2, chosen);
        shelf.addTile(3, chosen);
        shelf.addTile(4, chosen);

        total = shelf.checkAdjacentsPoints();

        assertEquals(total, 8);

    }

    @Test
    public void checkAdjacentsPointsTestAllDefault(){
        int total=0;

        total = shelf.checkAdjacentsPoints();

        assertEquals(total, 0);
    }

    @Test
    public void checkAdjacentsPointsTest(){

        chosen = new ArrayList<>(6);

        this.shelf = new Shelfie(u);

        int total;

        chosen.add(0,new Tile(PossibleColors.PINK));
        chosen.add(1,new Tile(PossibleColors.GREEN));
        chosen.add(2,new Tile(PossibleColors.CYAN));
        chosen.add(3,new Tile(PossibleColors.PINK));
        chosen.add(4,new Tile(PossibleColors.BLUE));
        chosen.add(5,new Tile(PossibleColors.YELLOW));

        shelf.addTile(0,chosen);

        chosen.set(0,new Tile(PossibleColors.CYAN));
        chosen.set(1,new Tile(PossibleColors.WHITE));
        chosen.set(2,new Tile(PossibleColors.YELLOW));
        chosen.set(3,new Tile(PossibleColors.CYAN));
        chosen.set(4,new Tile(PossibleColors.BLUE));
        chosen.set(5,new Tile(PossibleColors.BLUE));

        shelf.addTile(1,chosen);

        chosen.set(0,new Tile(PossibleColors.YELLOW));
        chosen.set(1,new Tile(PossibleColors.PINK));
        chosen.set(2,new Tile(PossibleColors.YELLOW));
        chosen.set(3,new Tile(PossibleColors.YELLOW));
        chosen.set(4,new Tile(PossibleColors.PINK));
        chosen.set(5,new Tile(PossibleColors.YELLOW));

        shelf.addTile(2,chosen);

        chosen.set(0,new Tile(PossibleColors.BLUE));
        chosen.set(1,new Tile(PossibleColors.CYAN));
        chosen.set(2,new Tile(PossibleColors.YELLOW));
        chosen.set(3,new Tile(PossibleColors.WHITE));
        chosen.set(4,new Tile(PossibleColors.WHITE));
        chosen.set(5,new Tile(PossibleColors.WHITE));

        shelf.addTile(3,chosen);

        chosen.set(0,new Tile(PossibleColors.PINK));
        chosen.set(1,new Tile(PossibleColors.BLUE));
        chosen.set(2,new Tile(PossibleColors.WHITE));
        chosen.set(3,new Tile(PossibleColors.YELLOW));
        chosen.set(4,new Tile(PossibleColors.CYAN));
        chosen.set(5,new Tile(PossibleColors.PINK));

        shelf.addTile(4,chosen);

        total = shelf.checkAdjacentsPoints();

        assertEquals(total, 7);

    }

}