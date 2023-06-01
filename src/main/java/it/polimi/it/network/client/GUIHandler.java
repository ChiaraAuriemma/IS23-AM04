package it.polimi.it.network.client;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.GUI.*;
import it.polimi.it.view.ViewInterface;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GUIHandler implements ViewInterface {

    private ClientInterface client;
    private ViewInterface view;





    //--------------------------------------------------------------------------------------------------------------
    // metodi per ricevere informazioni dalla network

    public void setView(ViewInterface view) {
        this.view = view;
    }

    public void setConnectionType(ClientInterface client) {
        this.client = client;
    }

    @Override
    public void setOrderView(ArrayList<String> order) {
        GUIApplication.setPlayers(order);
        //metodo per i nomi dei giocatori
    }

    @Override
    public void setBoardView(Tile[][] matrix) {
        Image[][] board = new Image[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String color = matrix[i][j].getColor();
                Image image = chooseAnImage(color);
                board[i][j] = image;
            }
        }
        GUIApplication.setBoard(board);
    }

    public Image chooseAnImage(String color){
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        if(color.equals("CYAN")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Trofei1.1.png");
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Trofei1.2.png");
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Trofei1.3.png");
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("PINK")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Piante1.1.png");
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Piante1.2.png");
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Piante1.3.png");
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("YELLOW")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Giochi1.1.png");
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Giochi1.2.png");
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Giochi1.3.png");
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("BLUE")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Cornici1.png");
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Cornici1.2.png");
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Cornici1.3.png");
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("GREEN")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Gatti1.1.png");
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Gatti1.2.png");
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Gatti1.3.png");
                return new Image(imageUrl.toString());

            }

        }
        if(color.equals("WHITE")){
            if(randomNumber == 1){
                URL imageUrl = getClass().getResource("/Images/Libri1.1.png");
                return new Image(imageUrl.toString());

            }
            if(randomNumber == 2){
                URL imageUrl = getClass().getResource("/Images/Libri1.2.png");
                return new Image(imageUrl.toString());

            }else {
                URL imageUrl = getClass().getResource("/Images/Libri1.3.png");
                return new Image(imageUrl.toString());

            }

        }

        return null;
    }

    @Override
    public void setPlayersShelfiesView(String player, Tile[][] shelfie) {
        Image[][] shelfImage = new Image[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                String color = shelfie[i][j].getColor();
                Image image = chooseAnImage(color);
                shelfImage[i][j] = image;
            }
        }
        GUIApplication.setShelfies(player,shelfImage);
    }

    @Override
    public void setPlayersPersonalCardView(PersonalGoalCard card) {
        int id = card.getId();
        if(id == 1){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals2.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals3.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals4.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals5.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals6.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals7.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals8.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals9.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals10.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals11.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals12.png");
            GUIApplication.setPersonalCard(imageUrl);
        }
    }

    @Override
    public void setCommon1View(CommonGoalCard card1) {
        int id = card1.getID();
        if(id == 1){
            URL imageUrl = getClass().getResource("/Images/1.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/2.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/3.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/4.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/5.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/6.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/7.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/8.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/9.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/10.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/11.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/12.jpg");
            GUIApplication.setCommonCard1(imageUrl);
        }
    }

    @Override
    public void setCommon2View(CommonGoalCard card2) {
        int id = card2.getID();
        if(id == 1){
            URL imageUrl = getClass().getResource("/Images/1.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/2.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/3.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/4.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/5.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/6.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/7.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/8.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/9.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/10.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/11.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/12.jpg");
            GUIApplication.setCommonCard2(imageUrl);
        }
    }

    @Override
    public void takeableTiles(List<List<Tile>> choosableTilesList, int num) {

    }

    @Override
    public void setPossibleColumns(boolean[] choosableColumns) {

    }

    @Override
    public void printError(String error) {
        Platform.runLater(new Thread(()-> {
                GUIApplication.showAlert(Alert.AlertType.WARNING, "Error", error);
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void setPlayersPointsView(String player, int points) {

    }

    @Override
    public void setFinalPoints(List<String> users, ArrayList<Integer> points) {

    }

    @Override
    public void setGameID(int gameId) {
        Platform.runLater(new Thread(()-> {
            if(GUIApplication.getCreateOrJoin().equals("CREATE"))
                GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Create Game ID", "Your game id is " + gameId);
        }));

    }

    @Override
    public void setEndToken(String user) {

    }

    @Override
    public void NotifyTurnStart(int maxValueofTiles, String username) {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void update() throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void updateChat(List<String> currentChat) {

    }

    @Override
    public void setThisNick(String nickname) {

    }

    @Override
    public String getTileColor(int row, int col) {
        return null;
    }

    @Override
    public void askNickname() {
    }

    @Override
    public void joinOrCreate(String username) throws IOException {
        Platform.runLater(new Thread(()-> {
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    @Override
    public void printTile(String color, int row, int column) {

    }

    @Override
    public void printThings(String s) {

    }

    @Override
    public void printCommands() {

    }

    @Override
    public void askColumn() throws IOException {
        GUIApplication.changeScene();
    }


    @Override
    public void askNicknameAgain(String errorMessage) {
        Platform.runLater(new Thread(()-> {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Login error", errorMessage);
        }));
    }

    @Override
    public void askNumPlayerAgain() {
        Platform.runLater(new Thread(()-> {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Create Game error", "The game is for 2 to 4 players");
        }));

    }

    @Override
    public void askIDAgain() {
        Platform.runLater(new Thread(()-> {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Join Game error", "This game id is invalid");;
        }));
    }

    @Override
    public void askNumTilesAgain() {
        Platform.runLater(new Thread(()-> {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "you can choose from 1 to 3 tiles");
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

    }

    @Override
    public void askTilesAgain() throws IOException {
        Platform.runLater(new Thread(()-> {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "You can only take adjacent tiles with a free edge");
            try {
                GUIApplication.changeScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

    }

    @Override
    public void askColumnAgain() {
        Platform.runLater(new Thread(()-> {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "The number of the column is invalid");
        }));
    }

    @Override
    public void boardRefill() {

    }



    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////
    //TE L'HO MESSO IO(FRA) OGGI PER FAR PARTIRE LA CLI
    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////
    /////////////////////////////////////////////

    @Override
    public void recover(int gameID, Tile[][] matrix, ArrayList<Tile[][]> shelfies, CommonGoalCard card1, CommonGoalCard card2, PersonalGoalCard personalGoalCard, ArrayList<Integer> points, List<String> playerList) {

    }


    public ClientInterface getClient(){
        return client;
    }


}
