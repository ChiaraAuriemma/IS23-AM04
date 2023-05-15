package it.polimi.it.view;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.out;

public class View implements Serializable {
    private static final long serialVersionUID = 892639889752766985L;
    private HashMap<String, Tile[][]> playersShelfies = new HashMap<String, Tile[][]>();
    private HashMap<String, String> playersPoints = new HashMap<String, String>();
    private HashMap<User, String> playersNicknames = new HashMap<>();
    private Tile[][] playersPersonalCard = new Tile[6][5];
    private ArrayList<String> order =new ArrayList<>();
    private String[][] board = new String[9][9];
    private String common1;
    private String common1SecondPart;
    private String common2;
    private String common2SecondPart;

    public final String firstLine = "╔════════════════════════════════════════════════════════════════╗";//1
    private final String blankLine = "║                                                                ║"; //2, 3, 5, 20, 54
    private final String lastLine = "╚════════════════════════════════════════════════════════════════╝";//55
    private final String border = "║";
    private final String chatBoxUp = "┌─────────────────────────────────┐";
    private final String chatBorder = "│";
    private final String chatBoxDown = "└─────────────────────────────────┘";

    private final String noPlayer = "            ";
    private static final String colorReset = "\u001B[0m";

    private String namesLine;//line 4
    private String pointsLine;//Line 21
    private int numPlayers;
    private ArrayList<String> names = new ArrayList<>();
    private List<List<Tile>> choosableTilesList = new ArrayList<>();
    private int gameID;
    private String endToken;





    private String Line1;
    private String Line2;
    private String Line3;
    private String Line4;
    private String Line5;
    private String Line6;
    private String Line7;
    private String Line8;
    private String Line9;
    private String Line10;
    private String Line11;
    private String Line12;
    private final String Line13 = "║   Common Goal:                                     Personal:   ║";
    private String Line14;
    private String Line15;
    private String Line16;
    private String Line17;
    private String Line18;
    private String Line19;
    private String Line20;
    private String Line21;
    private String Line22;
    private String Line23 = "║                                                    Personal    ║";
    private String Line24 = "║   Common Goal:                                                 ║";
    private String Line25;
    private String Line26;
    private String Line27;
    private String Line28;
    private String Line29;
    private String Line30;
    private String Line31;
    private final String Line32 = "║                           └─────────────────────────────────┘  ║";
    private String Line33;

    private List<String> chatMessages = new ArrayList<>(); // massimo 9 messaggi, lunghezza 33
    private final String blankChatLine = "                                 ";

    public View() {
        return;
    }

    /**
     * Given
     *
     * @param row row
     *            and
     * @param col column
     *            of a tile in th board
     * @return the color of that tile
     */
    public String getTileColor(int row, int col) {
        for (List<Tile> l : choosableTilesList) {
            for (Tile t : l) {
                if (t.getRow() == row && t.getColumn() == col) {
                    return t.getColor();
                }
            }
        }
        return "DEFAULT";
    }


    //METODI PER ORDINE E NICKNAME

    /**
     * Setter method for the player's turn order
     *
     * @param order is the ordered arrayList of the users in the game
     */
    public void setOrderView(ArrayList<String> order) {

        numPlayers = order.size();
        //Collections.copy(this.order, order);
        int i=0;
        for (String nick : order) {
            this.order.add(order.get(i));
            //setPlayersNicknamesView(nick);
            i++;
        }
        setPaddedNames();
        pointInitializer();
        shelfieInitializer();
    }

    /**
     * Setter method for the players' nicknames
     *
     * @param player is a user instance
     */
    //public void setPlayersNicknamesView(String player) {
    //    playersNicknames.put(player, player.getNickname());
    //}

    /**
     * Setter method for the nicknames in their padded version (fixed 12 chars length)
     */
    public void setPaddedNames() {
        for (String user : order) {
            names.add(nickPadder(user));
        }
    }


    /**
     * Helper mehod that given a
     *
     * @param nick nickname string
     * @return the nickname string padded to a fixed length of 12 chars
     */
    public String nickPadder(String nick) {
        while (nick.length() < 12) {
            nick = nick.concat(" ");
        }
        return nick;
    }


