package it.polimi.it.network.server;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.RemoteInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that manages the communication of the controllers with the clients
 */
public class VirtualView implements Serializable {

    private static final long serialVersionUID = -3906432862304671330L;
    private Game game;
    private ServerTCP serverTCP;
    private RMIImplementation serverRMI;
    private HashMap<String, RemoteInterface> users;
    private List<String> disconn_users ;

    /**
     * Constructor of the class
     */
    public VirtualView(){
        users = new HashMap<>();
        disconn_users = new ArrayList<>();
    }


    /**
     * Setter method for the instance of
     * @param game Game that this VirtualView handles.
     */
    public void setGame(Game game){
        this.game = game;
    }


    /**
     * Setter method for a player in the game.
     * @param username is the player's nickname
     * @param remoteInterface is the RemoteInterface of the player (implemented in TCP or RMI)
     */
    public void setUser(String username, RemoteInterface remoteInterface){
        users.put(username, remoteInterface);
    }


    /**
     * Sends to the clients of this game the players' turn order.
     * @param order is the ordered ArrayList of players.
     */
    public void startOrder(ArrayList<User> order) {
        ArrayList<String> orderNames = new ArrayList<>();
        for(User u: order){
            orderNames.add(u.getNickname());
        }
        for (int i=0; i < game.getNumplayers(); i++){
            String username = game.getPlayer(i).getNickname();
            if(!disconn_users.contains(username)) {
                try {
                    RemoteInterface client = users.get(username);
                    client.setStartOrder(orderNames);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + username + "/n");
                }
            }
        }
    }


    /**
     * Adds a certain user into the list of disconnected player after his disconnection.
     * @param username is the disconnected player.
     */
    public void insertDisconnection (String username){
        disconn_users.add(username);
    }


    /**
     * Removes a certain user from the list of disconnected player after his reconnection.
     * @param username is the reconnected player.
     */
    public void removeDisconnection (String username){
        disconn_users.remove(username);
    }


    /**
     * Sends to the client the initial LivingRoom board's disposition
     * @param matrix is the board.
     */
    public void initialMatrix(Tile[][] matrix) {
        for (int i=0; i < game.getNumplayers(); i++) {
            String username = game.getPlayer(i).getNickname();
            if(!disconn_users.contains(username)){
                try{
                    RemoteInterface client = users.get(username);
                    client.setNewBoard(matrix);
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + username +"/n");
                }
            }
        }
    }


    /**
     * Sends to every player in the game the CommonGoalCards that were drawn from the game and their respective token lists.
     * @param card1 is the first CommonGoalCard.
     * @param card2 is the second CommonGoalCard.
     * @param commonToken1 is the list of tokens of the first CommonGoalCard.
     * @param commonToken2 is the list of tokens of the second CommonGoalCard.
     */
    public void drawnCommonCards(CommonGoalCard card1, CommonGoalCard card2, List<Integer> commonToken1, List<Integer> commonToken2) {
        for (int i=0; i < game.getNumplayers(); i++) {
            String username = game.getPlayer(i).getNickname();
            if(!disconn_users.contains(username)) {
                try {
                    RemoteInterface client = users.get(username);
                    client.setNewCommon(card1.getID(), card2.getID(), commonToken1, commonToken2);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + username + "/n");
                }
            }
        }
    }


    /**
     * Sends to a certain player the PersonalGoalCard that he drew from the deck
     * @param username is the current player
     * @param card is the drawn card.
     */
    public void drawnPersonalCard(String username, PersonalGoalCard card) {
        if(!disconn_users.contains(username)) {
            try {
                RemoteInterface client = users.get(username);
                client.setNewPersonal(card);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + username + "/n");
            }
        }
    }


    /**
     * Sends to a certain client the maximum number of tiles that he can choose to take from the LivingRoom board
     * @param username is the current player
     * @param maxValueofTiles is the maximum number of tiles that are available for the given player.
     */
    public void startTurn(String username,int maxValueofTiles)  {
        if(!disconn_users.contains(username)) {
            try {
                RemoteInterface client = users.get(username);
                client.notifyTurnStart(maxValueofTiles);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + username + "/n");
            }
        }
    }


    /**
     * Notifies to a certain player that his turn just started.
     * @param user is the current player.
     */
    public void notifyTurnStart(User user) {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!receiver.equals(user) && !disconn_users.contains(receiver.getNickname())){
                try{
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setStageToNoTurn();
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                }
            }
        }
    }


    /**
     * Sends to a certain client
     * @param username ,
     * @param choosableTilesList the list of lists of tiles that the player may take from the LivingRoom.
     * @param num is the number of tiles that the player has to take from the board.
     */
    public void takeableTiles(String username, List<List<Tile>> choosableTilesList, int num) {
        if(!disconn_users.contains(username)) {
            try {
                RemoteInterface client = users.get(username);
                client.takeableTiles(choosableTilesList, num);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + username + "/n");
            }
        }
    }


    /**
     * Sends to a certain client
     * @param username (the client)
     * @param choosableColumns the list of columns that he can choose to put the tiles in.
     */
    public void possibleColumns(String username, boolean[] choosableColumns) {
        if(!disconn_users.contains(username)) {
            try {
                RemoteInterface client = users.get(username);
                client.askColumn(choosableColumns);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + username + "/n");
            }
        }
    }


    /**
     * Sends to the clients the newest Shelfie disposition of a
     * @param user player.
     */
    public void shelfieUpdate(User user) {
       for (int i=0; i < game.getNumplayers(); i++) {
           User  receiver = game.getPlayer(i);
           if(!disconn_users.contains(receiver.getNickname())) {
               try {
                   RemoteInterface client = users.get(receiver.getNickname());
                   client.setNewShelfie(user.getNickname(), user.getShelfie().getShelf());
               } catch (RemoteException e) {
                   System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
               }
           }
       }
    }


    /**
     * Sends to the clients the newest LivingRoom board disposition
     * @param matrix is the LivingRoom.
     */
    public void boardUpdate(Tile[][] matrix) {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!disconn_users.contains(receiver.getNickname())) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setNewBoard(matrix);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                }
            }
        }
    }


    /**
     * Sends to the clients the newly updated points
     * @param user is the player who now has a different amount of points
     * @param points is the player's current score.
     * @param commonToken1 is the number of points got from the first CommonGoalCard.
     * @param commonToken2 is the number of points got from the second CommonGoalCard.
     */
    public void pointsUpdate(User user, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!disconn_users.contains(receiver.getNickname())) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setNewPoints(user.getNickname(), points, commonToken1, commonToken2);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                }
            }
        }
    }


    /**
     * Notifies the clients about who took the EndToken
     * @param user is the player that won the token.
     */
    public void endTokenTaken(User user) {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!disconn_users.contains(receiver.getNickname())) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setEndToken(user.getNickname());
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                }
            }
        }
    }


    /**
     * Sends to the clients the final points list of the players
     * @param user is the list of the players in the game
     * @param points is the list of their points.
     */
    public void finalPoints(List<User> user, ArrayList<Integer> points) {
        ArrayList<String> usernames = new ArrayList<>();
        for(User u: user){
            usernames.add(u.getNickname());
        }
        for (int i=0; i < game.getNumplayers(); i++) {
            User receiver = game.getPlayer(i);
            if(!disconn_users.contains(receiver.getNickname())) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setFinalPoints(usernames, points);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**
     * Forces the client's view to update.
     */
    public void viewUpdate() {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
        if(receiver.getInGame() && !disconn_users.contains(receiver.getNickname())){
            sendChatUpdate(receiver.getChatList(), receiver);
                try{
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.updateView();
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                }
            }
        }
    }


    /**
     * Sends various components to the client in order to restore the view as it was before the disconnection.
     * @param user is the player that reconnected
     * @param gameID is the GameID
     * @param matrix is the LivingRoom Board
     * @param shelfies is a List of the Players' Shelfies
     * @param card1 is the first CommonGoalCard
     * @param card2 is the second CommonGoalCard
     * @param personalGoalCard is the player's PersonaleGoalCard
     * @param points is the list of the players' points
     * @param playerList is the list of the players, ordered as their turns are.
     */
    public void resetAfterDisconnection(String user, int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, CommonGoalCard card1, CommonGoalCard card2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList)  {
        if(!disconn_users.contains(user)) {
            try {
                RemoteInterface client = users.get(user);
                client.recover(gameID, matrix, shelfies, card1.getID(), card2.getID(), personalGoalCard, points, playerList);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + user + "/n");
            }
        }
    }


    /**
     * Setter method
     * @param serverTCP is the ServerTCP instance of a TCP server
     */
    public void setServerTCP(ServerTCP serverTCP) {
        this.serverTCP = serverTCP;
    }


    /**
     * Setter method
     * @param serverRMI is the RMIImplementation instance of an RMI server
     */
    public void setServerRMI(RMIImplementation serverRMI) {
        this.serverRMI = serverRMI;
    }


    /**
     * Notifies the clients that there are new Chat messages.
     * @param currentChat is the current list of the chat messages
     * @param receiver is the player that needs to receive the chat update.
     */
    public void sendChatUpdate(List<String> currentChat, User receiver) {
        if(receiver.getInGame() && !disconn_users.contains(receiver.getNickname())){
            try{
                RemoteInterface client = users.get(receiver.getNickname());
                client.updateChat(currentChat);
            }catch(RemoteException e){
                System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i=0; i < game.getNumplayers(); i++) {
            User u = game.getPlayer(i);
            if(u.getInGame() && !disconn_users.contains(u.getNickname())){
                try{
                    RemoteInterface client = users.get(u.getNickname());
                    client.updateChat(u.getChatList());
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + u.getNickname() +"/n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }


    /**
     * Notifies to the clients that the LivingRoom board was refilled.
     */
    public void boardRefill() {
        for (int i=0; i < game.getNumplayers(); i++) {
            User receiver = game.getPlayer(i);
            if (receiver.getInGame() && !disconn_users.contains(receiver.getNickname())) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.boardRefill();
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                }
            }
        }
    }


    /**
     * Notifies to the clients that the game ended due to the lack of players.
     */
    public void notifyEndGameDisconnection() {
        for (int i=0; i < game.getNumplayers(); i++) {
            User receiver = game.getPlayer(i);
            if (receiver.getInGame() && !disconn_users.contains(receiver.getNickname())) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.printError("You have won because this game was closed due to the lack of players! :(\n");
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                }
            }
        }
    }

    /**
     * Notifies to the clients that they can restart a new game.
     */
    public void restart(List<User> receivers){
        for (User user : receivers) {
            try {
                RemoteInterface client = users.get(user.getNickname());
                client.restart();
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + user.getNickname() + "/n");
            }
        }
    }
}
