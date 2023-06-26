package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.Exceptions.IllegalValueException;
import it.polimi.it.Exceptions.WrongTileException;

import it.polimi.it.model.Tiles.Tile;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * User class:
 *  For each player, one instance of the User class is created;
 *  it contains instances of chat and shelfie.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 8107849055941758371L;
    private Board board;
    private Shelfie shelf;
    private Game game;
    private int gameid;
    private int tilesNumber;
    private final String nickname;
    private boolean inGame;
    private Chat chat;


    /**
     * Method that creates a new instance of User given
     * @param nickname chosen by the player.
     * The method also sets the new shelf and chat for the user
     */
    public User(String nickname){
        this.nickname = nickname;
        this.shelf=new Shelfie(this);
        this.inGame = false;
        this.chat = new Chat();
    }


    /**
     * Method that
     * @return the maximum number of tiles that the user can take to put them in the shelfie
     * @throws IllegalValueException .
     * @throws IOException .
     */
    public int maxValueOfTiles() throws IllegalValueException, IOException {
        if(!inGame){
            return -1;
        }
        int max = shelf.possibleTiles();

        max = board.findMaxAdjacent(max);
        System.out.println("Asking to " + nickname + "the tiles num \n");
        game.getVirtualView().startTurn(this.getNickname(),max);
        return max;
    }


    /**
     * Given
     * @param tilesNum the number of tiles that the player wants to get from the board
     * @return the list of lists that contain the tiles valid so that the player can take them from the board
     * @throws IllegalValueException .
     * @throws RemoteException .
     */
    public List<List<Tile>> choosableTiles(int tilesNum) throws IllegalValueException, RemoteException {
        if(tilesNum < 1 || tilesNum > 3){
            throw new IllegalValueException("Wrong tiles number");
        }
        tilesNumber = tilesNum;
        List<List<Tile>> choosableList = board.choosableTiles(tilesNum);
        game.getVirtualView().takeableTiles(this.getNickname(),choosableList, tilesNum);
        return choosableList;
    }


    /**
     * Method that, given a list of tiles that the player chose, checks the tiles
     * and finds which columns may contain the selected tiles
     * @param chosen is the list of the tiles that the player chose
     * @return an array of booleans (one each column)
     *          True:   this column can be chosen
     *          False:  this column can't be chosen
     * @throws WrongTileException .
     */
    public boolean[] chooseSelectedTiles(List<Tile> chosen) throws WrongTileException {
        if(chosen.size() != tilesNumber){
            throw  new WrongTileException("you have to select " + tilesNumber + " tiles\n");
        }
        for(Tile t : chosen){
            if(t.getColor().equals("XTILE") || t.getColor().equals("DEFAULT")){
                throw new WrongTileException("Tiles of this type can't be chosen");
            }
        }
        boolean [] columns = shelf.chooseColumn(tilesNumber);
        game.getVirtualView().possibleColumns(this.getNickname(),columns);
        return columns;
    }


    /**
     * Method to insert a certain number of tiles in a given column of the user's shelfie
     * @param column is the column where the tiles have to be put
     * @param chosen is a list of the tiles that have to be put in the given column
     * @return the boolean that represents the failure or the success of the latest operation
     * @throws RemoteException .
     */
    public boolean insertTile(int column, List<Tile> chosen) throws RemoteException {
        boolean isEnd;
        isEnd = this.getShelfie().addTile(column, chosen);
        game.getVirtualView().shelfieUpdate(this);
        board.removeTiles(chosen);
        boolean refill = board.refill();
        if(refill){
            game.getVirtualView().boardRefill();
        }
        game.getVirtualView().boardUpdate(board.getMatrix());
        return isEnd;
    }


    /**
     * Method to initialize a new Shelfie
     * @return the new Shelfie Instance
     */
    public Shelfie createShelfie() {
        this.board = this.game.getBoard();
        return this.shelf = new Shelfie(this);
    }


    /**
     * Getter method
     * @return this user's shelfie instance
     */
    public Shelfie getShelfie(){
        return this.shelf;
    }


    /**
     * Getter method
     * @return the board instance
     */
    public Board getBoard(){
        return this.board;
    }


    /**
     * Getter method
     * @return the nickname String.
     */
    public String getNickname() {
        return this.nickname;
    }


    /**
     * Setter method
     * @param game is the game instance that has to be linked to this user
     * The method also sets the this.inGame boolean parameter to true.
     */
    public void setGame(Game game){
        this.game = game;
        this.gameid = game.getGameid();
        this.inGame = true;
    }


    /**
     * Getter method
     * @return the game instance which this user is part of.
     */
    public Game getGame(){
        return this.game;
    }

    /**
     * Getter method
     * @return the gameid.
     */
    public int getGameid() {
        return this.gameid;
    }

    /**
     * Getter method
     * @return the inGame boolean state.
     */
    public boolean getInGame() {
        return this.inGame;
    }


    /**
     * Setter method for this.inGame
     * @param state is the boolean parameter used for the setter operation.
     */
    public void setInGame(boolean state) {
        inGame=state;
    }


    /**
     * Setter method for this user's
     * @param shelfie instance.
     */
    public void setShelfie(Shelfie shelfie) {
        this.shelf = shelfie;
    }


    /**
     * Setter method for a
     * @param board instance.
     */
    public void setBoard(Board board) {
        this.board=board;
    }


    /**
     * Method used to
     * @return the latest 9 lines of message sent to this user to be displayed in the view.
     */
    public List<String> getChatList() {
        return chat.getCurrentChat();
    }


    /**
     * Method to push a new public message in the chat data structure
     * @param message is the message to be put in the chat.
     */
    public void newMessage(String message){
        chat.newMessage(message);
    }


    /**
     * Method to push a new private message in the chat data structure
     * @param chatMessage is the message to be put in the chat.
     */
    public void newPrivateMessage(String chatMessage) {
        chat.newPrivateMessage(chatMessage);
    }

    /**
     * Method used to
     * @return the tilesNumber of the player who is playing.
     */
    public int getTilesNumber() {
        return tilesNumber;
    }

    /**
     * Method used to
     * set the tilesNumber for testing.
     */
    public void setTilesNumber(int tilesNumber) {
        this.tilesNumber = tilesNumber;
    }

    /**
     * Method used to
     * @return the chat for testing
     */
    public Chat getChat() {
        return chat;
    }
}