    ////METODI PER I PUNTI

    /**
     * Initializes, for every user a string "00" which represents the initial points of every player
     */
    private void pointInitializer() {
        for (String user : order) {
            playersPoints.put(user, "00");
        }
    }

    /**
     * Setter method, used when a player's points amount changed
     *
     * @param player is the user
     * @param points is the new value of the user's points
     */
    public void setPlayersPointsView(String player, int points) {
        String pointString = pTS(points);
        playersPoints.put(player, pointString);
    }

    /**
     * Helper method that given a
     *
     * @param points integer
     * @return the corresponding points string, with a fixed length of 2
     */
    private String pTS(int points) {
        String pointString = Integer.toString(points);
        if (pointString.length() == 1) {
            pointString = " " + pointString;
        }
        return pointString;
    }




    //METODI PER LE SHELFIE

    /**
     * Initializes a new shelf for every user, to be put in the user-shelf hasmap
     */
    private void shelfieInitializer() {
        for (String user : order) {
            playersShelfies.put(user, new Shelfie().getShelf());
        }
    }

    /**
     * Setter method for the user-shelf hashmap
     *
     * @param player  is the current user
     * @param shelfie is the new shelfie, used to update the previous shelfie value
     */
    public void setPlayersShelfiesView(String player, Tile[][] shelfie) {
        playersShelfies.put(player, shelfie);
    }


    //METODI PERSONAL CARDS

