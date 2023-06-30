package it.polimi.it.controller;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.model.VirtualViewStub;
import org.junit.Test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private Game game;

    private LobbyStub lobby;
    private User host;

    private User joiner;
    private VirtualViewStub virtualViewStub;

    private GameController gameController;

    @Test
    public void testConstructor(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game, lobby);

        assertFalse(gameController.getEndGame());
        assertSame(game,gameController.getGame());
        assertEquals(game.getNumplayers(),gameController.getNumOfPlayers());
        assertEquals(game.getGameid(),gameController.getGameID());
        assertSame(lobby,gameController.getLobby());
        assertNotNull(gameController.getPlayerList());
        assertEquals(0,gameController.getMaxTile());
    }

    @Test
    public void testTurnDealer1(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        gameController.setEndGame(true);
        gameController.setCurrentPlayer(1);
        try {
            gameController.turnDealer();
        } catch (InvalidIDException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testTurnDealer2(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        gameController.setPlayerList(game.randomPlayers());
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);
        try {
            gameController.turnDealer();
            assertEquals(1,gameController.getCurrentPlayer());
            assertEquals(3, gameController.getMaxTile());
        } catch (InvalidIDException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void testTurnDealer3(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);


        gameController.setPlayerList(game.randomPlayers());
        gameController.setCurrentPlayer(0);
        host.setInGame(false);
        joiner.setInGame(false);
        try {
            gameController.turnDealer();
        } catch (InvalidIDException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    //TODO !!!!!!
    //TESTO LA FREEEEZZZEEEE
    @Test
    public void testTurnDealer4(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        gameController.setCurrentPlayer(1);
        host.setInGame(false);
        joiner.setInGame(true);
        try {
            gameController.turnDealer();
        } catch (InvalidIDException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testTurnDealer5(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        User joiner1 = new User("Francesco");
        User joiner2 = new User("Chiara");
        game = new Game(3, host, 0, virtualViewStub);
        game.joinGame(joiner1);
        game.joinGame(joiner2);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner1);
        users.add(joiner2);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(2);
        host.setInGame(false);
        joiner1.setInGame(true);
        joiner2.setInGame(true);
        try {
            gameController.turnDealer();
            assertEquals(1, gameController.getCurrentPlayer());
        } catch (InvalidIDException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testTurnStarter(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        try {
            gameController.firstTurnStarter();
            assertEquals(0,gameController.getCurrentPlayer());
            assertEquals(2, gameController.getPlayerList().size());
        } catch (InvalidIDException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testFirstOperation1(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);
        host.setInGame(false);
        try {
            gameController.firstOperation();
            assertEquals(1,gameController.getCurrentPlayer());
        } catch (InvalidIDException  | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testFirstOperation2(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);

        Tile[][] matrix = new Tile[6][5];

        matrix[0][0] = new Tile(0, 0, PossibleColors.PINK);
        matrix[1][0] = new Tile(1,0,PossibleColors.PINK);
        matrix[2][0] = new Tile(2,0,PossibleColors.CYAN);
        matrix[3][0] = new Tile(3, 0,PossibleColors.BLUE);
        matrix[4][0] = new Tile(4,0,PossibleColors.WHITE);
        matrix[5][0] = new Tile(5,0,PossibleColors.PINK);

        matrix[0][1] = new Tile(0, 1, PossibleColors.PINK);
        matrix[1][1] = new Tile(1,1,PossibleColors.PINK);
        matrix[2][1] = new Tile(2,1,PossibleColors.CYAN);
        matrix[3][1] = new Tile(3, 1,PossibleColors.BLUE);
        matrix[4][1] = new Tile(4,1,PossibleColors.YELLOW);
        matrix[5][1] = new Tile(5,1,PossibleColors.PINK);

        matrix[0][2] = new Tile(0, 2, PossibleColors.PINK);
        matrix[1][2] = new Tile(1,2,PossibleColors.YELLOW);
        matrix[2][2] = new Tile(2,2,PossibleColors.CYAN);
        matrix[3][2] = new Tile(3, 2,PossibleColors.PINK);
        matrix[4][2] = new Tile(4,2,PossibleColors.PINK);
        matrix[5][2] = new Tile(5,2,PossibleColors.PINK);

        matrix[0][3] = new Tile(0, 3, PossibleColors.PINK);
        matrix[1][3] = new Tile(1,3,PossibleColors.BLUE);
        matrix[2][3] = new Tile(2,3,PossibleColors.CYAN);
        matrix[3][3] = new Tile(3, 3,PossibleColors.PINK);
        matrix[4][3] = new Tile(4,3,PossibleColors.PINK);
        matrix[5][3] = new Tile(5,3,PossibleColors.PINK);

        matrix[0][4] = new Tile(0, 4, PossibleColors.PINK);
        matrix[1][4] = new Tile(1,4,PossibleColors.CYAN);
        matrix[2][4] = new Tile(2,4,PossibleColors.CYAN);
        matrix[3][4] = new Tile(3, 4,PossibleColors.BLUE);
        matrix[4][4] = new Tile(4,4,PossibleColors.WHITE);
        matrix[5][4] = new Tile(5,4,PossibleColors.CYAN);

        host.getShelfie().setShelf(matrix);
        try {
            gameController.firstOperation();
            //assertEquals(1,gameController.getCurrentPlayer());
        } catch (InvalidIDException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetFromViewNTiles1(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);
        host.setInGame(false);
        try {
            gameController.getFromViewNTiles("Giacomo",2);
            assertEquals(1,gameController.getCurrentPlayer());
        } catch (InvalidIDException | IOException | WrongPlayerException | IllegalValueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetFromViewNTiles2(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);
        gameController.setMaxTile(3);
        try {
            gameController.getFromViewNTiles("Giacomo",4);
        } catch (InvalidIDException | IOException | WrongPlayerException | IllegalValueException e) {
            assertEquals("You can't take so much tiles", e.getMessage());
        }

        try {
            gameController.getFromViewNTiles("Francesco",3);
        } catch (InvalidIDException | IOException | WrongPlayerException | IllegalValueException e) {
            assertEquals("It's not your turn", e.getMessage());
        }

        try {
            gameController.getFromViewNTiles("Giacomo",2);
        } catch (InvalidIDException | IOException | WrongPlayerException | IllegalValueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testgetTilesListFromView1(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);
        host.setInGame(false);
        List<Tile> tiles = new ArrayList<>();
        try {
            gameController.getTilesListFromView("Giacomo",tiles);
            assertEquals(1,gameController.getCurrentPlayer());
        } catch (InvalidIDException | IOException | WrongPlayerException | IllegalValueException | WrongListException | WrongTileException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetTilesListFromView2(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);
        gameController.setMaxTile(3);

        try {
            gameController.getFromViewNTiles("Giacomo",3);
        } catch (InvalidIDException | IOException | WrongPlayerException | IllegalValueException e) {
            System.out.println(e.getMessage());
        }

        List<Tile> choosenList = new ArrayList<>();

        try {
            gameController.getTilesListFromView("Francesco",choosenList);
        } catch (WrongPlayerException | WrongListException | IllegalValueException | InvalidIDException | IOException | WrongTileException e) {
            assertEquals("It's not your turn", e.getMessage());
        }

        try {
            gameController.getTilesListFromView("Giacomo",choosenList);
        } catch (WrongPlayerException | WrongListException | IllegalValueException | InvalidIDException | IOException | WrongTileException e) {
            assertEquals("You chose badly", e.getMessage());
        }

        List<Tile> choosenList2 = game.getBoard().choosableTiles(3).get(0);
        try {
            gameController.getTilesListFromView("Giacomo",choosenList2);
        } catch (WrongPlayerException | WrongListException | IllegalValueException | InvalidIDException | IOException | WrongTileException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetColumnFromView1(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);
        host.setInGame(false);

        try {
            gameController.getColumnFromView("Giacomo",6);
        } catch (InvalidIDException | IOException | IllegalValueException e) {
            assertEquals("Invalid column choice",e.getMessage());
        }

        try {
            gameController.getColumnFromView("Giacomo",4);
            assertEquals(1,gameController.getCurrentPlayer());
        } catch (InvalidIDException | IOException | IllegalValueException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetColumnFromView2(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);

        int row,column;
        Tile [][] matrix = new Tile[6][5];
        for(column=0; column<5; column++){
            for (row=0; row<6; row++){
                matrix[row][column] = new Tile(row, column,PossibleColors.BLUE);
            }
        }

        matrix[3][4] = new Tile(3, 4,PossibleColors.DEFAULT);
        matrix[4][4] = new Tile(4, 4,PossibleColors.DEFAULT);
        matrix[5][4] = new Tile(5, 4,PossibleColors.DEFAULT);

        host.getShelfie().setShelf(matrix);

        host.setTilesNumber(3);

        List<Tile> choosenList = game.getBoard().choosableTiles(3).get(0);
        try {
            gameController.getTilesListFromView("Giacomo",choosenList);
        } catch (WrongPlayerException | WrongListException | IllegalValueException | InvalidIDException | IOException | WrongTileException e) {
            System.out.println(e.getMessage());
        }

        boolean possibleColumn[] = {false,false,false,false,true};
        gameController.setPossibleColumnArray(possibleColumn);
        try {
            gameController.getColumnFromView("Giacomo",0);
        } catch (InvalidIDException | IOException | IllegalValueException e) {
            assertEquals("Invalid column choice",e.getMessage());
        }

        try {
            assertFalse(gameController.getEndGame());
            gameController.getColumnFromView("Giacomo",4);
            assertTrue(gameController.getEndGame());
        } catch (InvalidIDException | IOException | IllegalValueException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testReset() {
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        gameController.setCurrentPlayer(0);

        try {
            gameController.resetGame(joiner);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void otherTest(){
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo");
        joiner = new User("Francesco");
        game = new Game(2, host, 0, virtualViewStub);
        game.joinGame(joiner);
        lobby = new LobbyStub();
        gameController = new GameController(game,lobby);

        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        gameController.setPlayerList(users);
        gameController.setCurrentPlayer(0);

        assertEquals(0, gameController.getPlayerNumber(host));
        assertEquals(1, gameController.getPlayerNumber(joiner));

        assertEquals(host, gameController.getUser(host.getNickname()));
        assertEquals(joiner, gameController.getUser(joiner.getNickname()));
    }

    @Test
    public void pushChatMessageTest() throws RemoteException, IllegalValueException {
        virtualViewStub = new VirtualViewStub();
        host = new User("Giacomo     ");
        joiner = new User("Francesco   ");
        User third = new User("Alberto     ");
        game = new Game(3, host, 0, virtualViewStub);
        game.joinGame(joiner);
        game.joinGame(third);
        lobby = new LobbyStub();
        gameController = new GameController(game, lobby);
        ArrayList<User> users = new ArrayList<>();
        users.add(host);
        users.add(joiner);
        users.add(third);
        gameController.setPlayerList(users);


        gameController.pushChatMessage("ciao raga");
        assertTrue(host.getChatList().contains("ciao raga"));
        assertTrue(joiner.getChatList().contains("ciao raga"));
        assertTrue(third.getChatList().contains("ciao raga"));

        gameController.pushChatPrivateMessage("Giacomo", "Buongiorno amici", "Alberto");
        assertTrue(host.getChatList().contains("Buongiorno amici"));
        assertTrue(third.getChatList().contains("Buongiorno amici"));
        assertFalse(joiner.getChatList().contains("Buongiorno amici"));


        try {
            gameController.pushChatPrivateMessage("Giacomo", "Buongiorno amici", "Gianni");
        }catch (IllegalValueException e){
            assertEquals("There is no player with this nickname!", e.getMessage());
        }

    }

}
