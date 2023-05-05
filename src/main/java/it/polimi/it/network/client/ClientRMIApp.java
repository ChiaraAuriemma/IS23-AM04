package it.polimi.it.network.client;

import it.polimi.it.controller.Exceptions.*;
import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.PossibleColors;
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


        try {
            while (!nickOK){
                clientInput = stdIn.readLine();
                login(clientInput);
            }
            //view: mostro la scelta tra creategame e joingame
            joinOrCreate(clientInput);
            // delego in base alla scelta che fa la view al metodo create o join
            //appena mi arriva startgame dal server avvio la partita
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void joinOrCreate(String clientInput) throws IOException {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String response = " ";

        view.joinOrCreate(clientInput);

        while (!response.equals("create") && !response.equals("join") && !response.equals("Create")  &&
                !response.equals("CREATE") && !response.equals("JOIN") && !response.equals("Join")){

            try {
                response = stdIn.readLine();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        if(response.equals("create") || response.equals("Create")  || response.equals("CREATE") ){
            askJoinGame();
        } else{
            askCreateGame();
        }

    }

    private void askCreateGame() throws IOException {
        view.askCreate();
        this.scanner = new Scanner(System.in);
        int response;
        try{
            response = scanner.nextInt();
            createGame(response);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void askJoinGame() {
        view.askJoin();
        this.scanner = new Scanner(System.in);
        int response;
        try{
            response = scanner.nextInt();
            joinGame(response);
        }catch (IOException e){
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

    public void tilesSelector(List<List<Tile>> choosable, int num) throws RemoteException {
        List<Tile> chosen = new ArrayList<>();
        boolean found = false;

        for(int i=0; i<num; i++){
            view.askTileRow(i);
                //leggi la riga

            this.scanner = new Scanner(System.in);
            int row=0;
            row = scanner.nextInt();


            view.askTileCOl(i);
                //leggi la colonna
            int col= scanner.nextInt();

            //controlli



            Tile thisOne = null;
            found=false;
            for (List<Tile> list: choosable){
                for(Tile t : list){
                    if(t.getRow()==row && t.getColumn()==col){
                        thisOne=new Tile(row, col, PossibleColors.valueOf(t.getColor()));
                        found=true;
                    }
                }
            }
            if(!found){
                //devo ripetere la scelta
            }else{
                chosen.add(thisOne);
            }


            //una volta che ho la lista delle chosen completa la devo inviare al sever:
            selectedTiles(chosen);
            lastChosen=chosen;

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


        //chiedo quante tile vuole-> prendo in input la risposta
        this.scanner = new Scanner(System.in);
        int response = 0;
        try {
            response = scanner.nextInt();
            while(response <=0 || response > maxValueofTiles){
                view.askNumTilesAgain();
                response = scanner.nextInt();
            }
            //joinGame(response);
            tilesNumMessage(response);

        } catch (IOException f) {
            System.out.println(f.getMessage());
        }
    }

    @Override
    public void askColumn(boolean[] choosableColumns) throws RemoteException {
        view.askColumn();
        this.scanner = new Scanner(System.in);
        int col = 0;
        try {
            col = scanner.nextInt();

            while(col <=0 || col > 5 || choosableColumns[col]){
                view.askColumnAgain();
                col = scanner.nextInt();
            }
            tilesNumMessage(col);

        } catch (IOException f) {
            System.out.println(f.getMessage());
        }

        List<Tile> ordered= new ArrayList<>();
        this.scanner = new Scanner(System.in);

        if(lastChosen.size()==1){
            ordered = lastChosen;
        }else if(lastChosen.size()==2){
            view.askOrder("Do you want to insert first the tile at " + lastChosen.get(0).getRow() + "-" + lastChosen.get(0).getRow() + "? Type 1 to do so... ");
            int risp=0;
            risp = scanner.nextInt();
            switch (risp){
                case 1: ordered.add(lastChosen.get(1));
                        ordered.add(lastChosen.get(2));break;
                default:ordered.add(lastChosen.get(2));
                        ordered.add(lastChosen.get(1));break;

            }
        }else {

            int risp1 = 0;
            while (risp1 != 1 || risp1 != 2 || risp1 != 3) {
                view.askOrder("Which tile do you want to insert first: " + lastChosen.get(0).getRow() + "-" + lastChosen.get(0).getColumn() + " or "
                        + lastChosen.get(1).getRow() + "-" + lastChosen.get(1).getColumn() + " or " + lastChosen.get(2).getRow() + "-" + lastChosen.get(2).getColumn() + "? Type 1, 2 or 3... ");
                risp1 = scanner.nextInt();
            }

            int risp2=0;
            while (risp2 != 1 || risp2 != 2 || risp2 != 3 || risp2==risp1) {
                view.askOrder("Which tile do you want to insert second: " + lastChosen.get(0).getRow() + "-" + lastChosen.get(0).getColumn() + " or "
                        + lastChosen.get(1).getRow() + "-" + lastChosen.get(1).getColumn() + " or " + lastChosen.get(2).getRow() + "-" + lastChosen.get(2).getColumn() + "? Type 1, 2 or 3... ");
                risp2 = scanner.nextInt();
            }
            int risp3=0;

            if(risp1==1 && risp2==2){
                risp3=3;
            } else if(risp1==1 && risp2==3){
                risp3=2;
            }else if(risp1==2 && risp2==3){
                risp3=1;
            }else if(risp1==2 && risp2==1){
                risp3=3;
            }else if(risp1==3 && risp2==1){
                risp3=2;
            }else if(risp1==3 && risp2==2){
                risp3=1;
            }
            ordered.add(lastChosen.get(risp1));
            ordered.add(lastChosen.get(risp2));
            ordered.add(lastChosen.get(risp3));
        }

        chooseColumn(col, ordered);

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
        tilesSelector(choosableTilesList, num);
    }

}
