package it.polimi.it.network.client;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.ViewInterface;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Serializable {

    /**
     * Sends to the Server the
     * @param userName String chosen by the player
     * @throws IOException .
     */
    void login(String userName) throws IOException;


    /**
     * Communicates the server the number of people that the user wants in his new game
     * @param playerNumber .
     * @throws RemoteException .
     */
    void createGame(int playerNumber) throws RemoteException;


    /**
     * Method that communicates to the server the ID of the game that the user wants to join
     * @param gameId .
     * @throws RemoteException .
     */
    void joinGame(int gameId) throws RemoteException;


    /**
     * Communicates to the server the
     * @param numOfTiles number of tiles that the user wants to take from the LivingRoom.
     * @throws RemoteException .
     */
    void tilesNumMessage(int numOfTiles) throws RemoteException;


    /**
     * Communicates to the server the
     * @param choices list of tiles that the user wants to take from the LivingRoom.
     * @throws RemoteException .
     */
    void selectedTiles(List<Tile> choices) throws IOException;


    /**
     * Communicates to the server the
     * @param column column in which the user wants to put the tiles that he took from the LivingRoom.
     * @throws RemoteException .
     */
    void chooseColumn(int column) throws RemoteException;


    /**
     * Sends to the server the latest
     * @param chatMessage that the user wrote in the chat
     * @throws RemoteException .
     */
    void sendChatMessage(String chatMessage) throws RemoteException;


    /**
     * Setter method: given a
     * @param viewChoice string, instances either a CLI or a GUI
     */
    void setView(String viewChoice) throws RemoteException;


    /**
     * Setter and initializer method
     * @param gameStage is the new GameStage instance that will be linked to the client.
     *                  The stage is set to LOGIN.
     * @throws RemoteException .
     */
    void setGameStage(GameStage gameStage) throws RemoteException;


    /**
     * Getter method
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
     * Method that sends the private
     * @param chatMessage written by this player to the
     * @param receiver of the message
     * @throws RemoteException .
     */
    void sendChatPrivateMessage(String chatMessage, String receiver) throws RemoteException;


    /**
     * Getter method
     * @return the view instance
     * @throws RemoteException .
     */
    ViewInterface getView() throws RemoteException;


    /**
     * Getter method
     * @return the nickname String
     * @throws RemoteException .
     */
    String getNickname()throws RemoteException;
}
