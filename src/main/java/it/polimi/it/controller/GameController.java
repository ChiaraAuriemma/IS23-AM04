package it.polimi.it.controller;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class GameController implements Serializable {

    private static final long serialVersionUID = 9016415749066492223L;

    /**
     * Is the total number of players in the game
     */
    private int numOfPlayers;

    /**
     * Univocal ID for the current game
     */
    private int gameID;

    /**
     * Index of the playerList, represents the player who is able
     * to make a move this turn
     */
    private int currentPlayer=0;

    /**
     * Flag to avoid continuing the game if somebody has already finished
     */
    private boolean endGame;

    /**
     * List of the players, with a maximum size of 4
     * The players contained in the list are already ordered in the same way as they move during the game
     */
    private List<User> playerList;

    /**
     * Reference to the Lobby, used to destroy the game, when this ends
     */
    private Lobby lobby;

    /**
     * Reference to the Game
     */
    private Game game;

    /**
     * List of tiles chosen in the current turn by the current player
     */
    private List<Tile> currentTilesList = new ArrayList<>();

    /**
     * Max number of tiles take-able in a certain turn
     */
    private int maxTile;

    /**
     * boolean array which represents the empty columns with true
     */
    private boolean[] possibleColumnArray;

    Timer timer;


    /**
     * Constructor method
     * @param game is the game which this controller references to
     * @param lobbyIn is the reference to the external lobby
     */
    public GameController(Game game, Lobby lobbyIn){
        this.endGame = false;
        this.game=game;
        numOfPlayers = game.getNumplayers();
        gameID = game.getGameid();
        lobby = lobbyIn;
        playerList = new ArrayList<>();
        this.maxTile = 0;
    }


    /**
     * Method used to control which player needs to move in the current turn
     * @throws InvalidIDException exception used when the game ID is wrong or non-existent
     */
    public void turnDealer() throws InvalidIDException, IOException {

        if(this.endGame && this.currentPlayer == game.getNumplayers()-1){
            game.getVirtualView().viewUpdate();
            game.pointsFromAdjacent();
            lobby.notifyEndGame(gameID);
        }else{
            System.out.println("turndealer");
            currentPlayer = (currentPlayer + 1) % game.getNumplayers();
            System.out.println("turndealer: player " + currentPlayer + "\n");
            if(!playerList.get(currentPlayer).getInGame()){
                if(checkIfEverybodyIsDisconnected()){
                    lobby.notifyEndGame(gameID);
                    return;
                } else if (numDisconnected() == 1) {
                    freezeGame();
                    return;
                } else{
                    while(!playerList.get(currentPlayer).getInGame()){
                        currentPlayer= (currentPlayer + 1) % game.getNumplayers();
                        System.out.println("turndealer - while: player " + currentPlayer + "\n");
                    }
                }
            }
            firstOperation();
        }
    }

    /**
     * Method that freezes the game if there aren't enough people online
     */
    private void freezeGame(){
        System.out.println("freeze game");
        new Thread(this::controlTimer).start();
    }


    /**
     * controlTimer:
     * Gives every player 1 minute to reconnect to the game, otherwise, the next player will make his turn
     *  and the player who disconnected will be skipped.
     */
    private void controlTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
            System.out.println("One minute has passed for Game" + gameID);
            if (numDisconnected() == 1){
                try {
                    System.out.println("Closing game " + gameID + " due to lack of online players");
                    game.getVirtualView().notifyEndGameDisconnection();
                    lobby.notifyEndGame(gameID);
                } catch (InvalidIDException  e) {
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    System.out.println("There are enough players in game " + gameID + " to continue");
                    turnDealer();
                } catch (InvalidIDException |  IOException e) {
                    throw new RuntimeException(e);
                }
            }
            }
        };
        long delay = 10000;
        timer.schedule(task, delay);
    }


    /**
     * Method that
     * @return the number of players of this game that disconnected.
     */
    private int numDisconnected() {
        int num=0;
        for (User u: playerList){
            if (u.getInGame()){
                num++;
            }
        }
        return num;
    }


    /**
     * Method that checks if any participant of the game is still online
     * @return a boolean value: true->somebody is still online
     *                          false->everybody is offline or disconnected.
     */
    public boolean checkIfEverybodyIsDisconnected() {
        for (User u: playerList){
            if (u.getInGame()){
                return false;
            }
        }
        return true;
    }


    /**
     * Method to get the maximum possible number of tiles that can be selected from the board
     * Calls the view to ask the payer about how many tiles the player wants to retrieve
     */
    public void firstOperation() throws  IOException, InvalidIDException {
        if(!playerList.get(currentPlayer).getInGame()) {
            turnDealer();
        }else{
            try{
                this.maxTile = playerList.get(currentPlayer).maxValueOfTiles();
            }catch (IllegalValueException e){
                turnDealer();
                return;
            }
            game.getVirtualView().notifyTurnStart(playerList.get(currentPlayer));
            game.getVirtualView().viewUpdate();
        }
    }


    /**
     * Gets from the view the number of tiles that the current player wants to retrieve from the board
     * The method also sends to the view the list of lists of tiles that the user can choose
     *  in order to highlight them on the board.
     * @param chosenNumber is the input from the user
     */
    public void getFromViewNTiles(String user, int chosenNumber) throws WrongPlayerException, IOException, IllegalValueException, InvalidIDException {
        if(!playerList.get(currentPlayer).getInGame()) {
            turnDealer();
        }else {
            if (user.equals(playerList.get(currentPlayer).getNickname())) {
                if (chosenNumber > this.maxTile) {
                    throw new IllegalValueException("You can't take so much tiles");
                } else {
                    this.playerList.get(currentPlayer).choosableTiles(chosenNumber);
                }
            } else {
                throw new WrongPlayerException("It's not your turn");
            }
        }
    }


    /**
     * Method to get the list of tiles the user has selected from the board,
     * The method also sends to the view the list of 'empty-enough' columns in which the chosen
     *  tiles might be put in.
     * @param chosenList is the list of selected tiles
     * @throws WrongTileException exception used when a wrong tile is selected
     */
    public void getTilesListFromView(String user, List<Tile> chosenList) throws WrongPlayerException, WrongListException, IllegalValueException, InvalidIDException, IOException, WrongTileException {
        if(!playerList.get(currentPlayer).getInGame()) {
            turnDealer();
        }else {
            if (user.equals(playerList.get(currentPlayer).getNickname())) {
                if (validTilesCheck(chosenList)) {
                    currentTilesList.clear();
                    currentTilesList = new ArrayList<>(chosenList);
                    possibleColumnArray = playerList.stream().filter(curr -> Objects.equals(curr.getNickname(), user)).collect(Collectors.toList()).get(0).chooseSelectedTiles(currentTilesList);
                } else {
                    throw new WrongListException("You chose badly");
                }
            } else {
                throw new WrongPlayerException("It's not your turn");
            }
        }
    }


    /**
     * Checks if the parameter is present in the List of Lists of Tiles
     * @param chosenList is the chosen List of tiles
     * @return true is the choice is valid
     */
    private boolean validTilesCheck(List<Tile> chosenList) {
        List<List<Tile>> choosable = game.getBoard().choosableTiles(this.playerList.get(currentPlayer).getTilesNumber());
        return choosable.stream().anyMatch(list -> new HashSet<>(list).containsAll(chosenList)) && chosenList.size() == this.playerList.get(currentPlayer).getTilesNumber();
    }


    /**
     * Method that gets from the view the column that the player wants to put his chosen tiles and
     *  in which order he wants the tiles to be.
     *  The method also calls the view to display the new points acquired by the player
     * @param col is the column of the shelfie where to put the tiles in
     */
    public void getColumnFromView(String user, int col) throws IllegalValueException, InvalidIDException, IOException {
        if(col >= 0 && col <= 4) {
            if (!playerList.get(currentPlayer).getInGame()) {
                turnDealer();
            } else {
                if (!possibleColumnArray[col]) {
                    throw new IllegalValueException("Invalid column choice");
                }

                boolean firstEnder = playerList.stream().filter(curr -> Objects.equals(curr.getNickname(), user)).collect(Collectors.toList()).get(0).insertTile(col, currentTilesList);
                if(!this.endGame && firstEnder){
                    this.endGame = true;
                }

                game.pointCount(playerList.get(currentPlayer));
                if (firstEnder) {
                    game.endGame(playerList.get(currentPlayer));
                }
                turnDealer();
            }
        }else{
            throw new IllegalValueException("Invalid column choice");
        }
    }


    /**
     * Method used to initialize the parameters of the game, called before the firs turn of the game
     * Calls the view in order to display the initial board and the cards that have been extracted randomly
     */
    public void firstTurnStarter() throws IOException, InvalidIDException {
        game.getVirtualView().notifyTurnStart(null);
        playerList = game.randomPlayers();
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCards();
        currentPlayer=0;
        firstOperation();
    }


    /**
     * Getter method
     * @return the reference to the game controlled by this GameController
     */
    public Game getGame(){
        return this.game;
    }


    /**
     * Getter method
     * @return the list of all the players in the game
     */
    public List<User> getPlayerList() {
        return playerList;
    }


    /**
     * Method to reset the instances of
     * @param user user and
     * @throws RemoteException .
     */
     public void resetGame(User user) throws RemoteException {
        Tile[][] matrix = game.getBoard().getMatrix();
        ArrayList<Tile[][]> shelfies = new ArrayList<>();
        for(int i=0; i < game.getNumplayers(); i++){
            shelfies.add(playerList.get(i).getShelfie().getShelf());
        }
        CommonGoalCard card1 = game.getCommonCard1();
        CommonGoalCard card2 = game.getCommonCard2();
        PersonalGoalCard personalGoalCard = game.getPersonalCard(user);
        ArrayList<Integer> points = new ArrayList<>();
        for(int i=0; i < game.getNumplayers(); i++){
            points.add(game.getPoint(playerList.get(i)));
        }
        ArrayList<String> playersOrder = new ArrayList<>();
        for(int i=0; i < game.getNumplayers(); i++){
            playersOrder.add(playerList.get(i).getNickname());
        }
        game.getVirtualView().resetAfterDisconnection(user.getNickname(), game.getGameid(), matrix, shelfies, card1, card2, personalGoalCard, points, playersOrder);
    }


    /**
     * Method to insert a new message in the chat records
     * @param chatMessage is the latest chat message
     * @throws RemoteException Exception that might come from connection issues
     */
    public void pushChatMessage(String chatMessage) throws RemoteException {
        for(User user: playerList) {
            user.newMessage(chatMessage);
            game.getVirtualView().sendChatUpdate(user.getChatList(), user);
        }
    }


    /**
     * Method that swaps an old instance
     * @param old of a User class with its newer
     * @param newborn reconnected version.
     * @throws RemoteException
     */
    public void swapPlayers(User old, User newborn) throws RemoteException {
        playerList.set(playerList.indexOf(old), newborn);
        game.swapPlayers(old, newborn);
    }


    /**
     * Given
     * @param sender ,
     * @param chatMessage and
     * @param receiver inserts in the right data structures the private messages that were sent
     * @throws RemoteException .
     * @throws IllegalValueException .
     */
    public void pushChatPrivateMessage(String sender, String chatMessage, String receiver) throws RemoteException, IllegalValueException {
        while (sender.length()<12){
            sender = sender.concat(" ");
        }
        while (receiver.length()<12){
            receiver = receiver.concat(" ");
        }
        String finalReceiver = receiver;
        if(playerList.stream().noneMatch(user -> user.getNickname().equals(finalReceiver))){
            throw new IllegalValueException("There is no player with this nickname!");
        }
        for(User u : playerList){
            if(u.getNickname().equals(receiver)){
                u.newPrivateMessage(chatMessage);
                game.getVirtualView().sendChatUpdate(u.getChatList(), u);
            }
        }
        for(User u : playerList){
            if(u.getNickname().equals(sender)){
                u.newPrivateMessage(chatMessage);
                game.getVirtualView().sendChatUpdate(u.getChatList(), u);
            }
        }
    }


    /****************************************************
     *                                                  *
     *                                                  *
     * Methods inserted just for test purposes          *
     *                                                  *
     *                                                  *
     ****************************************************/
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getGameID() {
        return gameID;
    }

    public int getMaxTile() {
        return maxTile;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public int getPlayerNumber(User user){
        return playerList.indexOf(user);
    }

    public User getUser(String nickname){
        return playerList.stream().filter(user -> user.getNickname().equals(nickname)).collect(Collectors.toList()).get(0);
    }

    public boolean getEndGame(){
        return this.endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPlayerList(List<User> playerList) {
        this.playerList = playerList;
    }

    public void setMaxTile(int maxTile) {
        this.maxTile = maxTile;
    }

    public void setPossibleColumnArray(boolean[] possibleColumnArray) {
        this.possibleColumnArray = possibleColumnArray;
    }
}
