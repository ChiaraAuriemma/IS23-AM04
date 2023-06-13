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

public class VirtualView implements Serializable {

    private static final long serialVersionUID = -3906432862304671330L;
    private Game game;

    private ServerTCP serverTCP; //tolgo ???  NO

    private RMIImplementation serverRMI; //tolgo ??
                                            // NO, MI SERVE. Fra
    private HashMap<String, RemoteInterface> users;
    private List<String> disconn_users ;

    public VirtualView(){
        users = new HashMap<>();
        disconn_users = new ArrayList<>();
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void setUser(String username, RemoteInterface remoteInterface){
        users.put(username, remoteInterface);
    }


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
                    disconnect_user(username);
                }
            }
        }
    }

    private void disconnect_user(String username) {
        if (!disconn_users.contains(username))
            serverRMI.disconnect_user(username);
    }

    public void insertDisconnection (String username){
        disconn_users.add(username);
    }

    public void removeDisconnection (String username){
        disconn_users.remove(username);
    }


    public void initialMatrix(Tile[][] matrix) {
        for (int i=0; i < game.getNumplayers(); i++) {
            String username = game.getPlayer(i).getNickname();

            if(!disconn_users.contains(username)){
                try{
                    RemoteInterface client = users.get(username);
                    client.setNewBoard(matrix);
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + username +"/n");
                    disconnect_user(username);
                }
            }
        }
    }


    public void drawnCommonCards(CommonGoalCard card1, CommonGoalCard card2, List<Integer> commonToken1, List<Integer> commonToken2) {
        for (int i=0; i < game.getNumplayers(); i++) {
            String username = game.getPlayer(i).getNickname();

            if(!disconn_users.contains(username)) {
                try {
                    RemoteInterface client = users.get(username);
                    client.setNewCommon(card1.getID(), card2.getID(), commonToken1, commonToken2);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + username + "/n");
                    disconnect_user(username);
                }
            }
        }
    }


    public void drawnPersonalCard(String username, PersonalGoalCard card) {
        if(!disconn_users.contains(username)) {
            try {
                RemoteInterface client = users.get(username);
                client.setNewPersonal(card);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + username + "/n");
                disconnect_user(username);
            }
        }
    }




    public void startTurn(String username,int maxValueofTiles)  {
        if(!disconn_users.contains(username)) {
            try {
                RemoteInterface client = users.get(username);
                client.notifyTurnStart(maxValueofTiles);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + username + "/n");
                disconnect_user(username);
            }
        }
    }

    public void notifyTurnStart(User user) {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!receiver.equals(user) && !disconn_users.contains(receiver)){

                try{
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setStageToNoTurn();
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                    disconnect_user(receiver.getNickname());
                }

            }
        }
    }


    public void takeableTiles(String username, List<List<Tile>> choosableTilesList, int num) {
        if(!disconn_users.contains(username)) {
            try {
                RemoteInterface client = users.get(username);
                client.takeableTiles(choosableTilesList, num);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + username + "/n");
                disconnect_user(username);
            }
        }
    }



    //ricevo lista di colonne possibili, le comunico al client
    //client sceglierà una di queste colonne e la manda a RMIImplementation
    public void possibleColumns(String username, boolean[] choosableColumns) {
        if(!disconn_users.contains(username)) {
            try {
                RemoteInterface client = users.get(username);
                client.askColumn(choosableColumns);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + username + "/n");
                disconnect_user(username);
            }
        }
    }

    public void shelfieUpdate(User user) {
       for (int i=0; i < game.getNumplayers(); i++) {
           User  receiver = game.getPlayer(i);
           if(!disconn_users.contains(receiver)) {
               try {
                   RemoteInterface client = users.get(receiver.getNickname());
                   client.setNewShelfie(user.getNickname(), user.getShelfie().getShelf());
               } catch (RemoteException e) {
                   System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                   disconnect_user(receiver.getNickname());
               }
           }
       }
    }

    public void boardUpdate(Tile[][] matrix) {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!disconn_users.contains(receiver)) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setNewBoard(matrix);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                    disconnect_user(receiver.getNickname());
                }
            }
        }
    }

    public void pointsUpdate(User user, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!disconn_users.contains(receiver)) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setNewPoints(user.getNickname(), points, commonToken1, commonToken2);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                    disconnect_user(receiver.getNickname());
                }
            }
        }
    }

    public void endTokenTaken(User user) {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!disconn_users.contains(receiver)) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setEndToken(user.getNickname());
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                    disconnect_user(receiver.getNickname());
                }
            }
        }
    }


    public void finalPoints(List<User> user, ArrayList<Integer> points) {
        ArrayList<String> usernames = new ArrayList<>();
        for(User u: user){
            usernames.add(u.getNickname());
        }

        for (int i=0; i < game.getNumplayers(); i++) {
            User receiver = game.getPlayer(i);
            if(!disconn_users.contains(receiver)) {
                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.setFinalPoints(usernames, points);
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                    disconnect_user(receiver.getNickname());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void viewUpdate() {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
        if(receiver.getInGame() && !disconn_users.contains(receiver)){

            sendChatUpdate(receiver.getChatList(), receiver);
                try{
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.updateView();
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                    disconnect_user(receiver.getNickname());
                }
            }
        }
    }

    public void resetAfterDisconnection(String user, int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, CommonGoalCard card1, CommonGoalCard card2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList)  {
        if(!disconn_users.contains(user)) {
            try {
                RemoteInterface client = users.get(user);
                client.recover(gameID, matrix, shelfies, card1.getID(), card2.getID(), personalGoalCard, points, playerList);
            } catch (RemoteException e) {
                System.out.println(e.getMessage() + " user: " + user + "/n");
                disconnect_user(user);
            }
        }
    }


    public void setServerTCP(ServerTCP serverTCP) {
        this.serverTCP = serverTCP;
    }

    public void setServerRMI(RMIImplementation serverRMI) {
        this.serverRMI = serverRMI;
    }


    public void sendChatUpdate(List<String> currentChat, User receiver) {

        if(receiver.getInGame() && !disconn_users.contains(receiver)){
            try{
                RemoteInterface client = users.get(receiver.getNickname());
                client.updateChat(currentChat);
            }catch(RemoteException e){
                System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                disconnect_user(receiver.getNickname());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (int i=0; i < game.getNumplayers(); i++) {
            User u = game.getPlayer(i);
            if(u.getInGame() && !disconn_users.contains(u)){

                try{
                    RemoteInterface client = users.get(u.getNickname());
                    client.updateChat(u.getChatList());
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + u.getNickname() +"/n");
                    disconnect_user(u.getNickname());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }



        }

    public void boardRefill() {
        for (int i=0; i < game.getNumplayers(); i++) {
            User receiver = game.getPlayer(i);
            if (receiver.getInGame() && !disconn_users.contains(receiver)) {

                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.boardRefill();
                } catch (RemoteException e) {
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                    disconnect_user(receiver.getNickname());
                }
            }
        }
    }

    public void notifyEndGameDisconnection() {
        for (int i=0; i < game.getNumplayers(); i++) {
            User receiver = game.getPlayer(i);
            if (receiver.getInGame() && !disconn_users.contains(receiver)) {

                try {
                    RemoteInterface client = users.get(receiver.getNickname());
                    client.printError("This game was closed due to the lack of players! :(\n");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
