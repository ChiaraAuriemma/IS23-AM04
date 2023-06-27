package it.polimi.it.network.client;

import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface that ClientRMI and ClientTCPHandler implements
 */
public interface RemoteInterface extends Remote, Serializable {


    /**
     * Forces the View to print a given String
     * @param s that contains an error message.
     * @throws RemoteException .
     */
    void printError(String s)  throws RemoteException;


    /**
     * Setter Method for the EndToken in the view.
     * @param username is the player who won the endToken.
     * @throws RemoteException .
     */
    void setEndToken(String username) throws RemoteException;


    /**
     * Setter method for the final points of the match, given a
     * @param usernames list and a
     * @param points list.
     * @throws RemoteException .
     */
    void setFinalPoints(List<String> usernames, ArrayList<Integer> points) throws IOException;


    /**
     * Setter method that is used in case of a reconnection,
     * sets every parameter in order to let the player perform the right actions.
     * @param gameID ,
     * @param matrix is the LivingRoom,
     * @param shelfies is a list of the players' shelves,
     * @param id1 is the ID of the first CommonGoalCard,
     * @param id2 is the ID of the second CommonGoalCard,
     * @param personalGoalCard is the player's PersonalGoalCard,
     * @param points a list of the players' current points,
     * @param playerList is the list of the players' nicknames.
     * @throws RemoteException .
     */
    void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) throws RemoteException;


    /**
     * Forces the view to update the scene.
     * @throws RemoteException .
     */
    void updateView() throws RemoteException;


    /**
     * Sends to the view the latest chat messages.
     * @param currentChat is the list of messages.
     * @throws RemoteException .
     */
    void updateChat(List<String> currentChat) throws IOException;


    /**
     * Notifies the view and the client that the game is started.
     * @throws RemoteException .
     */
    void setStageToNoTurn() throws RemoteException;


    /**
     * Notifies that a LivingRoom refill has just happened.
     * @throws RemoteException .
     */
    void boardRefill() throws RemoteException;


    /**
     * Setter method for the
     * @param order players' order.
     * @throws RemoteException .
     */
    void setStartOrder(ArrayList<String> order) throws RemoteException;


    /**
     * Setter method for the new
     * @param matrix LivingRoom's disposition.
     * @throws RemoteException .
     */
    void setNewBoard(Tile[][] matrix) throws RemoteException;


    /**
     * Setter method for the common cards and their tokens.
     * @param id1 CommonGoalCard's ID 1,
     * @param id2 CommonGoalCard's ID 2,
     * @param commonToken1 CommonGoalCard's token 1,
     * @param commonToken2 CommonGoalCard's token 2,
     * @throws RemoteException .
     */
    void setNewCommon(int id1, int id2, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException;


    /**
     * Setter method for the player's
     * @param card PersonalGoalCard
     * @throws RemoteException .
     */
    void setNewPersonal(PersonalGoalCard card) throws RemoteException;


    /**
     * Setter method for the player's Shelfie.
     * @param username is the player's nickname,
     * @param shelfie is the player's Shelfie.
     * @throws RemoteException .
     */
    void setNewShelfie(String username, Tile[][] shelfie) throws RemoteException;


    /**
     * Setter method for various points related parameters.
     * @param username ,
     * @param points ,
     * @param commonToken1 is the list of the player's tokens for the first CommonGoalCard,
     * @param commonToken2 is the list of the player's tokens for the second CommonGoalCard,
     * @throws RemoteException .
     */
    void setNewPoints(String username, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException;


    /**
     * Notifies that the player's turn just started.
     * @param maxValueofTiles is the maximum number of tiles that the player might choose to take from the LivingRoom.
     * @throws RemoteException .
     */
    void notifyTurnStart(int maxValueofTiles) throws RemoteException;


    /**
     * Notifies that the player must choose the column
     * in which he wishes to put the tiles that he took from the LivingRoom.
     * @param choosableColumns is a boolean array,
     *                         the true indexes represent the columns where the player may put the tiles.
     * @throws RemoteException .
     */
    void askColumn(boolean[] choosableColumns) throws RemoteException;


    /**
     * Notifies that the player must choose the tiles that he wants to take from the LivingRoom.
     * @param choosableTilesList is a list of groups of tiles
     *                           which are all the possible groups of tiles that the player might choose.
     * @param num is the exact number of tiles that the player has to take.
     * @throws RemoteException .
     */
    void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException;


    /**
     * Ping method, used by the server to check if the player is still online.
     * @throws RemoteException .
     */
    void ping() throws RemoteException;

    /**
     * After the end of a game this method gives the possibility to create a new game.
     * @throws RemoteException .
     */
    void restart() throws RemoteException;
}
