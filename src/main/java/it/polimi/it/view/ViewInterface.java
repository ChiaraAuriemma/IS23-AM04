package it.polimi.it.view;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ViewInterface {


    //stringhe di nicknames ordinate in base a come giocano
    public void setOrderView(ArrayList<String> order);

    //Board iniziale
    //setter board
    public void setBoardView(Tile[][] matrix);

    //setter shelfie
    public void setPlayersShelfiesView(String player, Tile[][] shelfie);

    //setter carte
    public void setPlayersPersonalCardView(PersonalGoalCard card);
    public void setCommon1View(int id);
    public void setCommon2View(int id);



    //Tiles della board che in un dato turno possono essere prese
        //al momento inutilizzato
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num);

    //se volessi illuminare le colonne sceglibili, nella cli non lo uso
    public void setPossibleColumns(boolean[] choosableColumns);




    public void printError(String error);



    //setter punti
    public void setPlayersPointsView(String player, int points);
    public void setFinalPoints(List<String> users, ArrayList<Integer> points) throws IOException;





    //setter gameid
    public void setGameID(int gameId);



    //per il token del primo a finire
    public void setEndToken(String user);




    //"tocca a te"; maxValueofTiles è il num max di tile che può prendere dalla board
    public void NotifyTurnStart(int maxValueofTiles, String username) ;



    //io lo uso per stampare la nuova cli da capo, a te potrebbe non servire
    public void update() ;



    public void updateChat(List<String> currentChat) throws IOException;


    //setter per il nickname del giocatore che ha aperto l'app
    void setThisNick(String nickname);

    String getTileColor(int row, int col);

    void askNickname();

    void joinOrCreate(String username) throws IOException;

    void printTile(String color, int row, int column);

    void printThings(String s);

    void printCommands();

    void askColumn() throws IOException;


    void boardRefill();

    void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList);

    public void clean();
}
