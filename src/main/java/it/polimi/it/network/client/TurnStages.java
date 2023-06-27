package it.polimi.it.network.client;

import java.io.Serializable;

/**
 * Enumeration that manages the stages of the client from when it connects to when it disconnects
 */
public enum TurnStages implements Serializable {
    /**
     * "Not your Turn" stage
     */
    NOTURN,


    /**
     * The game hasn't started yet
     */
    NOTHING,


    /**
     * User still has to log in.
     */
    LOGIN,


    /**
     * User must choose whether he wants to create a new game or joining an existing one.
     */
    CREATEorJOIN,


    /*
    * User's latest game ended, the player has now to choose if he wants to play again. 
    */
    ENDGAME, 

    
    /**
     * User must choose the number of tiles he wants to take from the board.
     */
    TILESNUM,


    /**
     * User must choose the tiles he wants to take from the board.
     */
    CHOOSETILES,


    /**
     * User must choose the column of his Shelfie in which he wants to put
     * the tiles he previously took from the board.
     */
    CHOOSECOLUMN;

}
