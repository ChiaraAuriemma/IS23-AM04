package it.polimi.it.network.client;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.ViewInterface;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface that ClientRMI and ClientTCP implements
 */
public interface ClientInterface extends Serializable {

    /**
     * Sends to the server the client's username
     * @param userName is the username chosen by the player
     * @throws RemoteException .
     * @throws IOException .
     */
    void login(String userName) throws RemoteException, IOException;


    /**
     * Communicates the server the number of people that the user wants in his new game
     * @param playerNumber is the number of player that the user wants in his new game
     * @throws RemoteException .
     */
    void createGame(int playerNumber) throws RemoteException;


    /**
     * Method that communicates to the server the ID of the game that the user wants to join
     * @param gameId is the ID of the game that the user wants to join
     * @throws RemoteException .
     */
    void joinGame(int gameId) throws RemoteException;


    /**
     * Communicates to the server the number of tiles that the user wants to take from the LivingRoom.
     * @param numOfTiles is the number of tiles that the user wants to take from the LivingRoom.
     * @throws RemoteException .
     */
    void tilesNumMessage(int numOfTiles) throws RemoteException;


    /**
     * Communicates to the server the list of tiles that the user wants to take from the LivingRoom.
     * @param choices is the list of tiles that the user wants to take from the LivingRoom.
     * @throws RemoteException .
     */
    void selectedTiles(List<Tile> choices) throws IOException;


    /**
     * Communicates to the server the column in which the user wants to put the tiles that he took from the LivingRoom.
     * @param column is the column in which the user wants to put the tiles that he took from the LivingRoom.
     * @throws RemoteException .
     */
    void chooseColumn(int column) throws RemoteException;


    /**
     * Sends to the server the latest chat message that the user wrote in the chat
     * @param chatMessage is the latest chat message that the user wrote in the chat
     * @throws RemoteException .
     */
    void sendChatMessage(String chatMessage) throws RemoteException;


    /**
     * Setter method: given a string, instances either a CLI or a GUI
     * @param viewChoice is the client's choice of CLI or GUI
     */
    void setView(String viewChoice) throws RemoteException;


    /**
     * Setter and initializer class GameStage that is used to set the client's stage during the game
     * @param gameStage is the new GameStage instance that will be linked to the client.
     *                  The stage is set to LOGIN.
     * @throws RemoteException .
     */
    void setGameStage(GameStage gameStage) throws RemoteException;


    /**
     * Getter method of the GameStage stage
     * @return the current turn Stage.
     * @throws RemoteException .
     */
    TurnStages getGameStage()throws RemoteException;


    /**
     * Forces the GameStage to be ENDGAME
     * @throws RemoteException .
     */
    void setStageToEndGame() throws RemoteException;

    /**
     * Forces the GameStage to be CREATEorJOIN
     * @throws RemoteException .
     */
    void setStageToCreate() throws RemoteException;


    /**
     * Method that sends the private chat message written by this player to the receiver
     * @param chatMessage is the message written by this player
     * @param receiver is the receiver of the message
     * @throws RemoteException .
     */
    void sendChatPrivateMessage(String chatMessage, String receiver) throws RemoteException;


    /**
     * Getter method of the View instance
     * @return the view instance
     * @throws RemoteException .
     */
    ViewInterface getView() throws RemoteException;


    /**
     * Getter method of the client's nickname
     * @return the nickname String
     * @throws RemoteException .
     */
    String getNickname()throws RemoteException;
}
