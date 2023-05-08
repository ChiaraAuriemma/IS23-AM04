package it.polimi.it.network.client;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.message.ErrorMessage;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.request.*;
import it.polimi.it.network.message.responses.*;
import it.polimi.it.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ClientTCP implements ClientInterface {

    private int port;
    private String ip;
    private View view;


    private User user;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket serverSocket;
    public ClientTCP(int port, String ip){
        this.port = port;
        this.ip = ip;
        this.view=new View();
    }

    public View getView(){
        return view;
    }


    public void startClient(){
        try{
            serverSocket = new Socket(ip,port);

            try {
                in = new ObjectInputStream(serverSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                out = new ObjectOutputStream(serverSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // qui metto un thread che parte su run()
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        }
    }


    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException {

    }

    @Override
    public void setStartOrder(ArrayList<User> order) {

    }

    @Override
    public void setNewBoard(Tile[][] matrix) {

    }

    @Override
    public void setNewCommon(CommonGoalCard card1, CommonGoalCard card2) {

    }

    @Override
    public void setNewPersonal(PersonalGoalCard card) {

    }

    @Override
    public void setNewShelfie(User receiver, Shelfie shelfie) {

    }

    @Override
    public void setNewPoints(User user, Integer points) {

    }

    @Override
    public void notifyTurnStart(int maxValueofTiles) {

    }

    @Override
    public void askColumn(boolean[] choosableColumns) {

    }


    //IMPORTANTE : gestisco anche l'arrivo di messaggi d'errore da parte del server
    public void run(){
        Message response;
        MessageType messType;

        while(true){
            try {
                response = (Message) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            messType = response.getType();

            switch (messType){
                case CREATEPLAYERRESPONSE:
                    LoginResponse loginResponse = (LoginResponse) response.getPayload();
                    user = loginResponse.getUser();
                    //view : faccio passare la view alla schermata di selezione join o create game
                    //view : passo alla view lo user

                case CREATEGAMERESPONSE:
                    CreateGameResponse createGameResponse = (CreateGameResponse) response.getPayload();
                    //view : passo alla view il game id

                case STARTORDERPLAYER:
                    StartOrderMessage startOrderMessage = (StartOrderMessage) response.getPayload();
                    //view : passo l'ordine dei giocatori in modo che può disporli

                case INITIALMATRIX:
                    InitialMatrixMessage initialMatrixMessage = (InitialMatrixMessage) response.getPayload();
                    //view : passo la matrice in modo da visualizzare la board

                case DRAWNCOMMONCARDS:
                    DrawnCommonCardsMessage drawnCommonCardsMessage = (DrawnCommonCardsMessage) response.getPayload();
                    //view : passo le common cards e la lista dei token delle common cards

                case DRAWNPERSONALCARD:
                    DrawnPersonalCardMessage drawnPersonalCardMessage = (DrawnPersonalCardMessage) response.getPayload();
                    //view : passo la personal card del giocatore

                case STARTTURN:
                    StartTurnMessage startTurnMessage = (StartTurnMessage) response.getPayload();
                    //view : passo il maxNumOfTiles sceglibili

                case TAKEABLETILES:
                    TakeableTilesResponse takeableTilesResponse = (TakeableTilesResponse) response.getPayload();
                    //view : passo choosableTilesList per "illuminare" sulla board le tiles prendibili

                case POSSIBLECOLUMNS:
                    PossibleColumnsResponse possibleColumnsResponse = (PossibleColumnsResponse) response.getPayload();
                    //view : passo il booleano con true e false sulle varie colonne della shelfie

                case SHELFIEUPDATE:
                    ShelfieUpdateMessage shelfieUpdateMessage = (ShelfieUpdateMessage) response.getPayload();
                    //view : passo lo user,la colonna e la lista ordinata di tiles scelte per aggiornare la shelfie dello user corrispondente

                case BOARDUPDATE:
                    BoardUpdateMessage boardUpdateMessage = (BoardUpdateMessage) response.getPayload();
                    //view : passo la nuova matrice in modo da visualizzare la board aggiornata

                case POINTSUPDATE:
                    PointsUpdateMessage pointsUpdateMessage = (PointsUpdateMessage) response.getPayload();
                    //view : passo lo user,il nuovo punteggio e se questo ha preso qualche common token

                case ENDTOKEN:
                    EndTokenTakenMessage endTokenTakenMessage = (EndTokenTakenMessage) response.getPayload();
                    //view : passo lo user che ha preso l'endToken

                case FINALPOINTS:
                    FinalPointsMessage finalPointsMessage = (FinalPointsMessage) response.getPayload();
                    //view : passo la lista degli utenti e la lista dei loro punteggi

                case ERROR:
                    ErrorMessage errorMessage = (ErrorMessage) response.getPayload();
                    // il messaggio d'errore contiene la stringa error implementare la gestione dei vari errori
            }
        }
    }



    //metodi da chiamare per mandare una request
    @Override
    public void login(String nickname) {
        LoginRequest loginRequest = new LoginRequest(nickname);
        Message request = new Message(MessageType.CREATEPLAYER, loginRequest);
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
        public void createGame(int playerNumber){
        CreateGameRequest createGameRequest = new CreateGameRequest(user,playerNumber);
        Message request = new Message(MessageType.CREATEGAME, createGameRequest);
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void joinGame(int gameId){
        JoinGameRequest joinGameRequest = new JoinGameRequest(user,gameId);
        Message request = new Message(MessageType.JOINGAME, joinGameRequest);
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void tilesNumMessage(int numOfTiles){
        TilesNumRequest tilesNumRequest = new TilesNumRequest(numOfTiles);
        Message request = new Message(MessageType.TILESNUMMESSAGE, tilesNumRequest);
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
@Override
    public void selectedTiles(List<Tile> choices){
        SelectedTilesRequest selectedTilesRequest = new SelectedTilesRequest(choices);
        Message request = new Message(MessageType.SELECTEDTILES, selectedTilesRequest);
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
@Override
    public void chooseColumn (int column){
        ChooseColumnRequest chooseColumnRequest = new ChooseColumnRequest(column);
        Message request = new Message(MessageType.CHOOSECOLUMN, chooseColumnRequest);
        try {
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
