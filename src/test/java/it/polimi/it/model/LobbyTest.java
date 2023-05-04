/*package it.polimi.it.model;


import it.polimi.it.controller.Exceptions.EmptyNicknameException;
import it.polimi.it.controller.Exceptions.ExistingNicknameException;
import it.polimi.it.controller.Exceptions.NotExistingUser;
import it.polimi.it.controller.Lobby;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {

    private User user;
    private Lobby lobby;

    @Before
    public void CreateLobbyTest(){
        this.lobby = new Lobby();

        assertNotNull(lobby);
        assertNotNull(lobby.getUserList());
    }

    @After
    public void destroy(){
        lobby = null;
    }

    @Test
    public void createUserTest() throws EmptyNicknameException, ExistingNicknameException {

        user = lobby.createUser("Alby");

        assertNotNull(user);

        assertEquals(lobby.getUserList().get(0), user);
        assertEquals(lobby.getUserList().get(0).getNickname(), "Alby");

        user = lobby.createUser("Marco");

        assertNotNull(user);

        assertEquals(lobby.getUserList().get(1), user);
        assertEquals(lobby.getUserList().get(1).getNickname(), "Marco");

        user = lobby.createUser("");

        assertNotNull(user);

        assertEquals(lobby.getUserList().get(2), user);
        assertEquals(lobby.getUserList().get(2).getNickname(), "");

    }

    @Test
    public void joinGameTest() throws NotExistingUser, EmptyNicknameException, ExistingNicknameException {

        User user1 = lobby.createUser("Alby");

        assertNotNull(user1);

        assertEquals(lobby.getUserList().get(0), user1);
        assertEquals(lobby.getUserList().get(0).getNickname(), "Alby");

        User user2 = lobby.createUser("Marco");

        assertNotNull(user2);

        assertEquals(lobby.getUserList().get(1), user2);
        assertEquals(lobby.getUserList().get(1).getNickname(), "Marco");

        lobby.joinGame(user2);

        assertEquals(lobby.getUserList().size(), 1);
        assertEquals(lobby.getUserList().get(0), user1);
        assertEquals(lobby.getUserList().get(0).getNickname(), "Alby");

    }

    @Test
    public void createGameTest() throws NotExistingUser, EmptyNicknameException, ExistingNicknameException {

        User user1 = lobby.createUser("Alby");

        assertEquals(lobby.getUserList().size(), 1);
        assertEquals(lobby.getUserList().get(0), user1);
        assertEquals(lobby.getUserList().get(0).getNickname(), "Alby");

        lobby.createGame(user1,4);

        assertNotNull(lobby.getGame());

        assertEquals(lobby.getUserList().size(), 0);

    }


}
*/