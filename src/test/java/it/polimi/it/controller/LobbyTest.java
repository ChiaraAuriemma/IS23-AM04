package it.polimi.it.controller;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.User;
import it.polimi.it.network.server.RMIImplementation;
import it.polimi.it.network.server.ServerTCP;
import org.junit.Test;

import java.io.IOException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {

    private Lobby lobby;

    @Test
    public void testConstructor() {
        lobby = new Lobby();

        assertNotNull(lobby.getUserList());
        assertNotNull(lobby.getGameList());
        assertNotNull(lobby.getGameControllerList());
        assertEquals(0, lobby.getGameCounterID());
    }

    @Test
    public void testCreateUser1() {
        lobby = new Lobby();

        String nickname1 = new String();
        try {
            lobby.createUser(nickname1);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            assertEquals("You have to write something", e.getMessage());
        }

        String nickname2 = "";
        try {
            lobby.createUser(nickname2);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            assertEquals("You have to write something", e.getMessage());
        }
    }

    @Test
    public void testCreateUser2() {
        lobby = new Lobby();

        String nickname = "Giacomo";
        try {
            lobby.createUser(nickname);
            assertEquals(1, lobby.getUserList().size());
            assertEquals(nickname, lobby.getUserList().get(0).getNickname());
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        lobby.getUserList().get(0).setInGame(true);

        try {
            lobby.createUser(nickname);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            assertEquals("This nickname already exists!", e.getMessage());
            assertEquals(1, lobby.getUserList().size());
            assertEquals(nickname, lobby.getUserList().get(0).getNickname());
        }
    }

    //TODO !!!!!!!!!!!!!!
    //manca solo il test per l'if più "grande"

    @Test
    public void testCreateGame() {
        lobby = new Lobby();
        String username = "Giacomo";

        try {
            lobby.createUser(username);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.createGame(username, 64);
        } catch (WrongPlayerException e) {
            assertEquals("\"Retry! You must insert a number between 2 and 4... \"", e.getMessage());
        }

        try {
            lobby.createGame(username, 0);
        } catch (WrongPlayerException e) {
            assertEquals("\"Retry! You must insert a number between 2 and 4... \"", e.getMessage());
        }

        try {
            GameController gameController = lobby.createGame(username, 2);
            assertEquals(2, lobby.getGameList().get(0).getNumplayers());
            assertEquals(username, lobby.getGameList().get(0).getPlayer(0).getNickname());
            assertTrue(lobby.getUserList().get(0).getInGame());
            assertEquals(1, lobby.getGameList().size());
            assertEquals(1, lobby.getGameControllerList().size());
            assertSame(gameController, lobby.getGameControllerList().get(0));
            assertSame(lobby.getGameList().get(0), lobby.getGameControllerList().get(0).getGame());
            assertSame(lobby, lobby.getGameControllerList().get(0).getLobby());
            assertEquals(1, lobby.getGameCounterID());
        } catch (WrongPlayerException e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void testJoinGame(){
        lobby = new Lobby();
        RemoteInterfaceStub remoteInterfaceStub = new RemoteInterfaceStub();

        String username = "Giacomo";

        try {
            lobby.createUser(username);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.createGame(username, 2);
            lobby.getGameList().get(0).getVirtualView().setUser(username,remoteInterfaceStub);
        } catch (WrongPlayerException e) {
            System.out.println(e.getMessage());
        }


        String username2 = "Alberto";

        try {
            lobby.createUser(username2);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.joinGame(username2,1);
        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | IOException e) {
            assertEquals("This ID does not exists",e.getMessage());
        }

        String username3 = "Chiara";

        try {
            lobby.createUser(username3);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.getGameList().get(0).getVirtualView().setUser(username2,remoteInterfaceStub);
            lobby.joinGame(username2,0);
            assertTrue(lobby.getUserList().get(1).getInGame());
            try {
                lobby.joinGame(username3,0);
            } catch (InvalidIDException | WrongPlayerException | IllegalValueException | IOException e) {
                assertEquals("There are already too many players in this game!",e.getMessage());
            }

        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.notifyEndGame(0);
        } catch (InvalidIDException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.joinGame(username3,0);
        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | IOException e) {
            assertEquals("This ID does not exists",e.getMessage());
        }
    }

    @Test
    public void testNotifyEndGame(){
        lobby = new Lobby();

        String username = "Giacomo";

        try {
            lobby.createUser(username);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.createGame(username, 2);
        } catch (WrongPlayerException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.getGameController(2);
        } catch (InvalidIDException e) {
            assertEquals("This ID does not exists",e.getMessage());
        }

        try {
            lobby.getGame(2);
        } catch (InvalidIDException e) {
            assertEquals("This ID does not exists",e.getMessage());
        }

        try {
            lobby.notifyEndGame(0);
            assertEquals(0,lobby.getGameList().size());
            assertEquals(0,lobby.getGameControllerList().size());
        } catch (InvalidIDException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.notifyEndGame(2);
        } catch (InvalidIDException e) {
            assertEquals("This ID does not exists",e.getMessage());
        }
    }

    @Test
    public void testDisconnectedUser(){
        lobby = new Lobby();
        RemoteInterfaceStub remoteInterfaceStub = new RemoteInterfaceStub();

        String username = "Giacomo";

        try {
            lobby.createUser(username);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.createGame(username, 2);
            lobby.getGameList().get(0).getVirtualView().setUser(username,remoteInterfaceStub);
        } catch (WrongPlayerException e) {
            System.out.println(e.getMessage());
        }


        String username2 = "Alberto";

        try {
            lobby.createUser(username2);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.getGameList().get(0).getVirtualView().setUser(username2,remoteInterfaceStub);
            lobby.joinGame(username2,0);
            assertTrue(lobby.getUserList().get(1).getInGame());
            lobby.disconnect_user(username2);
            lobby.removeUserFromGame(username2);
            assertFalse(lobby.getUserList().get(1).getInGame());
        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | IOException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testCreateUser3(){
        lobby = new Lobby();
        RemoteInterfaceStub remoteInterfaceStub = new RemoteInterfaceStub();

        String username = "Giacomo";

        try {
            lobby.createUser(username);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.createGame(username, 2);
            lobby.getGameList().get(0).getVirtualView().setUser(username,remoteInterfaceStub);
        } catch (WrongPlayerException e) {
            System.out.println(e.getMessage());
        }


        String username2 = "Alberto";

        try {
            lobby.createUser(username2);
        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            lobby.getGameList().get(0).getVirtualView().setUser(username2,remoteInterfaceStub);
            lobby.joinGame(username2,0);
        } catch (InvalidIDException | WrongPlayerException | IllegalValueException | IOException e) {
            System.out.println(e.getMessage());
        }

        lobby.disconnect_user(username2);
        lobby.removeUserFromGame(username2);
        User user2 = lobby.getUserList().get(1);

        try {
            lobby.createUser(username2);
            assertFalse(user2.getInGame());
            assertTrue(lobby.getUserList().get(1).getInGame());
            assertEquals(user2.getNickname(),lobby.getUserList().get(1).getNickname());
            assertSame(user2.getShelfie(),lobby.getUserList().get(1).getShelfie());
            assertSame(user2.getGame(),lobby.getUserList().get(1).getGame());

        } catch (ExistingNicknameException | EmptyNicknameException | RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSetServer(){
        Lobby lobby = new Lobby();
        ServerTCP serverTCP = new ServerTCP(50040);
        try {
            RMIImplementation rmiImplementation = new RMIImplementation();
            lobby.setServerRMI(rmiImplementation);
            lobby.setServerTCP(serverTCP);
            assertSame(serverTCP,lobby.getServerTCP());
            assertSame(rmiImplementation,lobby.getServerRMI());
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }
}