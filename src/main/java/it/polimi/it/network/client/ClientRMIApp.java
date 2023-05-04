package it.polimi.it.network.client;

import it.polimi.it.controller.Exceptions.*;
import it.polimi.it.model.Board.Board;
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
import java.util.List;

public class ClientRMIApp extends UnicastRemoteObject implements ClientRMI {
    private int port;
    private String ip;
    private Registry registry;
    private ServerRMI sr;

    private User user;
    private View view;
    public ClientRMIApp(int port, String ip) throws RemoteException {
        this.port = port;
        this.ip = ip;
        this.view = new View();
    }

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
        String clientInput;
        //view : do la possibilità all'utente di inserire un nickname
        view.askNickname();

        try {
            clientInput = stdIn.readLine();
            login(clientInput);
            //view: mostro la scelta tra creategame e joingame
            view.joinOrCreate();
            // delego in base alla scelta che fa la view al metodo create o join
            //appena mi arriva startgame dal server avvio la partita
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void login(String userName) throws RemoteException {
        try{
            user = sr.login(this,userName);
            //view : passo alla schermata con create e join game
        }catch (ExistingNicknameException e) {
            //view : notifico la view che deve riproporre l'inserimento del nickname con l'errore in rosso
            view.askNicknameAgain();
        }

    }

    public void setNewShelfie(User user, Shelfie shelfie){
        view.updateShelfie(user, shelfie);
    }

    public void setNewBoard(Board matrix){
        view.updateBoard(matrix);
    }

    public void setNewPoints(User user, int points){
        view.pointsList(user, points);
    }

    public void createGame(int playerNumber) throws RemoteException {
        try{
            sr.createGame(user,playerNumber);
        } catch (WrongPlayerException e) {
            //view : notifico alla view che il numero di giocatori inseriti non è corretto
            view.askNumPlayerAgain();
        }
    }

    public void joinGame(int gameID) throws RemoteException {
        try{
            sr.joinGame(user,gameID);
        }catch (InvalidIDException e) {
            //view : notifico alla view che l'id non è corretto
            view.askIDAgain();
        }catch (WrongPlayerException e){
            //view : notifico alla view che ci sono già troppi player
            view.askIDAgain();
        }
    }

    public void tilesNumMessage(int numOfTiles) throws RemoteException {
        try {
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
        }
    }

    public void selectedTiles(List<Tile>choosenTiles) throws RemoteException {
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

    //----------------------------------------------------------------------------------------------------------
    //metodi che chiama il server:

    public void takeableTiles(List<List<Tile>> choosableTilesList){
        //view : faccio vedere illuminate le tiles nella lista
        view.takeableTiles();
    }

}
