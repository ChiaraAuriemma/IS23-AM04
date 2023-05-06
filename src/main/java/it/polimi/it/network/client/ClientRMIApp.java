package it.polimi.it.network.client;

import it.polimi.it.controller.Exceptions.*;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.server.ServerRMI;
import it.polimi.it.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientRMIApp extends UnicastRemoteObject implements ClientRMI {
    private int port;
    private String ip;
    private Registry registry;
    private ServerRMI sr;
    private boolean nickOK = false;

    private User user;
    private View view;
    private Scanner scanner;
    private List<Tile> lastChosen = new ArrayList<>();


    public ClientRMIApp(int port, String ip) throws RemoteException {
        this.port = port;
        this.ip = ip;
        this.view = new View();
    }

    public View getView(){
        return view;
    }

    /**
     * First method used by the client, communicates with the view in order to let him choose his nickname,
     * then communicates it to the serverRMI to create an actual User instance.
     */
    public void startClient()  {
        try {
            registry = LocateRegistry.getRegistry(ip,port);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        try {
            this.sr = (ServerRMI) registry.lookup("server_RMI");
        } catch (RemoteException | NotBoundException e) {
            System.out.println(e.getMessage());
        }
        //view : passo come parametro di start client un riferimento alla view che voglio usare

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String clientInput = " ";
        //view : do la possibilità all'utente di inserire un nickname
        view.askNickname();


            //view: mostro la scelta tra creategame e joingame
            // delego in base alla scelta che fa la view al metodo create o join
            //appena mi arriva startgame dal server avvio la partita

    }




    /**
     * Method called by startClient, sends the server the chosen username
      * @param userName .
     * @throws RemoteException .
     */
    public void login(String userName) throws RemoteException {
        try{
            user = sr.login(this,userName);
            //view : passo alla schermata con create e join game
        }catch (ExistingNicknameException e) {
            //view : notifico la view che deve riproporre l'inserimento del nickname con l'errore in rosso
            view.askNicknameAgain();
        }

    }

    /**
     * Communicates the server the number of people that the user wants in his new game
     * @param playerNumber .
     * @throws RemoteException .
     */
    public void createGame(int playerNumber) throws RemoteException {
        try{
            sr.createGame(user,playerNumber);
        } catch (WrongPlayerException e) {
            //view : notifico alla view che il numero di giocatori inseriti non è corretto
            view.askNumPlayerAgain();
            this.scanner = new Scanner(System.in);
            int response;
            try {
                response = scanner.nextInt();
                createGame(response);
            } catch (IOException f) {
                System.out.println(f.getMessage());
            }

        }
    }


    /**
     *Method that communicates to the server the ID of the game that the user wants to join
     * @param gameID .
     * @throws RemoteException .
     */
    public void joinGame(int gameID) throws RemoteException {
        try{
            sr.joinGame(user,gameID);
        }catch (InvalidIDException | WrongPlayerException e) {

            view.askIDAgain();
            this.scanner = new Scanner(System.in);
            int response;
            try {
                response = scanner.nextInt();
                joinGame(response);
            } catch (IOException f) {
                System.out.println(f.getMessage());
            }
        }
    }


    public void tilesNumMessage(int numOfTiles) throws RemoteException {
        try {   //comunico al server il numero scelto
            sr.tilesNumMessage(user,numOfTiles);
        }catch (IndexOutOfBoundsException e) {
            //view : notifico alla view che il numero di tiles indicato non è valido
            view.askNumTilesAgain();
        }catch (WrongPlayerException e) {
            //view : non è il turno di questo giocatore
        }catch (WrongListException e){
            //view : il numero di tiles non è valido in base agli spazi rimasti liberi
            view.askNumTilesAgain();
        } catch (WrongTurnException e) {
           // throw new RuntimeException(e);
            System.out.println("This is not your turn!");
        }
    }

    public void selectedTiles(List<Tile>choosenTiles) throws RemoteException {
        //mando le tiles a RMIImplementation
        try{
           sr.selectedTiles(user,choosenTiles);
        } catch (WrongPlayerException e) {
            //view : notifico che l'user non è quello giusto
        //}catch (WrongListException){
            //manca eccezione nel caso in cui abbia scelto un set di tile invalido
        }
    }


    public void chooseColumn (int numOfColum, List<Tile> orderedTiles) throws RemoteException {
        try {
            sr.chooseColumn(user,numOfColum,orderedTiles);
        } catch (InvalidIDException e) {
            //view : il numero della colonna non è valido
            view.askColumnAgain();
        }
    }



    public void setNewShelfie(User user, Shelfie shelfie){
        view.setPlayersShelfiesView(user, shelfie.getShelf());
    }

    public void setNewBoard(Tile[][] matrix){
        view.setBoardView(matrix);
    }

    public void setNewPoints(User user, Integer points){
        view.setPlayersPointsView(user, points);
    }


    public void notifyTurnStart(int maxValueofTiles) {
        view.NotifyTurnStart(maxValueofTiles, user.getNickname());
    }


    public void setStartOrder(ArrayList<User> order){
        view.setOrderView(order);
    }

    public void setNewCommon(CommonGoalCard card1, CommonGoalCard card2){
        view.setCommon1View(card1);
        view.setCommon2View(card2);
    }


    public void setNewPersonal(PersonalGoalCard card){
        view.setPlayersPersonalCardView(card);
    }

    //----------------------------------------------------------------------------------------------------------
    //metodi che chiama il server:

    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) throws RemoteException {
        //view : faccio vedere illuminate le tiles nella lista
        view.takeableTiles(choosableTilesList);
    }

}
