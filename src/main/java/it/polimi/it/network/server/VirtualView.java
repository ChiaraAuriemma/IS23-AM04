package it.polimi.it.network.server;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.others.ThisNotTheDay;
import it.polimi.it.network.message.responses.*;
import it.polimi.it.network.server.Exceptions.NotTcpUserException;

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
    private ServerTCP serverTCP; //tolgo ???

    private HashMap<String, ObjectOutputStream> userTCP;
    private RMIImplementation serverRMI; //tolgo ??
    private HashMap<String, ClientInterface> userRMI;

    public VirtualView(){
        typeOfConnection = new HashMap<>();
        userTCP = new HashMap<>();
        userRMI = new HashMap<>();
    }

    public void setGame(Game game){
        this.game = game;
    }



    //
    /*public void setUser(User user, ClientInterface client){ ///////////////TOLGOOOOOOOOOOOOOOO
        try {
            Socket socket = serverTCP.getUserTCP(user);
            typeOfConnection.put(user, "TCP");
            try{
                userTCP.put(user, new ObjectOutputStream(socket.getOutputStream()));
            } catch (IOException e) {
            throw new RuntimeException(e); /// gestiscoooooooooooooooooooooo
            }
        } catch (NotTcpUserException e) {
            userRMI.put(user, client);
            typeOfConnection.put(user, "RMI");
        }
    }*/

    public void setUserTCP(String username,Socket socket){
        typeOfConnection.put(username, "TCP");
        try {
            userTCP.put(username, new ObjectOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e); ///gestiscoooooooooooooooooooooo
        }
    }

    public void setUserRMI(String username, ClientInterface client){
        typeOfConnection.put(username, "RMI");
        userRMI.put(username, client);
    }

    //metodi di inizio partita
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

               ClientInterface clientRMI = userRMI.get(username);
               clientRMI.setStartOrder(orderNames);
            }

        }
    }

    //mando la matrice iniziale e la lista dei token
    public void initialMatrix(Tile[][] matrix) throws RemoteException {//da mandare a tutti
        for (int i=0; i < game.getNumplayers(); i++) {
            String username = game.getPlayer(i).getNickname();

            if (typeOfConnection.get(username).equals("TCP")) {

                InitialMatrixMessage initialMatrixMessage = new InitialMatrixMessage(matrix);
                Message message = new Message(MessageType.INITIALMATRIX, initialMatrixMessage);
                sendTCPMessage(userTCP.get(username), message);

            } else if (typeOfConnection.get(game.getPlayer(i).getNickname()).equals("RMI")) {
                //sviluppo in RMI

                ClientInterface clientRMI = userRMI.get(username);
                clientRMI.setNewBoard(matrix);
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
                //sviluppo in RMI

                ClientInterface clientRMI = userRMI.get(username);
                clientRMI.setNewCommon(card1,card2);
            }
        }
    }

    public void drawnPersonalCard(String username, PersonalGoalCard card) throws RemoteException {

        if (typeOfConnection.get(username).equals("TCP")) {

            DrawnPersonalCardMessage drawnPersonalCardMessage = new DrawnPersonalCardMessage(card);
            Message message = new Message(MessageType.DRAWNPERSONALCARD,drawnPersonalCardMessage);
            sendTCPMessage(userTCP.get(username), message);

        } else if (typeOfConnection.get(username).equals("RMI")) {
            //sviluppo in RMI

            ClientInterface clientRMI = userRMI.get(username);
            clientRMI.setNewPersonal(card);
        }
    }



    //during the turn
    //starting turn
    public void startTurn(String username,int maxValueofTiles) throws RemoteException {


        if (typeOfConnection.get(username).equals("TCP")) {

            StartTurnMessage startTurnMessage = new StartTurnMessage(maxValueofTiles);
            Message message = new Message(MessageType.STARTTURN,startTurnMessage);
            sendTCPMessage(userTCP.get(username), message);

        } else if (typeOfConnection.get(username).equals("RMI")) {
            //sviluppo in RMI

            ClientInterface clientRMI = userRMI.get(username);
            clientRMI.notifyTurnStart(maxValueofTiles);
        }

        //starto il turno mandando il massimo numero di tile da server a client
        //lui risponde col numero che vuole -> Arriva a gameController
    }



    //chiamato da user, serve a mandare le takeable tiles fino alla view
    // chi prende la risposta?.......
    public void takeableTiles(String username, List<List<Tile>> choosableTilesList, int num) throws RemoteException {
        if(typeOfConnection.get(username).equals("TCP")){

                TakeableTilesResponse takeableTilesResponse = new TakeableTilesResponse(choosableTilesList);
                Message response = new Message(MessageType.TAKEABLETILES , takeableTilesResponse);
                sendTCPMessage(userTCP.get(username), response);


        }else if(typeOfConnection.get(username).equals("RMI")){

            ClientInterface clientRMI = userRMI.get(username);
            clientRMI.takeableTiles(choosableTilesList, num);
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

            ClientInterface clientRMI = userRMI.get(username);
            clientRMI.askColumn(choosableColumns);

        }
    }

    public void shelfieUpdate(User user) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {
                ShelfieUpdateMessage shelfieUpdateMessage = new ShelfieUpdateMessage(user.getNickname(), user.getShelfie().getShelf());
                Message message = new Message(MessageType.SHELFIEUPDATE, shelfieUpdateMessage);
                sendTCPMessage(userTCP.get(receiver.getNickname()), message);
                // perchè non aggiorno la shelfie direttamente qui??

            } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                //sviluppo in RMI
                ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                clientRMI.setNewShelfie(user.getNickname(), user.getShelfie().getShelf());
            }
        }
    }

    public void boardUpdate(Tile[][] matrix) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {

                BoardUpdateMessage boardUpdateMessage = new BoardUpdateMessage(matrix);
                Message message = new Message(MessageType.BOARDUPDATE, boardUpdateMessage);
                sendTCPMessage(userTCP.get(receiver.getNickname()), message);

            } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                //sviluppo in RMI

                ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                clientRMI.setNewBoard(matrix);
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

                ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                clientRMI.setNewPoints(user.getNickname(),points);
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

                ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                clientRMI.setEndToken(user.getNickname());
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
                ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                clientRMI.setFinalPoints(usernames, points);
            }
        }
    }

    public void viewUpdate() throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver.getNickname()).equals("TCP")) {
                ViewUpdate viewUpdate = new ViewUpdate();
                Message message = new Message(MessageType.UPDATEVIEW,viewUpdate);
                sendTCPMessage(userTCP.get(receiver.getNickname()), message);

            } else if (typeOfConnection.get(receiver.getNickname()).equals("RMI")) {
                //sviluppo in RMI
                ClientInterface clientRMI = userRMI.get(receiver.getNickname());
                ///////////////////////////////////////////////////////metodo su clientRMI e client Interface da  chiamare
                clientRMI.updateView();
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
}
