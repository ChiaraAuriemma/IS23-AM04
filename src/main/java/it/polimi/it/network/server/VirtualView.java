package it.polimi.it.network.server;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.responses.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VirtualView implements Serializable {

    private static final long serialVersionUID = -3906432862304671330L;
    private Game game;

    private HashMap<String,String > typeOfConnection;
    private ServerTCP serverTCP; //tolgo ???  NO

    private HashMap<String, ObjectOutputStream> userTCP;
    private RMIImplementation serverRMI; //tolgo ??
                                            // NO, MI SERVE. Fra
    private HashMap<String, ClientInterface> userRMI;

    public VirtualView(){
        typeOfConnection = new HashMap<>();
        userTCP = new HashMap<>();
        userRMI = new HashMap<>();
    }

    public void setGame(Game game){
        this.game = game;
    }


    public void setUserTCP(String username,ObjectOutputStream out){
        typeOfConnection.put(username, "TCP");
        userTCP.put(username, out);
    }

    public void setUserRMI(String username, ClientInterface client){
        typeOfConnection.put(username, "RMI");
        userRMI.put(username, client);
    }


    public void startOrder(ArrayList<User> order) throws RemoteException {//->da mandare a tutti
        ArrayList<String> orderNames = new ArrayList<>();
        for(User u: order){
            orderNames.add(u.getNickname());
        }

        for (int i=0; i < game.getNumplayers(); i++){
            String username = game.getPlayer(i).getNickname();

            if(typeOfConnection.get(username).equals("TCP")){

                StartOrderMessage startOrderMessage = new StartOrderMessage(orderNames);
                Message message = new Message(MessageType.STARTORDERPLAYER, startOrderMessage);
                sendTCPMessage(userTCP.get(username), message);

            }else if(typeOfConnection.get(username).equals("RMI")){
               try{
                   ClientInterface clientRMI = userRMI.get(username);
                   clientRMI.setStartOrder(orderNames);
               }catch(RemoteException e){
                   System.out.println(e.getMessage() + " user: " + username +"/n");
                   disconnect_user(username);
               }
            }
        }
    }

    private void disconnect_user(String username) {
        serverRMI.disconnect_user(username);
    }


    public void initialMatrix(Tile[][] matrix) throws RemoteException {//da mandare a tutti
        for (int i=0; i < game.getNumplayers(); i++) {
            String username = game.getPlayer(i).getNickname();

            if (typeOfConnection.get(username).equals("TCP")) {

                InitialMatrixMessage initialMatrixMessage = new InitialMatrixMessage(matrix);
                Message message = new Message(MessageType.INITIALMATRIX, initialMatrixMessage);
                sendTCPMessage(userTCP.get(username), message);

            } else if (typeOfConnection.get(game.getPlayer(i).getNickname()).equals("RMI")) {

                try{
                ClientInterface clientRMI = userRMI.get(username);
                clientRMI.setNewBoard(matrix);
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + username +"/n");
                    disconnect_user(username);
                }
            }
        }
    }


    public void drawnCommonCards(CommonGoalCard card1, CommonGoalCard card2, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            String username = game.getPlayer(i).getNickname();

            if (typeOfConnection.get(username).equals("TCP")) {

                DrawnCommonCardsMessage drawnCommonCardsMessage = new DrawnCommonCardsMessage(card1,card2,commonToken1,commonToken2);
                Message message = new Message(MessageType.DRAWNCOMMONCARDS, drawnCommonCardsMessage);
                sendTCPMessage(userTCP.get(username), message);

            } else if (typeOfConnection.get(username).equals("RMI")) {

                try{
                ClientInterface clientRMI = userRMI.get(username);
                clientRMI.setNewCommon(card1,card2);
            }catch(RemoteException e){
                System.out.println(e.getMessage() + " user: " + username +"/n");
                disconnect_user(username);
            }
            }
        }
    }


    public void drawnPersonalCard(String username, PersonalGoalCard card) throws RemoteException {

        if (typeOfConnection.get(username).equals("TCP")) {

            PersonalGoalCard personalCard = card;
            DrawnPersonalCardMessage drawnPersonalCardMessage = new DrawnPersonalCardMessage(personalCard);
            Message message = new Message(MessageType.DRAWNPERSONALCARD,drawnPersonalCardMessage);
            sendTCPMessage(userTCP.get(username), message);

        } else if (typeOfConnection.get(username).equals("RMI")) {

            try{
                ClientInterface clientRMI = userRMI.get(username);
                clientRMI.setNewPersonal(card);
            }catch(RemoteException e){
                System.out.println(e.getMessage() + " user: " + username +"/n");
                disconnect_user(username);
            }

        }
    }




    public void startTurn(String username,int maxValueofTiles) throws RemoteException {


        if (typeOfConnection.get(username).equals("TCP")) {

            StartTurnMessage startTurnMessage = new StartTurnMessage(maxValueofTiles);
            Message message = new Message(MessageType.STARTTURN,startTurnMessage);
            sendTCPMessage(userTCP.get(username), message);

        } else if (typeOfConnection.get(username).equals("RMI")) {

            try{
                ClientInterface clientRMI = userRMI.get(username);
                clientRMI.notifyTurnStart(maxValueofTiles);
            }catch(RemoteException e){
                System.out.println(e.getMessage() + " user: " + username +"/n");
                disconnect_user(username);
            }

        }
    }

    public void notifyTurnStart(User user) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(!receiver.equals(user)){
                if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {

                    NoTurnSetter noTurnSetter = new NoTurnSetter(true);
                    Message message = new Message(MessageType.NOTURNSETTER,noTurnSetter);
                    sendTCPMessage(userTCP.get(receiver.getNickname()), message);

                } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                    //sviluppo in RMI

                    try{
                        ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                        clientRMI.setStageToNoTurn();
                    }catch(RemoteException e){
                        System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                        disconnect_user(receiver.getNickname());
                    }
                }
            }
        }
    }


    public void takeableTiles(String username, List<List<Tile>> choosableTilesList, int num) throws RemoteException {
        if(typeOfConnection.get(username).equals("TCP")){

                TakeableTilesResponse takeableTilesResponse = new TakeableTilesResponse(choosableTilesList);
                Message response = new Message(MessageType.TAKEABLETILES , takeableTilesResponse);
                sendTCPMessage(userTCP.get(username), response);


        }else if(typeOfConnection.get(username).equals("RMI")){

            try{
                ClientInterface clientRMI = userRMI.get(username);
                clientRMI.takeableTiles(choosableTilesList, num);
            }catch(RemoteException e){
                System.out.println(e.getMessage() + " user: " + username +"/n");
                disconnect_user(username);
            }

        }
    }



    //ricevo lista di colonne possibili, le comunico al client
    //client sceglierà una di queste colonne e la manda a RMIImplementation
    public void possibleColumns(String username, boolean[] choosableColumns) throws RemoteException {

        if(typeOfConnection.get(username).equals("TCP")){

            PossibleColumnsResponse possibleColumnsResponse = new PossibleColumnsResponse(choosableColumns);
            Message response = new Message(MessageType.POSSIBLECOLUMNS , possibleColumnsResponse);
            sendTCPMessage(userTCP.get(username), response);


        }else if(typeOfConnection.get(username).equals("RMI")){
            //sviluppo in RMI

            try{
                ClientInterface clientRMI = userRMI.get(username);
                clientRMI.askColumn(choosableColumns);
            }catch(RemoteException e){
                System.out.println(e.getMessage() + " user: " + username +"/n");
                disconnect_user(username);
            }


        }
    }

    public void shelfieUpdate(User user) throws RemoteException {
       for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {

                Tile[][] shelf = user.getShelfie().getShelf();

                Tile[][] s = new Tile[6][5];
                for(int k = 0; k < 6; k++){
                    for(int j = 0; j < 5; j++){
                        s[k][j] = new Tile(k,j, PossibleColors.valueOf(shelf[k][j].getColor()));
                    }
                }

                ShelfieUpdateMessage shelfieUpdateMessage = new ShelfieUpdateMessage(user.getNickname(), s);
                Message message = new Message(MessageType.SHELFIEUPDATE, shelfieUpdateMessage);
                sendTCPMessage(userTCP.get(receiver.getNickname()), message);
                // perchè non aggiorno la shelfie direttamente qui??

            } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                //sviluppo in RMI
                try{
                    ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                    clientRMI.setNewShelfie(user.getNickname(), user.getShelfie().getShelf());
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                    disconnect_user(receiver.getNickname());
                }

            }
       }
    }

    public void boardUpdate(Tile[][] matrix) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {

                Tile[][] m = new Tile[9][9];
                for(int k = 0; k < 9; k++){
                    for(int j = 0; j < 9; j++){
                        m[k][j] = new Tile(k,j, PossibleColors.valueOf(matrix[k][j].getColor()));
                    }
                }

                BoardUpdateMessage boardUpdateMessage = new BoardUpdateMessage(m);
                Message message = new Message(MessageType.BOARDUPDATE, boardUpdateMessage);
                sendTCPMessage(userTCP.get(receiver.getNickname()), message);

            } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                //sviluppo in RMI
                try{
                    ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                    clientRMI.setNewBoard(matrix);
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                    disconnect_user(receiver.getNickname());
                }

            }
        }
    }

    public void pointsUpdate(User user, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {

                PointsUpdateMessage pointsUpdateMessage = new PointsUpdateMessage(user.getNickname(),points,commonToken1,commonToken2);
                Message message = new Message(MessageType.POINTSUPDATE, pointsUpdateMessage);
                sendTCPMessage(userTCP.get(receiver.getNickname()), message);

            } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                //sviluppo in RMI

                try{
                    ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                    clientRMI.setNewPoints(user.getNickname(),points);
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                    disconnect_user(receiver.getNickname());
                }

            }
        }
    }

    public void endTokenTaken(User user) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {

                EndTokenTakenMessage endTokenTakenMessage = new EndTokenTakenMessage(user.getNickname());
                Message message = new Message(MessageType.ENDTOKEN, endTokenTakenMessage);
                sendTCPMessage(userTCP.get(receiver.getNickname()), message);

            } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                //sviluppo in RMI

                try{
                    ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                    clientRMI.setEndToken(user.getNickname());
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                    disconnect_user(receiver.getNickname());
                }
            }
        }
    }


    public void finalPoints(List<User> user, ArrayList<Integer> points) throws RemoteException {
        ArrayList<String> usernames = new ArrayList<>();
        for(User u: user){
            usernames.add(u.getNickname());
        }

        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {

                FinalPointsMessage finalPointsMessage = new FinalPointsMessage(usernames,points);
                Message message = new Message(MessageType.FINALPOINTS, finalPointsMessage);
                sendTCPMessage(userTCP.get(receiver.getNickname()), message);

            } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                //sviluppo in RMI
                try{
                    ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                    clientRMI.setFinalPoints(usernames, points);
                }catch(RemoteException e){
                    System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                    disconnect_user(receiver.getNickname());
                }

            }
        }
    }

    public void viewUpdate(List<String> currentChat) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(receiver.getInGame()){



                if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {

                    sendChatUpdate(currentChat);
                    /*ChatUpdate chatUpdate = new ChatUpdate(currentChat);
                    Message messageChat = new Message(MessageType.CHATUPDATE, chatUpdate);
                    sendTCPMessage(userTCP.get(receiver.getNickname()), messageChat);*/

                    ViewUpdate viewUpdate = new ViewUpdate();
                    Message message = new Message(MessageType.UPDATEVIEW,viewUpdate);
                    sendTCPMessage(userTCP.get(receiver.getNickname()), message);

                } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                    //sviluppo in RMI
                    try{
                        ClientInterface clientRMI = userRMI.get(receiver.getNickname());

                    ///////////////////////////////////////////////////////metodo su clientRMI e client Interface da  chiamare
                        clientRMI.updateChat(currentChat);
                        clientRMI.updateView();
                    }catch(RemoteException e){
                        System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                        disconnect_user(receiver.getNickname());
                    }
                }
            }
        }
    }

    public void sendTCPMessage(ObjectOutputStream out, Message message){

        synchronized (out){
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }


    //quando scommento questo metodo mi ricordo di mettere lo username al posto di user!!!!!!!!!!!!!
    /*
    public void resetAfterDisconnection(User user, int gameID) throws RemoteException {

        if (typeOfConnection.get(user).equalsIgnoreCase("TCP")){
            ThisNotTheDay recover = new ThisNotTheDay(game,gameID);
            Message message = new Message(MessageType.THISISNOTTHEDAY, recover);
            sendTCPMessage(userTCP.get(user), message);

        }else if (typeOfConnection.get(user).equalsIgnoreCase("RMI")){
            ClientInterface clientRMI = userRMI.get(user);
            clientRMI.recover(game, gameID);

        }
    }


     */
    public void setServerTCP(ServerTCP serverTCP) {
        this.serverTCP = serverTCP;
    }

    public void setServerRMI(RMIImplementation serverRMI) {
        this.serverRMI = serverRMI;
    }


    public void sendChatUpdate(List<String> currentChat) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);
            if(receiver.getInGame()){
                if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {
                    ChatUpdate chatUpdate = new ChatUpdate(currentChat);
                    Message messageChat = new Message(MessageType.CHATUPDATE, chatUpdate);
                    sendTCPMessage(userTCP.get(receiver.getNickname()), messageChat);


                } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                    try{
                        ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                        clientRMI.updateChat(currentChat);
                    }catch(RemoteException e){
                        System.out.println(e.getMessage() + " user: " + receiver.getNickname() +"/n");
                        disconnect_user(receiver.getNickname());
                    }
                }
            }
        }
    }

    public void boardRefill() throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User receiver = game.getPlayer(i);
            if (receiver.getInGame()) {

                if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {
                    BoardRefill boardRefill = new BoardRefill();
                    Message message = new Message(MessageType.BOARDREFILL, boardRefill);
                    sendTCPMessage(userTCP.get(receiver.getNickname()), message);


                } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                    try {
                        ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                        clientRMI.boardRefill();
                    } catch (RemoteException e) {
                        System.out.println(e.getMessage() + " user: " + receiver.getNickname() + "/n");
                        disconnect_user(receiver.getNickname());
                    }
                }
            }
        }
    }
}
