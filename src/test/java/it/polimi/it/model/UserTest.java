package it.polimi.it.model;

import it.polimi.it.Exceptions.IllegalValueException;
import it.polimi.it.Exceptions.WrongTileException;
import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private Game game;
    private User host;
    private User joiner;
    private VirtualViewStub virtualViewStub;
    private Board board;

    @Before
    public void setup() {
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        host.createShelfie();
    }

    @Test
    public void CorrectInitialization(){
        User u1 = new User("Giacomo");

        assertNotNull(u1);
        assertNotNull(u1.getShelfie());
        assertFalse(u1.getInGame());
        assertNotNull(u1.getChat());
    }

    @Test
    public void MaxValueOfTilesCrashTest() throws IllegalValueException, IOException {

        host.setInGame(false);
        if(host.maxValueOfTiles() != -1){
            fail();
        }
    }

    @Test
    public void MaxValueOfTilesTest() throws IllegalValueException, IOException {

        host.setInGame(true);

        assertTrue(host.getInGame());

        if(host.maxValueOfTiles() != 3){
            fail();
        }

        host.getShelfie().getShelf()[0][0] = new Tile(0, 0, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][0] = new Tile(1,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][0] = new Tile(2,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[3][0] = new Tile(3, 0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[4][0] = new Tile(4,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[5][0] = new Tile(5,0,PossibleColors.BLUE);

        host.getShelfie().getShelf()[0][1] = new Tile(0, 1, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][1] = new Tile(1,1,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][1] = new Tile(2,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[3][1] = new Tile(3, 1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[4][1] = new Tile(4,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[5][1] = new Tile(5,1,PossibleColors.GREEN);

        host.getShelfie().getShelf()[0][2] = new Tile(0, 2, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][2] = new Tile(1,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][2] = new Tile(2,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][2] = new Tile(3, 2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[4][2] = new Tile(4,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[5][2] = new Tile(5,2,PossibleColors.YELLOW);

        host.getShelfie().getShelf()[0][3] = new Tile(0, 3, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][3] = new Tile(1,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][3] = new Tile(2,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][3] = new Tile(3, 3,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][3] = new Tile(4,3,PossibleColors.PINK);
        host.getShelfie().getShelf()[5][3] = new Tile(5,3,PossibleColors.PINK);

        host.getShelfie().getShelf()[0][4] = new Tile(0, 4, PossibleColors.GREEN);
        host.getShelfie().getShelf()[1][4] = new Tile(1,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[2][4] = new Tile(2,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[3][4] = new Tile(3, 4,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][4] = new Tile(4,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[5][4] = new Tile(5,4,PossibleColors.PINK);

        if(host.maxValueOfTiles() != 0){
            fail();
        }

        host.getShelfie().getShelf()[0][0] = new Tile(0, 0, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][0] = new Tile(1,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][0] = new Tile(2,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[3][0] = new Tile(3, 0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[4][0] = new Tile(4,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[5][0] = new Tile(5,0,PossibleColors.BLUE);

        host.getShelfie().getShelf()[0][1] = new Tile(0, 1, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][1] = new Tile(1,1,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][1] = new Tile(2,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[3][1] = new Tile(3, 1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[4][1] = new Tile(4,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[5][1] = new Tile(5,1,PossibleColors.GREEN);

        host.getShelfie().getShelf()[0][2] = new Tile(0, 2, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][2] = new Tile(1,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][2] = new Tile(2,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][2] = new Tile(3, 2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[4][2] = new Tile(4,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[5][2] = new Tile(5,2,PossibleColors.YELLOW);

        host.getShelfie().getShelf()[0][3] = new Tile(0, 3, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][3] = new Tile(1,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][3] = new Tile(2,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][3] = new Tile(3, 3,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][3] = new Tile(4,3,PossibleColors.PINK);
        host.getShelfie().getShelf()[5][3] = new Tile(5,3,PossibleColors.PINK);

        host.getShelfie().getShelf()[0][4] = new Tile(0, 4, PossibleColors.GREEN);
        host.getShelfie().getShelf()[1][4] = new Tile(1,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[2][4] = new Tile(2,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[3][4] = new Tile(3, 4,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][4] = new Tile(4,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[5][4] = new Tile(5,4,PossibleColors.DEFAULT);

        if(host.maxValueOfTiles() != 1){
            fail();
        }

        host.getShelfie().getShelf()[0][0] = new Tile(0, 0, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][0] = new Tile(1,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][0] = new Tile(2,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[3][0] = new Tile(3, 0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[4][0] = new Tile(4,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[5][0] = new Tile(5,0,PossibleColors.BLUE);

        host.getShelfie().getShelf()[0][1] = new Tile(0, 1, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][1] = new Tile(1,1,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][1] = new Tile(2,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[3][1] = new Tile(3, 1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[4][1] = new Tile(4,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[5][1] = new Tile(5,1,PossibleColors.GREEN);

        host.getShelfie().getShelf()[0][2] = new Tile(0, 2, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][2] = new Tile(1,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][2] = new Tile(2,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][2] = new Tile(3, 2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[4][2] = new Tile(4,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[5][2] = new Tile(5,2,PossibleColors.YELLOW);

        host.getShelfie().getShelf()[0][3] = new Tile(0, 3, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][3] = new Tile(1,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][3] = new Tile(2,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][3] = new Tile(3, 3,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][3] = new Tile(4,3,PossibleColors.PINK);
        host.getShelfie().getShelf()[5][3] = new Tile(5,3,PossibleColors.PINK);

        host.getShelfie().getShelf()[0][4] = new Tile(0, 4, PossibleColors.GREEN);
        host.getShelfie().getShelf()[1][4] = new Tile(1,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[2][4] = new Tile(2,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[3][4] = new Tile(3, 4,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][4] = new Tile(4,4,PossibleColors.DEFAULT);
        host.getShelfie().getShelf()[5][4] = new Tile(5,4,PossibleColors.DEFAULT);

        if(host.maxValueOfTiles() != 2){
            fail();
        }

        host.getShelfie().getShelf()[0][0] = new Tile(0, 0, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][0] = new Tile(1,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][0] = new Tile(2,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[3][0] = new Tile(3, 0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[4][0] = new Tile(4,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[5][0] = new Tile(5,0,PossibleColors.BLUE);

        host.getShelfie().getShelf()[0][1] = new Tile(0, 1, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][1] = new Tile(1,1,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][1] = new Tile(2,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[3][1] = new Tile(3, 1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[4][1] = new Tile(4,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[5][1] = new Tile(5,1,PossibleColors.GREEN);

        host.getShelfie().getShelf()[0][2] = new Tile(0, 2, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][2] = new Tile(1,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][2] = new Tile(2,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][2] = new Tile(3, 2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[4][2] = new Tile(4,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[5][2] = new Tile(5,2,PossibleColors.YELLOW);

        host.getShelfie().getShelf()[0][3] = new Tile(0, 3, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][3] = new Tile(1,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][3] = new Tile(2,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][3] = new Tile(3, 3,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][3] = new Tile(4,3,PossibleColors.PINK);
        host.getShelfie().getShelf()[5][3] = new Tile(5,3,PossibleColors.PINK);

        host.getShelfie().getShelf()[0][4] = new Tile(0, 4, PossibleColors.GREEN);
        host.getShelfie().getShelf()[1][4] = new Tile(1,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[2][4] = new Tile(2,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[3][4] = new Tile(3, 4,PossibleColors.DEFAULT);
        host.getShelfie().getShelf()[4][4] = new Tile(4,4,PossibleColors.DEFAULT);
        host.getShelfie().getShelf()[5][4] = new Tile(5,4,PossibleColors.DEFAULT);

        if(host.maxValueOfTiles() != 3){
            fail();
        }

        host.getShelfie().getShelf()[0][0] = new Tile(0, 0, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][0] = new Tile(1,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][0] = new Tile(2,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[3][0] = new Tile(3, 0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[4][0] = new Tile(4,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[5][0] = new Tile(5,0,PossibleColors.BLUE);

        host.getShelfie().getShelf()[0][1] = new Tile(0, 1, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][1] = new Tile(1,1,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][1] = new Tile(2,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[3][1] = new Tile(3, 1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[4][1] = new Tile(4,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[5][1] = new Tile(5,1,PossibleColors.GREEN);

        host.getShelfie().getShelf()[0][2] = new Tile(0, 2, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][2] = new Tile(1,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][2] = new Tile(2,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][2] = new Tile(3, 2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[4][2] = new Tile(4,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[5][2] = new Tile(5,2,PossibleColors.YELLOW);

        host.getShelfie().getShelf()[0][3] = new Tile(0, 3, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][3] = new Tile(1,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][3] = new Tile(2,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][3] = new Tile(3, 3,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][3] = new Tile(4,3,PossibleColors.PINK);
        host.getShelfie().getShelf()[5][3] = new Tile(5,3,PossibleColors.PINK);

        host.getShelfie().getShelf()[0][4] = new Tile(0, 4, PossibleColors.GREEN);
        host.getShelfie().getShelf()[1][4] = new Tile(1,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[2][4] = new Tile(2,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[3][4] = new Tile(3, 4,PossibleColors.DEFAULT);
        host.getShelfie().getShelf()[4][4] = new Tile(4,4,PossibleColors.DEFAULT);
        host.getShelfie().getShelf()[5][4] = new Tile(5,4,PossibleColors.DEFAULT);

        if(host.maxValueOfTiles() != 3){
            fail();
        }

        //FRAAAAAA



    }
    @Test
    public void ChoosableTilesCrashTest() throws RemoteException {
        try {
            host.choosableTiles(0);
        }catch (IllegalValueException e){
            assertEquals("Wrong tiles number", e.getMessage());
        }
        try {
            host.choosableTiles(4);
        }catch (IllegalValueException e){
            assertEquals("Wrong tiles number", e.getMessage());
        }
    }

    @Test
    public void ChoosableTilesTest() throws RemoteException, IllegalValueException {
        try {
            host.choosableTiles(1);
        }catch (IllegalValueException e){
            fail();
        }

        assertNotNull(host.choosableTiles(1));

        try {
            host.choosableTiles(2);
        }catch (IllegalValueException e){
            fail();
        }

        assertNotNull(host.choosableTiles(2));

        try {
            host.choosableTiles(3);
        }catch (IllegalValueException e){
            fail();
        }

        assertNotNull(host.choosableTiles(3));

    }
    @Test
    public void chooseSelectedTilesTest() throws WrongTileException {
        host.setTilesNumber(2);
        List<Tile> choosenList1 = game.getBoard().choosableTiles(3).get(0);
        try {
            host.chooseSelectedTiles(choosenList1);
        }catch (WrongTileException e){
            System.out.println(e.getMessage());
            assertEquals("you have to select " + host.getTilesNumber() + " tiles\n", e.getMessage());
        }

        host.setTilesNumber(1);
        List<Tile> choosenList2 = new ArrayList<>();
        choosenList2.add(new Tile(PossibleColors.DEFAULT));

        try{
            host.chooseSelectedTiles(choosenList2);
        }catch (WrongTileException e){
            assertEquals("Tiles of this type can't be chosen", e.getMessage());
        }

        host.setTilesNumber(1);
        List<Tile> choosenList3 = new ArrayList<>();
        choosenList3.add(new Tile(PossibleColors.XTILE));

        try{
            host.chooseSelectedTiles(choosenList3);
        }catch (WrongTileException e){
            assertEquals("Tiles of this type can't be chosen", e.getMessage());
        }

        host.getShelfie().getShelf()[0][0] = new Tile(0, 0, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][0] = new Tile(1,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][0] = new Tile(2,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[3][0] = new Tile(3, 0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[4][0] = new Tile(4,0,PossibleColors.BLUE);
        host.getShelfie().getShelf()[5][0] = new Tile(5,0,PossibleColors.BLUE);

        host.getShelfie().getShelf()[0][1] = new Tile(0, 1, PossibleColors.BLUE);
        host.getShelfie().getShelf()[1][1] = new Tile(1,1,PossibleColors.BLUE);
        host.getShelfie().getShelf()[2][1] = new Tile(2,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[3][1] = new Tile(3, 1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[4][1] = new Tile(4,1,PossibleColors.GREEN);
        host.getShelfie().getShelf()[5][1] = new Tile(5,1,PossibleColors.GREEN);

        host.getShelfie().getShelf()[0][2] = new Tile(0, 2, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][2] = new Tile(1,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][2] = new Tile(2,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][2] = new Tile(3, 2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[4][2] = new Tile(4,2,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[5][2] = new Tile(5,2,PossibleColors.YELLOW);

        host.getShelfie().getShelf()[0][3] = new Tile(0, 3, PossibleColors.YELLOW);
        host.getShelfie().getShelf()[1][3] = new Tile(1,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[2][3] = new Tile(2,3,PossibleColors.YELLOW);
        host.getShelfie().getShelf()[3][3] = new Tile(3, 3,PossibleColors.PINK);
        host.getShelfie().getShelf()[4][3] = new Tile(4,3,PossibleColors.PINK);
        host.getShelfie().getShelf()[5][3] = new Tile(5,3,PossibleColors.PINK);

        host.getShelfie().getShelf()[0][4] = new Tile(0, 4, PossibleColors.GREEN);
        host.getShelfie().getShelf()[1][4] = new Tile(1,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[2][4] = new Tile(2,4,PossibleColors.PINK);
        host.getShelfie().getShelf()[3][4] = new Tile(3, 4,PossibleColors.DEFAULT);
        host.getShelfie().getShelf()[4][4] = new Tile(4,4,PossibleColors.DEFAULT);
        host.getShelfie().getShelf()[5][4] = new Tile(5,4,PossibleColors.DEFAULT);

        host.setTilesNumber(3);
        List<Tile> choosableTiles4 = new ArrayList<>();
        choosableTiles4.add((new Tile(PossibleColors.BLUE)));
        choosableTiles4.add((new Tile(PossibleColors.BLUE)));
        choosableTiles4.add((new Tile(PossibleColors.BLUE)));

        boolean[] testC4 = {false, false, false, false, true};

        for(int i=0; i<5; i++) {
            assertEquals(host.chooseSelectedTiles(choosableTiles4)[i], testC4[i]);
        }
    }
    @Test
    public void insertTileTest() throws RemoteException {
        List<Tile> chosen = new ArrayList<>();
        chosen.add(new Tile(3, 4, PossibleColors.BLUE));
        chosen.add(new Tile(3, 5, PossibleColors.GREEN));
        chosen.add(new Tile(3, 6, PossibleColors.WHITE));

        host.insertTile(0, chosen);

        assertEquals(host.getShelfie().getCell(0, 0).getColor(), PossibleColors.BLUE.toString());
        assertEquals(host.getShelfie().getCell(0, 1).getColor(), PossibleColors.GREEN.toString());
        assertEquals(host.getShelfie().getCell(0, 2).getColor(), PossibleColors.WHITE.toString());

        Tile x = new Tile(PossibleColors.XTILE);
        Tile d = new Tile(PossibleColors.DEFAULT);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 0 && j < 3) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 0 && j > 4) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 8 && j < 4) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 8 && j > 5) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 1 && j < 3) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 2 && j < 2) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 1 && j > 5) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 2 && j > 6) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 3 && j == 0) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 5 && j == 8) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 6 && j < 2) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 6 && j > 6) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 7 && j < 3) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else if (i == 7 && j > 5) {
                    assertEquals(x.getColor(), host.getBoard().getMatrix()[i][j].getColor());
                } else {
                    host.getBoard().getMatrix()[i][j] = new Tile(i, j, PossibleColors.DEFAULT);
                }
            }
        }

        host.insertTile(0, chosen);
    }
    @Test
    public void BoardTest(){
        Board b = host.getBoard();
        assertNotNull(b);
        host.setBoard(b);
        assertEquals(host.getBoard(), b);

    }

    @Test
    public void getGameIDTest(){
        assertEquals(host.getGameid(),0);
        User user = new User("Chiara");
        assertEquals(user.getGameid(), -1);
    }

    @Test
    public void getGameTest(){
        assertNotNull(host.getGame());
    }

    @Test
    public void setShelfieTest(){
        Shelfie s = new Shelfie(joiner);
        host.setShelfie(s);
        assertEquals(host.getShelfie(), s);
    }

    @Test
    public void getChatListTest(){
        assertNotNull(host.getChatList());
    }

    @Test
    public void chatMessageTest(){
        String message = "ciao";
        host.newMessage(message);
        while(message.length()<33){
            message = message + " ";
        }
        host.newMessage(message);
        assertTrue(host.getChatList().contains(message));

        String message1 = "mamma";
        host.newPrivateMessage(message1);
        while(message1.length()<43){
            message1 = message1 + " ";
        }
        host.newPrivateMessage(message1);
        assertTrue(host.getChatList().contains(message1));
    }

}
