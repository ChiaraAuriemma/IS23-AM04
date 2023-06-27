package it.polimi.it.view;

import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * View Interface. Contains the methods that are implemented by the CLI and GUI classes.
 */
public interface ViewInterface {

    /**
     * Setter method for the player's turn order
     * @param order is the ordered arrayList of the users in the game
     */
    void setOrderView(ArrayList<String> order);


    /**
     * Setter method for the LivingRoom's board.
     * @param matrix is the tile matrix that represents the LivingRoom.
     */
    void setBoardView(Tile[][] matrix);


    /**
     * Setter method for the user-shelf hashmap
     * @param player  is the current user
     * @param shelfie is the new shelfie, used to update the previous shelfie value
     */
    void setPlayersShelfiesView(String player, Tile[][] shelfie);


    /**
     * Setter Method
     * @param card given the personal card, sets the example Shelf to get it visualized on screen
     */
    void setPlayersPersonalCardView(PersonalGoalCard card);


    /**
     * Setter method
     * @param id given the card id, chooses the right card's visualization.
     */
    void setCommon1View(int id);


    /**
     * Setter method
     * @param id given the card id, chooses the right card's visualization.
     */
    void setCommon2View(int id);


    /**
     * Stores in the cli
     * @param choosableTilesList the list of lists of tiles that the player may choose in this turn
     * @param num is the number of tiles that the player has to take from the LivingRoom.
     */
    void takeableTiles(List<List<Tile>> choosableTilesList, int num);



    /**
     * Method that prints the list of his Shelfie's columns that have enough empty spaces to contain the new Tiles.
     * @param choosableColumns is the list of the empty columns.
     */
    void setPossibleColumns(boolean[] choosableColumns);


    /**
     * Method that prints an error String.
     * @param error .
     */
    void printError(String error);


    /**
     * Setter method, used when a player's points amount changed
     * @param player is the user
     * @param points is the new value of the user's points
     */
    void setPlayersPointsView(String player, int points);


    /**
     * Setter method
     * @param users is the players list
     * @param points is the list of the players' points.
     */
    void setFinalPoints(List<String> users, ArrayList<Integer> points) throws IOException;


    /**
     * Setter for the
     * @param gameId .
     */
    void setGameID(int gameId);


    /**
     * Setter method: sets the EndToken to the player which has
     * @param user as nickname.
     */
    void setEndToken(String user);


    /**
     * Method that, at the beginning of the player's turn, asks him the number of tiles that he wants to get from the board.
     * @param maxValueofTiles is the maximum number of tiles that the player can get from the board.
     * @param username is the palyer's nickname.
     */
    void NotifyTurnStart(int maxValueofTiles, String username) ;


    /**
     * Method that updates the CLI
     */
    void update() ;


    /**
     * Forces the chat to be updated
     * @param currentChat is the list of the newest chat messages.
     */
    void updateChat(List<String> currentChat) throws IOException;


    /**
     * Sets the player (this client)'s name to the given
     * @param nickname .
     */
    void setThisNick(String nickname);


    /**
     * Given
     * @param row row  and
     * @param col column of a tile in th board
     * @return the color of that tile
     */
    String getTileColor(int row, int col);


    /**
     * Asks the player to insert his nickname.
     */
    void askNickname();


    /**
     * Method that asks the player whether he wants to Create a new Game or Join an existing one
     * @param username is the player's nickname.
     */
    void joinOrCreate(String username) throws IOException;


    /**
     * Prints the current position and color of a tile
     * @param color is the tile's color
     * @param row is the tile's row
     * @param column is the tile's column
     */
    void printTile(String color, int row, int column);


    /**
     * Prints some text or colors.
     * @param s is the character.
     */
    void printThings(String s);


    /**
     * Method that prints all the useful commands.
     */
    void printCommands();


    /**
     * Method that asks the player in whick column he wants to put the tiles that he took.
     */
    void askColumn() throws IOException;


    /**
     * Notifies the LivingRoom Board's refill.
     */
    void boardRefill();


    /**
     * Forces a Cli update after the reconnection to the game
     * @param gameID is the gameID of the game that the player just reconnected to.
     * @param matrix is the LivingRoom Board
     * @param shelfies is a List of the Players' Shelfies
     * @param id1 is the first CommonGoalCard
     * @param id2 is the second CommonGoalCard
     * @param personalGoalCard is the player's PersonaleGoalCard
     * @param points is the list of the players' points
     * @param playerList is the list of the players, ordered as their turns are.
     */
    void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList);


    /**
     * method that restart all the variable of the game
     */
    void clean();
}
