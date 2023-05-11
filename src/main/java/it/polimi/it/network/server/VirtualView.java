package it.polimi.it.network.server;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.ClientRMIApp;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.others.ThisNotTheDay;
import it.polimi.it.network.message.responses.*;
import it.polimi.it.network.server.Exceptions.NotTcpUserException;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class VirtualView {

    private Game game;

    private HashMap<User,String > typeOfConnection;
    private ServerTCP serverTCP;

    private HashMap<User, ObjectOutputStream> userTCP;
    private RMIImplementation serverRMI;
    private HashMap<User, ClientInterface> userRMI;

    private ClientInterface client;

    public VirtualView( ServerTCP serverTCP ,RMIImplementation serverRMI){
        typeOfConnection = new HashMap<>();
        userTCP = new HashMap<>();
        userRMI = new HashMap<>();
        this.serverTCP = serverTCP;
        this.serverRMI = serverRMI;
    }

    public void setGame(Game game){
        this.game = game;
    }



    //
    public void setUser(User user, ClientInterface client){
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
    }

    //metodi di inizio partita
    public void startOrder(ArrayList<User> order) throws RemoteException {//->da mandare a tutti
        for (int i=0; i < game.getNumplayers(); i++){
            User user = game.getPlayer(i);

            if(typeOfConnection.get(user).equals("TCP")){

                StartOrderMessage startOrderMessage = new StartOrderMessage(order);
                Message message = new Message(MessageType.STARTORDERPLAYER, startOrderMessage);
                sendTCPMessage(userTCP.get(user), message);

            }else if(typeOfConnection.get(user).equals("RMI")){

                //////////////////////

              // this.client = userRMI.get(user);
              // this.client.setStartOrder(order);

               ClientInterface clientRMI = userRMI.get(user);
               clientRMI.setStartOrder(order);
            }

        }
    }

    //mando la matrice iniziale e la lista dei token
    public void initialMatrix(Tile[][] matrix) throws RemoteException {//da mandare a tutti
        for (int i=0; i < game.getNumplayers(); i++) {
            User user = game.getPlayer(i);

            if (typeOfConnection.get(user).equals("TCP")) {

                InitialMatrixMessage initialMatrixMessage = new InitialMatrixMessage(matrix);
                Message message = new Message(MessageType.INITIALMATRIX, initialMatrixMessage);
                sendTCPMessage(userTCP.get(user), message);

            } else if (typeOfConnection.get(game.getPlayer(i)).equals("RMI")) {
                //sviluppo in RMI

                this.client = userRMI.get(user);
                this.client.setNewBoard(matrix);
            }
        }
    }

    public void drawnCommonCards(CommonGoalCard card1, CommonGoalCard card2, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User user = game.getPlayer(i);

            if (typeOfConnection.get(user).equals("TCP")) {

                DrawnCommonCardsMessage drawnCommonCardsMessage = new DrawnCommonCardsMessage(card1,card2,commonToken1,commonToken2);
                Message message = new Message(MessageType.DRAWNCOMMONCARDS, drawnCommonCardsMessage);
                sendTCPMessage(userTCP.get(user), message);

            } else if (typeOfConnection.get(user).equals("RMI")) {
                //sviluppo in RMI

               this.client = userRMI.get(user);
               this.client.setNewCommon(card1, card2);
            }
        }
    }

    public void drawnPersonalCard(User user, PersonalGoalCard card) throws RemoteException {

        if (typeOfConnection.get(user).equals("TCP")) {

            DrawnPersonalCardMessage drawnPersonalCardMessage = new DrawnPersonalCardMessage(card);
            Message message = new Message(MessageType.DRAWNPERSONALCARD,drawnPersonalCardMessage);
            sendTCPMessage(userTCP.get(user), message);

        } else if (typeOfConnection.get(user).equals("RMI")) {
            //sviluppo in RMI

            this.client = userRMI.get(user);
            this.client.setNewPersonal(card);
        }
    }



    //during the turn
    //starting turn
    public void startTurn(User user,int maxValueofTiles) throws RemoteException {


        if (typeOfConnection.get(user).equals("TCP")) {

            StartTurnMessage startTurnMessage = new StartTurnMessage(maxValueofTiles);
            Message message = new Message(MessageType.STARTTURN,startTurnMessage);
            sendTCPMessage(userTCP.get(user), message);

        } else if (typeOfConnection.get(user).equals("RMI")) {
            //sviluppo in RMI
            this.client = userRMI.get(user);
            this.client.notifyTurnStart(maxValueofTiles);
        }

        //starto il turno mandando il massimo numero di tile da server a client
        //lui risponde col numero che vuole -> Arriva a gameController
    }



    //chiamato da user, serve a mandare le takeable tiles fino alla view
    // chi prende la risposta?.......
    public void takeableTiles(User user, List<List<Tile>> choosableTilesList, int num) throws RemoteException {
        if(typeOfConnection.get(user).equals("TCP")){

                TakeableTilesResponse takeableTilesResponse = new TakeableTilesResponse(choosableTilesList);
                Message response = new Message(MessageType.TAKEABLETILES , takeableTilesResponse);
                sendTCPMessage(userTCP.get(user), response);


        }else if(typeOfConnection.get(user).equals("RMI")){
            this.client = userRMI.get(user);
            this.client.takeableTiles(choosableTilesList, num);
        }
    }



    //ricevo lista di colonne possibili, le comunico al client
    //client sceglierà una di queste colonne e la manda a RMIImplementation
    public void possibleColumns(User user, boolean[] choosableColumns) throws RemoteException {

        if(typeOfConnection.get(user).equals("TCP")){

            PossibleColumnsResponse possibleColumnsResponse = new PossibleColumnsResponse(choosableColumns);
            Message response = new Message(MessageType.POSSIBLECOLUMNS , possibleColumnsResponse);
            sendTCPMessage(userTCP.get(user), response);


        }else if(typeOfConnection.get(user).equals("RMI")){
            //sviluppo in RMI

            this.client = userRMI.get(user);
            this.client.askColumn(choosableColumns);

        }
    }

    public void shelfieUpdate(User user) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver).equals("TCP")) {

                ShelfieUpdateMessage shelfieUpdateMessage = new ShelfieUpdateMessage(user, user.getShelfie().getShelf());
                Message message = new Message(MessageType.SHELFIEUPDATE, shelfieUpdateMessage);
                sendTCPMessage(userTCP.get(receiver), message);
                // perchè non aggiorno la shelfie direttamente qui??

            } else if (typeOfConnection.get(receiver).equals("RMI")) {
                //sviluppo in RMI

                this.client= userRMI.get(user);
                this.client.setNewShelfie(receiver,receiver.getShelfie().getShelf());
            }
        }
    }

    public void boardUpdate(Tile[][] matrix) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver).equals("TCP")) {

                BoardUpdateMessage boardUpdateMessage = new BoardUpdateMessage(matrix);
                Message message = new Message(MessageType.BOARDUPDATE, boardUpdateMessage);
                sendTCPMessage(userTCP.get(receiver), message);

            } else if (typeOfConnection.get(receiver).equals("RMI")) {
                //sviluppo in RMI
                this.client = userRMI.get(receiver);
                this.client.setNewBoard(matrix);
            }
        }
    }

    public void pointsUpdate(User user, Integer points, List<Integer> commonToken1, List<Integer> commonToken2) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver).equals("TCP")) {

                PointsUpdateMessage pointsUpdateMessage = new PointsUpdateMessage(user,points,commonToken1,commonToken2);
                Message message = new Message(MessageType.POINTSUPDATE, pointsUpdateMessage);
                sendTCPMessage(userTCP.get(receiver), message);

            } else if (typeOfConnection.get(receiver).equals("RMI")) {
                //sviluppo in RMI

                this.client = userRMI.get(user);
                this.client.setNewPoints(user,points);
            }
        }
    }

    public void endTokenTaken(User user) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver).equals("TCP")) {

                EndTokenTakenMessage endTokenTakenMessage = new EndTokenTakenMessage(user);
                Message message = new Message(MessageType.ENDTOKEN, endTokenTakenMessage);
                sendTCPMessage(userTCP.get(receiver), message);

            } else if (typeOfConnection.get(receiver).equals("RMI")) {
                //sviluppo in RMI

                this.client = userRMI.get(user);
                client.setEndToken(user);
            }
        }
    }


    public void finalPoints(List<User> users, ArrayList<Integer> points) throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver).equals("TCP")) {

                FinalPointsMessage finalPointsMessage = new FinalPointsMessage(users,points);
                Message message = new Message(MessageType.FINALPOINTS, finalPointsMessage);
                sendTCPMessage(userTCP.get(receiver), message);

            } else if (typeOfConnection.get(receiver).equals("RMI")) {
                //sviluppo in RMI
                ClientInterface clientRMI = userRMI.get(users);
                clientRMI.setFinalPoints(users, points);
            }
        }
    }

    public void viewUpdate() throws RemoteException {
        for (int i=0; i < game.getNumplayers(); i++) {
            User  receiver = game.getPlayer(i);

            if (typeOfConnection.get(receiver).equals("TCP")) {
                ViewUpdate viewUpdate = new ViewUpdate();
                Message message = new Message(MessageType.UPDATEVIEW,viewUpdate);
                sendTCPMessage(userTCP.get(receiver), message);

            } else if (typeOfConnection.get(receiver).equals("RMI")) {
                //sviluppo in RMI
                ClientInterface clientRMI = userRMI.get(receiver);
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
}
