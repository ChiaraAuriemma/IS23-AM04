package it.polimi.it.view.GUI;

import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Shelfie;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.view.ViewInterface;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that manages the communication between the server and the client that choose GUI
 */
public class GUIHandler implements ViewInterface {


    /**
     * Setter method for the player's turn order
     * @param order is the ordered arrayList of the users in the game
     */
    @Override
    public void setOrderView(ArrayList<String> order) {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.setPlayers(order);
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }


    /**
     * Setter method for the LivingRoom's board.
     * @param matrix is the tile matrix that represents the LivingRoom.
     */
    @Override
    public void setBoardView(Tile[][] matrix) {
        Platform.runLater(new Thread(()-> {
            Image[][] board = new Image[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String color = matrix[i][j].getColor();
                    Image image = chooseAnImage(color);
                    board[i][j] = image;
                }
            }
            GUIApplication.setBoard(board);
        }));
    }


    /**
     * Extracts a random image out of the available ones given a specific color.
     * @param color is the Tile's color.
     * @return the Image
     */
    public Image chooseAnImage(String color){
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        String num = String.valueOf(randomNumber);
        String url = "/Images/";

        if(color.equals("CYAN")){
            url = url+ "Trofei1.";
        }else if(color.equals("PINK")){
            url = url+ "Piante1.";
        }else if(color.equals("YELLOW")){
            url = url+ "Giochi1.";
        }else if(color.equals("BLUE")){
            if(randomNumber==1){
                url = url+ "Cornici";
            }else{
                url = url+ "Cornici1.";
            }
        }else if(color.equals("GREEN")){
            url = url+ "Gatti1.";
        }else if(color.equals("WHITE")){
            url = url+ "Libri1.";
        }else{
            return null;
        }
        url = url + num + ".png";
        URL imageUrl = getClass().getResource(url);
        assert imageUrl != null;
        return new Image(imageUrl.toString());

    }


    /**
     * Setter method for the user-shelf hashmap
     * @param player  is the current user
     * @param shelfie is the new shelfie, used to update the previous shelfie value
     */
    @Override
    public void setPlayersShelfiesView(String player, Tile[][] shelfie) {
        Platform.runLater(new Thread(()-> {
            Image[][] shelfImage = new Image[6][5];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    String color = shelfie[i][j].getColor();
                    Image image = chooseAnImage(color);
                    shelfImage[i][j] = image;
                }
            }
            GUIApplication.setShelfies(player,shelfImage);
        }));
    }


    /**
     * Setter Method for the PersonalGoalCard.
     * @param card is the card.
     */
    @Override
    public void setPlayersPersonalCardView(PersonalGoalCard card) {
        Platform.runLater(new Thread(()-> {
            int id = card.getId();
            String num = String.valueOf(id);
            String url = "/Images/Personal_Goals";

            if(id != 1){
                url = url + num;
            }
            url= url + ".png";
            URL imageUrl = getClass().getResource(url);
            GUIApplication.setPersonalCard(imageUrl);
        }));
    }


    /**
     * Setter Method for the first CommonGoalCard.
     * @param id is the card's id.
     */
    @Override
    public void setCommon1View(int id) {
        Platform.runLater(new Thread(()-> {
            String num = String.valueOf(id);
            String url = "/Images/" + num +".jpg";
            URL imageUrl = getClass().getResource(url);
            GUIApplication.setCommonCard1(imageUrl, id);
        }));
    }


    /**
     * Setter Method for the second CommonGoalCard.
     * @param id is the card's id.
     */
    @Override
    public void setCommon2View(int id) {
        Platform.runLater(new Thread(()-> {
            String num = String.valueOf(id);
            String url = "/Images/" + num +".jpg";
            URL imageUrl = getClass().getResource(url);
            GUIApplication.setCommonCard2(imageUrl, id);
        }));
    }


    /**
     * CLI only method.
     */
    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) {

    }


    /**
     * Method that prints the list of his Shelfie's columns that have enough empty spaces to contain the new Tiles.
     * @param choosableColumns is the list of the empty columns.
     */
    @Override
    public void setPossibleColumns(boolean[] choosableColumns) {

    }


    /**
     * Method that prints an error String.
     * @param error .
     */
    @Override
    public void printError(String error) {
        Platform.runLater(new Thread(()-> {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Error", error);
        }));
    }


    /**
     * Setter method, used when a player's points amount changed
     * @param player is the user
     * @param points is the new value of the user's points
     */
    @Override
    public void setPlayersPointsView(String player, int points) {
        Platform.runLater(new Thread(()-> {
            int i = 0;
            while (!GUIApplication.getPlayers().get(i).equals(player)){
                i++;
            }
            GUIApplication.setPoints(i,points);
        }));
    }


    /**
     * Setter method
     * @param users is the players list
     * @param points is the list of the players' points.
     */
    @Override
    public void setFinalPoints(List<String> users, ArrayList<Integer> points) throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.setFinalPoint(users, points);
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }


    /**
     * Setter for the
     * @param gameId .
     */
    @Override
    public void setGameID(int gameId) {
        Platform.runLater(new Thread(()-> {
            if(GUIApplication.getCreateOrJoin().equals("CREATE"))
                GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Create Game ID", "Your game id is " + gameId);
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }


    /**
     * Setter method for the EndToken
     * Cli only method
     * @param user
     */
    @Override
    public void setEndToken(String user) {

    }


    /**
     * Method that, at the beginning of the player's turn, asks him the number of tiles that he wants to get from the board.
     * @param maxValueofTiles is the maximum number of tiles that the player can get from the board.
     * @param username is the player's nickname.
     */
    @Override
    public void NotifyTurnStart(int maxValueofTiles, String username) {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }));
    }


    /**
     * Method that updates the GUI scene.
     * Cli only method
     */
    @Override
    public void update() {

    }


    /**
     * Forces the chat to be updated
     * @param currentChat is the list of the newest chat messages.
     */
    @Override
    public void updateChat(List<String> currentChat) throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                for (String str : currentChat) {
                    if(str.contains("\u001B[33m")){
                        String privateMessage1 = str.replace("\u001B[33m", "[DM] ");
                        String privateMessage2 = privateMessage1.replace("\u001B[39m","" );
                        stringBuilder.append(privateMessage2).append("\n");
                    }else {
                        stringBuilder.append(str).append("\n");
                    }
                }
                String joinedString = stringBuilder.toString().trim();
                GUIApplication.setCurrentChat(joinedString);
                GUIApplication.changeScene();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }));
    }


    /**
     * CLI only method, sets the player's Nickname.
     */
    @Override
    public void setThisNick(String nickname) {

    }


    /**
     * CLI only method.
     */
    @Override
    public String getTileColor(int row, int col) {
        return null;
    }


    /**
     * CLI only method, asks the player for a Nickname.
     */
    @Override
    public void askNickname() {
    }


    /**
     * Method that asks the player whether he wants to Create a new Game or Join an existing one
     * @param username is the player's nickname.
     */    @Override
    public void joinOrCreate(String username) throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }


    /**
     * CLI only method, prints a Tile's datas in order to make the player check his move before confirming.
     * @param color is the Tile's color
     * @param row is the Tile's row
     * @param column is the Tile's column
     */
    @Override
    public void printTile(String color, int row, int column) {
    }


    /**
     * CLI only method, deals with errors.
     * @param s is an errorString.
     */
    @Override
    public void printThings(String s) {
    }


    /**
     * Prints every cli command.
     * CLI only method.
     */
    @Override
    public void printCommands() {
        ;
    }


    /**
     * Method that asks the player in whick column he wants to put the tiles that he took.
     * Cli only method
     */
    @Override
    public void askColumn() throws IOException {

    }


    /**
     * Notifies the client of a LivingRoom board's refill.
     * CLI only method
     */
    @Override
    public void boardRefill() {

    }


    /**
     * Forces a GUI update after the reconnection to the game
     * @param gameID is the gameID of the game that the player just reconnected to.
     * @param matrix is the LivingRoom Board
     * @param shelfies is a List of the Players' Shelfies
     * @param id1 is the first CommonGoalCard
     * @param id2 is the second CommonGoalCard
     * @param personalGoalCard is the player's PersonaleGoalCard
     * @param points is the list of the players' points
     * @param playerList is the list of the players, ordered as their turns are.
     */
    @Override
    public void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, int id1, int id2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) {
        setOrderView(new ArrayList<>(playerList));
        setBoardView(matrix);
        setCommon1View(id1);
        setCommon2View(id2);
        setPlayersPersonalCardView(personalGoalCard);
        for(int i=0; i<playerList.size(); i++){
            setPlayersShelfiesView(playerList.get(i), shelfies.get(i));
            setPlayersPointsView(playerList.get(i), points.get(i));
        }
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

    }


    /**
     * Resets all the game's data.
     */
    @Override
    public void clean() {
        Platform.runLater(new Thread(()-> {
            for(String user: GUIApplication.getPlayers()){
                User u = new User(" ");
                Shelfie s = new Shelfie(u);
                Image[][] s1 = new Image[6][5];
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        String color = s.getShelf()[i][j].getColor();
                        Image image = chooseAnImage(color);
                        s1[i][j] = image;
                    }
                }
                GUIApplication.setShelfies(user,s1);
            }

            for(String user: GUIApplication.getPlayers()){
                int i = 0;
                while (!GUIApplication.getPlayers().get(i).equals(user)){
                    i++;
                }
                GUIApplication.setPoints(i,0);
            }

            GUIApplication.getFinalPoints().clear();
        }));
    }


}