    /**
     * Setter Method
     *
     * @param card given the personal card, sets the example Shelf to get it visualized on screen
     */
    public void setPlayersPersonalCardView(PersonalGoalCard card) {
        playersPersonalCard = new Tile[6][5];
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 5; column++) {
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

    /**
     * Setter method
     *
     * @param card1 given the card, chooses the output messages
     */
    public void setCommon1View(CommonGoalCard card1) {
        int id1 = card1.getID();
        common1 = commonDescription(id1);
        common1SecondPart = commonDescriptionSecondPart(id1);
    }

    /**
     * Setter method
     *
     * @param card2 given the card, chooses the output messages
     */
    public void setCommon2View(CommonGoalCard card2) {
        int id2 = card2.getID();
        common2 = commonDescription(id2);
        common2SecondPart = commonDescriptionSecondPart(id2);
    }


    //MEDTODI BOARD
    public void setBoardView(Tile[][] matrix) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = colorPicker(matrix[i][j].getColor());
            }
        }
    }


    //UTILS
    private String colorPicker(String color) {
        switch (color) {
            case "CYAN":
                return "\u001B[46m  ";
            case "BLUE":
                return "\u001B[44m  ";
            case "GREEN":
                return "\u001B[42m  ";
            case "PINK":
                return "\u001B[45m  ";
            case "YELLOW":
                return "\u001B[43m  ";
            case "WHITE":
                return "\u001B[47m  ";
            default:
                return "\u001B[49m  ";
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
    public void setNamesLine() {
        //Line 4
        switch (numPlayers) {
            case 2:
                namesLine = border + "   " + names.get(0) + "   " + names.get(1) + "   " + noPlayer + "   " + noPlayer + "    " + border;
            case 3:
                namesLine = border + "   " + names.get(0) + "   " + names.get(1) + "   " + names.get(2) + "   " + noPlayer + "    " + border;
            case 4:
                namesLine = border + "   " + names.get(0) + "   " + names.get(1) + "   " + names.get(2) + "   " + names.get(3) + "    " + border;
        }
    }

    public void setPointsLine() {
        switch (numPlayers) {
            case 2:
                pointsLine = border + "    " + "Points: " + playersPoints.get(order.get(0)) + "     " + "Points: " + playersPoints.get(order.get(1)) + "     " + "          " + "     " + "          " + "     " + border;
            case 3:
                pointsLine = border + "    " + "Points: " + playersPoints.get(order.get(0)) + "     " + "Points: " + playersPoints.get(order.get(1)) + "     " + "Points: " + playersPoints.get(order.get(2)) + "     " + "          " + "     " + border;
            case 4:
                pointsLine = border + "    " + "Points: " + playersPoints.get(order.get(0)) + "     " + "Points: " + playersPoints.get(order.get(1)) + "     " + "Points: " + playersPoints.get(order.get(2)) + "     " + "Points: " + playersPoints.get(order.get(3)) + "     " + border;
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
        String title = "     ▒█▀▄▀█ █▒ █ 　 ▒█▀▀▀█ █░ █ █▀▀ █▒░ █▀▀ ░▀░ █▀▀\n" +
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


    public void NotifyTurnStart(int maxValueofTiles, String username) {//stampa tipo Bro è il tuo turno, chiedi di scegliere quante tile vuole; in input ho il max numero che posso prendere
        out.print("Hey " + username + " it's your turn!\nChoose how many Tiles you want to take from the LivingRoom... ");
    }

    public void askNumTilesAgain() {
        out.print("Are you dumb!? Please retry... ");

    }

    public void askColumnAgain() {
    }

    public void printCommands() {
        out.println("┌────────────────────┐\n" +
                "│List of commands:   │\n" +
                "│ login>>            │\n" +
                "│ create_game>>      │\n" +
                "│ join_game>>        │\n" +
                "│ num_tiles>>        │\n" +
                "│ take_tiles>>       │\n" +
                "│ choose_column>>    │\n" +
                "│ help>>             │\n" +
                "└────────────────────┘");
    }

    public void setGameID(int gameId) {
        this.gameID = gameId;
        out.print("Game ID: " + gameId + "\n");
    }

    public void setEndToken(String user) {
        this.endToken = user;
    }

    public void askColumn() {
        out.println("Please choose in which column you want to put the tiles that you took... ");
    }

    public void printError(String error) {
        out.println(error);
    }

    public void setFinalPoints(List<String> users, ArrayList<Integer> points) {
        for (String u: users){
            setPlayersPointsView(u, points.get(users.indexOf(u)));
        }
    }

    public void setPossibleColumns(boolean[] choosableColumns) {
        askColumn();
        out.print("You can choose between the columns numbered as: ");
        int i=0;
        for (boolean b: choosableColumns){
            if (b){
                out.print(i);
                out.print(" ");
            }
            i++;
        }
        out.print("\n");
    }

   /* public void recover(Game game, int gameID, String username) {
        this.gameID=gameID;
        numPlayers=game.getNumplayers();
        setBoardView(game.getBoard().getMatrix());
        setCommon1View(game.getCommonCard1());
        setCommon2View(game.getCommonCard2());
        this.order = game.playersOrder();
        int index = order.indexOf(user);
        setPlayersPersonalCardView(game.getPersonalCard(index));
       // this.endToken=game.getEndToken();


        for(User u: order){
            setPlayersShelfiesView(u, u.getShelfie().getShelf());
            setPlayersNicknamesView(u);
            index = order.indexOf(u);
            setPlayersPointsView(u, u.getGame().getPoint(index));
            setPlayersNicknamesView(u);

        }
    }

*/
    public String boardRow (int row){
        String returnString = String.valueOf(row) +" ";
        for(int i=0; i<9; i++){
            returnString = returnString + board[row][i];
        }
        return returnString;
    }

    public void update(){
        //serve a stampare
        updateEveryLine();
        printEveryLine();
    }

    private void printEveryLine() {
        out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", Line1, Line2, Line3, Line4, Line5, Line6, Line7, Line8, Line9, Line10, Line11, Line12, Line13, Line14, Line15, Line16, Line17, Line18, Line19, Line20, Line21, Line22, Line23, Line24, Line25, Line26, Line27, Line28, Line29, Line30, Line31, Line32, Line33);
    }

    private void updateEveryLine() {
        //setPaddedNames();
        //Line 1 is the upper, Line 2 is blank
        Line1 = firstLine;
        Line2 = blankLine;
        setNamesLine();
        Line3 = namesLine;
        Line4 = setLine4();
        Line5 = setShelfiesEvenLine(5);
        Line6 = setShelfiesEvenLine(4);
        Line7 = setShelfiesEvenLine(3);
        Line8 = setShelfiesEvenLine(2);
        Line9 = setShelfiesEvenLine(1);
        Line10 = setShelfiesEvenLine(0);
        setPointsLine();
        Line11 = pointsLine;
        Line12 = blankLine;
        //Lines 13 is always the same, actually final

        Line14 = setLine14();
        Line15 = setLine15();
        Line16 = setLine16();
        Line17 = setLine17();
        Line18 = setLine18();
        Line19 = setLine19();
        Line20 = blankLine;


        Line21 = setLine21();
        Line22 = setLine22();

        Line23 = setLineBoardChat(0);
        Line24 = setLineBoardChat(1);
        Line25 = setLineBoardChat(2);
        Line26 = setLineBoardChat(3);
        Line27 = setLineBoardChat(4);
        Line28 = setLineBoardChat(5);
        Line29 = setLineBoardChat(6);
        Line30 = setLineBoardChat(7);
        Line31 = setLineBoardChat(8);

        //Lines 32 is always the same, actually final

        Line33 = lastLine;
    }


    private String setLineBoardChat(int line) {
        String returnString = null;
        returnString =  border + "   " + boardRow(line) + "\u001B[0m    " + chatBorder + chatMessages.get(line) + chatBorder +  "\u001B[0m  " + border;
        return returnString;
    }


    private String setLine21() {
        String returnString = null;
        returnString =  border + "   LivingRoom:             Chat:                                " + border;
        return returnString;
    }

    private String setLine22() {
        String returnString = null;
        returnString =  border + "      0 1 2 3 4 5 6 7 8    ┌─────────────────────────────────┐  " + border;
        return returnString;
    }

    private String setLine14() {
        String returnString = null;
        returnString =  border + "   "+ common1 +"  " + sColorPicker(playersPersonalCard, 5, 0)
                + sColorPicker(playersPersonalCard, 5, 1)+ sColorPicker(playersPersonalCard, 5, 2)+ sColorPicker(playersPersonalCard, 5, 3)
                + sColorPicker(playersPersonalCard, 5, 4) + "\u001B[0m  " + border;
        return returnString;
    }
    private String setLine15() {
        String returnString = null;
        returnString =  border + "   "+ common1SecondPart +"  " + sColorPicker(playersPersonalCard, 4, 0)
                + sColorPicker(playersPersonalCard, 4, 1)+ sColorPicker(playersPersonalCard, 4, 2)+ sColorPicker(playersPersonalCard, 4, 3)
                + sColorPicker(playersPersonalCard, 4, 4) + "\u001B[0m  " + border;
        return returnString;
    }

    private String setLine16() {
        String returnString = null;
        returnString =  border + "                                                    "+ sColorPicker(playersPersonalCard, 3, 0)
                + sColorPicker(playersPersonalCard, 3, 1)+ sColorPicker(playersPersonalCard, 3, 2)+ sColorPicker(playersPersonalCard, 3, 3)
                + sColorPicker(playersPersonalCard, 3, 4) + "\u001B[0m  " + border;
        return returnString;
    }
    private String setLine17() {
        String returnString = null;
        returnString =  border + "   Common Goal:                                     "+ sColorPicker(playersPersonalCard, 2, 0)
                + sColorPicker(playersPersonalCard, 2, 1)+ sColorPicker(playersPersonalCard, 2, 2)+ sColorPicker(playersPersonalCard, 2, 3)
                + sColorPicker(playersPersonalCard, 2, 4) + "\u001B[0m  " + border;
        return returnString;
    }

    private String setLine18() {
        String returnString = null;
        returnString =  border + "   "+ common2 +"  " + sColorPicker(playersPersonalCard, 1, 0)
                + sColorPicker(playersPersonalCard, 1, 1)+ sColorPicker(playersPersonalCard, 1, 2)+ sColorPicker(playersPersonalCard, 1, 3)
                + sColorPicker(playersPersonalCard, 1, 4) + "\u001B[0m  " + border;
        return returnString;
    }
    private String setLine19() {
        String returnString = null;
        returnString =  border + "   "+ common2SecondPart +"  " + sColorPicker(playersPersonalCard, 0, 0)
                + sColorPicker(playersPersonalCard, 0, 1)+ sColorPicker(playersPersonalCard, 0, 2)+ sColorPicker(playersPersonalCard, 0, 3)
                + sColorPicker(playersPersonalCard, 0, 4) + "\u001B[0m  " + border;
        return returnString;
    }








    private String setShelfiesEvenLine(int row) {//pari, con numero riga
        String returnString=null;
        String r = String.valueOf(row);
        Tile[][] s1 = playersShelfies.get(order.get(0));
        Tile[][] s2 = playersShelfies.get(order.get(1));
        switch (numPlayers){
            case 2:
                returnString = border + "   "+r+" " +sColorPicker(s1,row,0)+sColorPicker(s1,row,1)+sColorPicker(s1,row,2)+sColorPicker(s1,row,3)+sColorPicker(s1,row,4) + "\u001B[0m   " +
                        r +" " +sColorPicker(s2,row,0)+sColorPicker(s2,row,1)+sColorPicker(s2,row,2)+sColorPicker(s2,row,3)+sColorPicker(s2,row,4) + "\u001B[0m   " +
                        "            " + "            " + " " + border
                ;break;
            case 3:
                Tile[][] s3 = playersShelfies.get(order.get(2));

                returnString = border + "   "+r+" " +sColorPicker(s1,row,0)+sColorPicker(s1,row,1)+sColorPicker(s1,row,2)+sColorPicker(s1,row,3)+sColorPicker(s1,row,4) + "\u001B[0m   " +
                        r +" " +sColorPicker(s2,row,0)+sColorPicker(s2,row,1)+sColorPicker(s2,row,2)+sColorPicker(s2,row,3)+sColorPicker(s2,row,4) + "\u001B[0m   " +
                        r +" " +sColorPicker(s3,row,0)+sColorPicker(s3,row,1)+sColorPicker(s3,row,2)+sColorPicker(s3,row,3)+sColorPicker(s3,row,4) + "\u001B[0m   " +
                        "            " + " " + border
                ;break;
            case 4:
                Tile[][] ss3 = playersShelfies.get(order.get(2));
                Tile[][] s4 = playersShelfies.get(order.get(3));

                returnString = border + "   "+r+" " +sColorPicker(s1,row,0)+sColorPicker(s1,row,1)+sColorPicker(s1,row,2)+sColorPicker(s1,row,3)+sColorPicker(s1,row,4) + "\u001B[0m   " +
                        r +" " +sColorPicker(s2,row,0)+sColorPicker(s2,row,1)+sColorPicker(s2,row,2)+sColorPicker(s2,row,3)+sColorPicker(s2,row,4) + "\u001B[0m   " +
                        r +" " +sColorPicker(ss3,row,0)+sColorPicker(ss3,row,1)+sColorPicker(ss3,row,2)+sColorPicker(ss3,row,3)+sColorPicker(ss3,row,4) + "\u001B[0m   " +
                        r +" " +sColorPicker(s4,row,0)+sColorPicker(s4,row,1)+sColorPicker(s4,row,2)+sColorPicker(s4,row,3)+sColorPicker(s4,row,4) + "\u001B[0m   " +
                        " " + border
                ;break;
        }
        return returnString;
    }

    private String sColorPicker(Tile[][] shelf, int row, int col){
        return colorPicker(shelf[row][col].getColor());
    }

    private String setLine4() {
        String returnString = null;
        switch (numPlayers){
            case 2: returnString = border + "   " +
                    "   0 1 2 3 4   " + "   0 1 2 3 4   " + "               " + "               " +
                    " " + border
            ;break;
            case 3: returnString = border + "   " +
                    "   0 1 2 3 4   " + "   0 1 2 3 4   " + "   0 1 2 3 4   " + "               " +
                    " " + border
            ;break;
            case 4: returnString = border + "   " +
                                    "   0 1 2 3 4   " + "   0 1 2 3 4   " + "   0 1 2 3 4   " + "   0 1 2 3 4   " +
                                    " " + border
                                    ;break;
        }
        return returnString;
    }



    public void setChat(){
        for (int i=0; i<20; i++){
            chatMessages.add("Placeholder Chat Message JaaackBB");
        }
    }


    public void fakePersonal() {
        playersPersonalCard = new Tile[6][5];
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 5; column++) {
                playersPersonalCard[row][column] = new Tile(row, column, PossibleColors.GREEN);
            }
        }
    }

}