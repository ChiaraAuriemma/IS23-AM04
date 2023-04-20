package it.polimi.it.network.client;

import it.polimi.it.controller.Exceptions.ExistingNicknameException;
import it.polimi.it.controller.Exceptions.IllegalValueException;
import it.polimi.it.controller.Exceptions.InvalidIDException;
import it.polimi.it.controller.Exceptions.WrongPlayerException;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.server.ServerRMI;

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
    public ClientRMIApp(int port, String ip) throws RemoteException {
        this.port = port;
        this.ip = ip;
    }

    public void startClient() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(ip,port);
        sr = (ServerRMI) registry.lookup("server_RMI");
        //view : do la possibilità all'utente di inserire un nickname
    }

    public void login(String userName) throws RemoteException {
        try{
            user = sr.login(this,userName);
            //view : passo alla schermata con create e join game
        }catch (ExistingNicknameException e) {
            //view : notifico la view che deve riproporre l'inserimento del nickname con l'errore in rosso
        }

    }

    public void createGame(int playerNumber) throws RemoteException {
        try{
            sr.createGame(user,playerNumber);
        } catch (WrongPlayerException e) {
            //view : notifico alla view che il numero di giocatori inseriti non è corretto
        }
    }

    public void joinGame(int gameID) throws RemoteException {
        try{
            sr.joinGame(user,gameID);
        }catch (InvalidIDException e) {
            //view : notifico alla view che l'id non è corretto
        }catch (WrongPlayerException e){
            //view : notifico alla view che ci sono già troppi player
        }
    }

    public void tilesNumMessage(int numOfTiles) throws RemoteException {
        try {
            sr.tilesNumMessage(user,numOfTiles);
        }catch (IndexOutOfBoundsException e) {
            //view : notifico alla view che il numero di tiles indicato non è valido
        }catch (WrongPlayerException e) {
            //view : non è il turno di questo giocatore
        }catch (WrongListException e){
            //view : il numero di tiles non è valido in base agli spazi rimasti liberi
        }
    }

    public void selectedTiles(List<Tile>choosenTiles) throws RemoteException {
        try{
           sr.selectedTiles(user,choosenTiles);
        } catch (WrongPlayerException e) {
            //view : notifico che l'user non è quello giusto
        }
    }

    public void chooseColumn (int numOfColum, List<Tile> orderedTiles) throws RemoteException {
        try {
            sr.chooseColumn(user,numOfColum,orderedTiles);
        } catch (InvalidIDException e) {
            //view : il numero della colonna non è valido
        }
    }

}
