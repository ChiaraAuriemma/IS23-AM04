package it.polimi.it.network.server;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientRMI;
import it.polimi.it.network.client.ClientRMIApp;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.responses.*;
import it.polimi.it.network.server.Exceptions.NotTcpUserException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.polimi.it.network.message.MessageType.TAKEABLETILES;

public class VirtualView {

    private Game game;

    private HashMap<User,String > typeOfConnection;
    private ServerTCP serverTCP;

    private HashMap<User, ObjectOutputStream> userTCP;
    private RMIImplementation serverRMI;

    private HashMap<User, ClientRMI> userRMI;

    public VirtualView( ServerTCP serverTCP ,RMIImplementation serverRMI){
        typeOfConnection = new HashMap<>();
        this.serverTCP = serverTCP;
        this.serverRMI = serverRMI;
    }

    public void setGame(Game game){
        this.game = game;
    }
    public void setUser(User user){
        try {
            Socket socket = serverTCP.getUserTCP(user);
            typeOfConnection.put(user, "TCP");
            try{
                userTCP.put(user, new ObjectOutputStream(socket.getOutputStream()));
            } catch (IOException e) {
            throw new RuntimeException(e); /// gestiscoooooooooooooooooooooo
            }
        } catch (NotTcpUserException e) {
            userRMI.put(user, serverRMI.getUserRMI(user));
            typeOfConnection.put(user, "RMI");
        }
    }

    //metodi di inizio partita
    public void startOrder(ArrayList<User> order){
        for (int i=0; i < game.getNumplayers(); i++){
            User user = game.getPlayer(i);

            if(typeOfConnection.get(user).equals("TCP")){

                StartOrderMessage startOrderMessage = new StartOrderMessage(order);
                Message message = new Message(MessageType.STARTORDERPLAYER, startOrderMessage);
                try {
                    userTCP.get(user).writeObject(message);
                    userTCP.get(user).flush();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }else if(typeOfConnection.get(game.getPlayer(i)).equals("RMI")){
                //sviluppo in RMI
            }

        }
    }

    //mando la matrice iniziale e la lista dei token
    public void initialMatrix(Tile[][] matrix){
        for (int i=0; i < game.getNumplayers(); i++) {
            User user = game.getPlayer(i);

            if (typeOfConnection.get(user).equals("TCP")) {

                InitialMatrixMessage initialMatrixMessage = new InitialMatrixMessage(matrix);
                Message message = new Message(MessageType.INITIALMATRIX, initialMatrixMessage);
                try {
                    userTCP.get(user).writeObject(message);
                    userTCP.get(user).flush();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            } else if (typeOfConnection.get(game.getPlayer(i)).equals("RMI")) {
                //sviluppo in RMI
            }
        }
    }

    public void drawnCommonCards(CommonGoalCard card1, CommonGoalCard card2, List<Integer> commonToken1, List<Integer> commonToken2){
        for (int i=0; i < game.getNumplayers(); i++) {
            User user = game.getPlayer(i);

            if (typeOfConnection.get(user).equals("TCP")) {

                DrawnCommonCardsMessage drawnCommonCardsMessage = new DrawnCommonCardsMessage(card1,card2,commonToken1,commonToken2);
                Message message = new Message(MessageType.DRAWNCOMMONCARDS, drawnCommonCardsMessage);
                try {
                    userTCP.get(user).writeObject(message);
                    userTCP.get(user).flush();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            } else if (typeOfConnection.get(game.getPlayer(i)).equals("RMI")) {
                //sviluppo in RMI
            }
        }
    }

    public void drawnPersonalCard(User user, PersonalGoalCard card){

        if (typeOfConnection.get(user).equals("TCP")) {

            DrawnPersonalCardMessage drawnPersonalCardMessage = new DrawnPersonalCardMessage(card);
            Message message = new Message(MessageType.DRAWNPERSONALCARD,drawnPersonalCardMessage);
            try {
                userTCP.get(user).writeObject(message);
                userTCP.get(user).flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } else if (typeOfConnection.get(user).equals("RMI")) {
            //sviluppo in RMI
        }
    }

    //sistemo questo metodo : sistemoooo rmi!!!!!!!!!!!!!!!!!!!
    public void takeableTiles(User user, List<List<Tile>> choosableTilesList){
        if(typeOfConnection.get(user).equals("TCP")){

                TakeableTilesResponse takeableTilesResponse = new TakeableTilesResponse(choosableTilesList);
                Message response = new Message(MessageType.TAKEABLETILES , takeableTilesResponse);
                try {
                    userTCP.get(user).writeObject(response);
                    userTCP.get(user).flush();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }


        }else if(typeOfConnection.get(user).equals("RMI")){
            //sistemooooooooooooo
            ClientRMI clientRMI = serverRMI.getUserRMI(user);
            clientRMI.takeableTiles(List<List<Tile>> choosableTilesList);
        }
    }





}
