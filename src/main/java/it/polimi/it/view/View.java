package it.polimi.it.view;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;

public class View {
    private HashMap<User, Tile[][]> playersShelfies = new HashMap<>();
    private HashMap<User, String> playersPoints = new HashMap<>();
    private HashMap<User, String> playersNicknames = new HashMap<>();
    private Tile[][] playersPersonalCard;
    private ArrayList<User> order;
    private String[][] board = new String[9][9];
    //private Tile[][] highlightedBoard = new Tile[9][9];
    private String common1;
    private String common1SecondPart;
    private String common2;
    private String common2SecondPart;

    public final String firstLine = "╔════════════════════════════════════════════════════════════════╗";//1
    private final String blankLine= "║                                                                ║"; //2, 3, 5, 20, 54
    private final String lastLine = "╚════════════════════════════════════════════════════════════════╝";//55
    private final String border = "║";
    private final String chatBoxUp =    "┌────────────────────────────────┐";
    private final String chatBorder=    "│";
    private final String blankChatLine= "│                                │";
    private final String chatBoxDown =  "└────────────────────────────────┘";

    private final String noPlayer = "            ";
    private static final String colorReset = "\u001B[0m";

    private String namesLine;//line 4
    private String pointsLine;//Line 21
    private int numPlayers;
    private ArrayList<String> names;
    private List<List<Tile>> choosableTilesList;
    

    public View(){
        return;
    }


    //METODI PER ORDINE E NICKNAME
    public void setOrderView(ArrayList<User> order){
        Collections.copy(this.order, order);
        for (User user : order) {
            setPlayersNicknamesView(user);
        }
        setPaddedNames();
        pointInitializer();
        shelfieInitializer();
    }

    public void setPlayersNicknamesView(User player){
        playersNicknames.put(player, player.getNickname());
    }
    public void setPaddedNames(){
        for (User user : order) {
            names.add(nickPadder(user.getNickname()));
        }
    }
    public String nickPadder(String nick){
        while(nick.length()<12){
            nick = nick.concat(" ");
        }
        return nick;
    }




    ////METODI PER I PUNTI
    private void pointInitializer() {
        for (User user : order) {
            playersPoints.put(user, "00");
        }
    }
    public void setPlayersPointsView(User player, int points){
        String pointString = pTS(points);
        playersPoints.put(player, pointString);
    }
    private String pTS(int points) {
        String pointString = Integer.toString(points);
        if(pointString.length()==1){
            pointString = " " + pointString;
        }
        return pointString;
    }




    //METODI PER LE SHELFIE
    private void shelfieInitializer() {
        for (User user : order) {
            playersShelfies.put(user, new Shelfie().getShelf());
        }
    }
    public void setPlayersShelfiesView(User player, Tile[][] shelfie){
        playersShelfies.put(player, shelfie);
    }






    //METODI PERSONAL CARDS
    public void setPlayersPersonalCardView(PersonalGoalCard card){
        playersPersonalCard = new Tile[6][5];
        for(int row=0; row<6; row++){
            for(int column=0; column<5; column++){
                playersPersonalCard[row][column] = new Tile(row, column, PossibleColors.DEFAULT);
            }
        }
        playersPersonalCard[card.getCyanposRow()][card.getCyanposColumn()] = new Tile(PossibleColors.CYAN);
        playersPersonalCard[card.getBlueposRow()][card.getBlueposColumn()] = new Tile(PossibleColors.BLUE);
        playersPersonalCard[card.getPinkposRow()][card.getPinkposColumn()] = new Tile(PossibleColors.PINK);
        playersPersonalCard[card.getYellowposRow()][card.getYellowposColumn()] = new Tile(PossibleColors.YELLOW);
        playersPersonalCard[card.getGreenposRow()][card.getGreenposColumn()] = new Tile(PossibleColors.GREEN);
        playersPersonalCard[card.getWhiteposRow()][card.getWhiteposColumn()] = new Tile(PossibleColors.WHITE);
    }




    //METODI COMMON CARDS
    public void setCommon1View(CommonGoalCard card1){
        int id1 = card1.getID();
        common1 = commonDescription(id1);
        common1SecondPart = commonDescriptionSecondPart(id1);
    }
    public void setCommon2View(CommonGoalCard card2){
        int id2 = card2.getID();
        common2 = commonDescription(id2);
        common2SecondPart = commonDescriptionSecondPart(id2);
    }




    //MEDTODI BOARD
    public void setBoardView(Tile[][] matrix){
        for (int i=0; i<9; i++){
            for (int j=0; j<9; j++){
                board[i][j] = colorPicker(matrix[i][j].getColor());
            }
        }
    }




    //UTILS
    private String colorPicker(String color) {
        switch (color){
            case "CYAN":return"\u001B[46m  ";
            case "BLUE":return"\u001B[44m  ";
            case "GREEN":return"\u001B[42m  ";
            case "PINK":return"\u001B[45m  ";
            case "YELLOW":return"\u001B[43m  ";
            case "WHITE":return"\u001B[47m  ";
            default:return"\u001B[49m  ";
        }
    }























    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //METODI CHE GESTISCONO CIO' CHE POI VIENE STAMPATO
    public void setNamesLine(){
        //Line 4
        switch (numPlayers){
            case 2: namesLine = border + "   " + names.get(0) + "   " + names.get(1) + "   " + noPlayer + "   " + noPlayer + "    " + border;
            case 3: namesLine = border + "   " + names.get(0) + "   " + names.get(1) + "   " + names.get(2) + "   " + noPlayer + "    "+ border;
            case 4: namesLine = border + "   " + names.get(0) + "   " + names.get(1) + "   " + names.get(2) + "   " + names.get(3) + "    "+ border;
        }
    }

