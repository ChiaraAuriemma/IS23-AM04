package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.client.RemoteInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote, Serializable {

    /**
     * Method called by a client when he needs to perform a login.
     * @param cr is the client's RemoteInterface
     * @param username is the username that the player chose.
     * @return the same nickname string to confirm the login.
     * @throws RemoteException ,
     * @throws ExistingNicknameException ,
     * @throws EmptyNicknameException ,
     * @throws InvalidIDException .
     */
    String login(RemoteInterface cr, String username) throws RemoteException, ExistingNicknameException, EmptyNicknameException, InvalidIDException;


    /**
     * Method invoked by a client in order to create a new Game.
     * @param username is the player's nickname
     * @param playerNumber is the number of players that the host wants to join to the game.
     * @param client is the RemoteInterface of who invoked the method.
     * @return the GameID of the newly created game.
     * @throws RemoteException .
     * @throws WrongPlayerException .
     */
    int createGame(String username, int playerNumber, RemoteInterface client) throws RemoteException, WrongPlayerException;


    /**
     * Method invoked by a client in order to join a Game.
     * @param username is the player's nickname
     * @param id is the ID of the game that the player wants to join.
     * @param client is the RemoteInterface of who invoked the method.
     * @return the GameID of the newly created game.
     * @throws IOException .
     * @throws InvalidIDException .
     * @throws WrongPlayerException .
     * @throws IllegalValueException .
     */
    int joinGame(String username,int id, RemoteInterface client) throws IOException, InvalidIDException, WrongPlayerException, IllegalValueException;


    /**
     * Method invoked by a client in order to choose the number of tiles to be taken from the board.
     * @param username is the player's nickname
     * @param numTiles is the number of tiles that the player wants to retrieve from the LivingRoom Board.
     * @throws IOException .
     * @throws WrongPlayerException .
     * @throws IllegalValueException .
     * @throws InvalidIDException .
     */
    void tilesNumMessage(String username,int numTiles) throws IOException, WrongPlayerException, IllegalValueException, InvalidIDException;


    /**
     * Method invoked by a client in order to take some tiles from the Board.
     * @param username is the player's nickname
     * @param choosenTiles is the list of tiles that the player chose to take from the LivingRoom Board.
     * @throws IOException .
     * @throws WrongPlayerException .
     * @throws WrongListException .
     * @throws IllegalValueException .
     * @throws InvalidIDException .
     * @throws WrongTileException .
     */
    void selectedTiles(String username,List<Tile> choosenTiles) throws IOException, WrongPlayerException, WrongListException, IllegalValueException, InvalidIDException, WrongTileException;


    /**
     * Method invoked by a client in order to put the tiles in a certain column of the Shelfie.
     * @param username is the player's nickname
     * @param columnNumber is the chosen column.
     * @throws IOException .
     * @throws InvalidIDException .
     * @throws IllegalValueException .
     */
    void chooseColumn (String username,int columnNumber) throws IOException, InvalidIDException, IllegalValueException;


    /**
     * Method invoked by a client in order to send a chat message.
     * @param chatMessage is the player's nickname
     * @param message is the message.
     * @throws RemoteException .
     */
    void chatMessage(String chatMessage, String message) throws RemoteException;


    /**
     * Method invoked by a client in order to send a private chat message.
     * @param sender is the sender's username.
     * @param chatMessage is the message
     * @param receiver is the receiver's username.
     * @throws RemoteException .
     * @throws IllegalValueException .
     */
    void chatPrivateMessage(String sender, String chatMessage, String receiver) throws RemoteException, IllegalValueException;
}