    public void setPointsLine(){
        switch (numPlayers){
            case 2:pointsLine = border + "    " + "Points: " + playersPoints.get(order.get(0)) + "     " + "Points: " + playersPoints.get(order.get(1)) + "     " + "          " + "     " + "          " + "      " + border;
            case 3:pointsLine = border + "    " + "Points: " + playersPoints.get(order.get(0)) + "     " + "Points: " + playersPoints.get(order.get(1)) + "     " + "Points: " + playersPoints.get(order.get(2)) + "     " + "          " + "      " + border;
            case 4: pointsLine = border + "    " + "Points: " + playersPoints.get(order.get(0)) + "     " + "Points: " + playersPoints.get(order.get(1)) + "     " + "Points: " + playersPoints.get(order.get(2)) + "     " + "Points: " + playersPoints.get(order.get(3)) + "      " + border;
        }
    }


    public String commonDescription(int id) {
        // stringhe lunghe 48 caselle
        switch (id) {
            case 1:
                return "Two groups each containing 4 tiles of the same ";
            case 2:
                return "Two columns each formed by 6  different types  ";
            case 3:
                return "Four groups each containing at least 4 tiles of";
            case 4:
                return "Six groups each containing at least 2 tiles of ";
            case 5:
                return "Three columns each formed by 6 tiles of        ";
            case 6:
                return "Two lines each formed by 5 different types     ";
            case 7:
                return "Four lines each formed by 5 tiles of maximum   ";
            case 8:
                return "Four tiles of the same type in the four corners";
            case 9:
                return "Eight tiles of the same type. There’s no       ";
            case 10:
                return "Five tiles of the same type forming an X.      ";
            case 11:
                return "Five tiles of the same type forming a diagonal.";
            case 12:
                return "Five columns of increasing or decreasing       ";
            default:
                return null;
        }
    }
    public String commonDescriptionSecondPart(int id) {
        switch (id) {
            case 1:
                return "type in a 2x2 square.                          ";
            case 2:
            case 6:
                return "of tiles.                                      ";
            case 3:
            case 4:
                return "the same type that may differ for each group.  ";
            case 5:
                return "maximum three different types.                 ";
            case 7:
                return "three different types.                         ";
            case 8:
                return "of the bookshelf.                              ";
            case 9:
                return "restriction about the position of these tiles. ";
            case 10:
            case 11:
                return "                                               ";
            case 12:
                return "height from the left to the right.             ";
            default:
                return null;
        }
    }
    
    public void takeableTiles(List<List<Tile>> choosableTilesList) {
        this.choosableTilesList = choosableTilesList;
    }

    public void askNickname() {
        out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Title();
        out.println("\n");
        out.println("Welcome to My Shelfie!");
        out.println("\n\n\n");
        out.println("Please submit your nickname! ");
        
    }


    public void Title() {
        String title =  "     ▒█▀▄▀█ █▒ █ 　 ▒█▀▀▀█ █░ █ █▀▀ █▒░ █▀▀ ░▀░ █▀▀\n" +
                        "     ▒█▒█░█ █▄▄█ 　 ░▀▀▀▄▄ █▀▀█ █▀▀ █▒░ █▀▀ ▀█▀ █▀▀\n" +
                        "     ▒█░░░█ ▄▄▄█ 　 ▒█▄▄▄█ ▀░ ▀ ▀▀▀ ▀▀▀ ▀░  ▀▀▀ ▀▀▀\n";
        out.println(title);
    }

    public void askNicknameAgain() {
        out.println("This nickname is already taken, try with another one! ");
    }

    public void joinOrCreate(String clientInput) {
        out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Title();
        out.println("\n");
        out.println("Welcome to My Shelfie, " + clientInput + "!");
        out.println("\n\n\n");
        out.println("Do you want to join or create a new Game? ");
    }

    public void askNumPlayerAgain() {

        out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Title();

        out.println("\n\n\n");
        out.println("Retry! You must insert a number between 2 and 4... ");
    }

    public void askIDAgain() {
        out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Title();

        out.println("\n\n\n");
        out.println("Retry! You must insert a valid GameID... ");
    }

    public void askCreate() {
        out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Title();

        out.println("\n\n\n");
        out.println("Insert a number between 2 and 4 to choose how many players can play... ");
    }

    public void askJoin() {
        out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Title();

        out.println("\n\n\n");
        out.println("Insert the ID of the Game you'd like to play... ");
    }







    public void NotifyTurnStart(int maxValueofTiles, String username) {//stampa tipo Bro è il tuo turno, chiedi di scegilere quante tile vuole; in input ho il max numero che posso prendere
        out.print("Hey " + username + " it's your turn!\nChoose how many Tiles you want to take from the LivingRoom... ");
    }
    public void askNumTilesAgain() {
        out.print("Are you dumb!? Please retry... ");

    }

    public void askColumnAgain() {
    }

    public void askTileRow(int i) {//chiedi la riga della i+1-esima tile da prendere dalla board
    }

    public void askTileCOl(int i) {//chiedi la colonna della i+1-esima tile da prendere dalla board
    }

    public void askColumn() {
    }

    public void askOrder(String s) {
        out.println(s);
    }
}