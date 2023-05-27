package it.polimi.it.network.client;

import it.polimi.it.model.Card.CommonGoalCards.CommonGoalCard;
import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.GUI.*;
import it.polimi.it.view.ViewInterface;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    }

    @Override
    public void setBoardView(Tile[][] matrix) {

    }

    @Override
    public void setPlayersShelfiesView(String player, Tile[][] shelfie) {

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
    public void takeableTiles(List<List<Tile>> choosableTilesList) {

    }

    @Override
    public void setPossibleColumns(boolean[] choosableColumns) {

    }

    @Override
    public void printError(String error) {

    }

    @Override
    public void setPlayersPointsView(String player, int points) {

    }

    @Override
    public void setFinalPoints(List<String> users, ArrayList<Integer> points) {

    }

    @Override
    public void setGameID(int gameId) {
        GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Create Game ID", "your game id is " + gameId);
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
    public void update() {

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
        GUIApplication.changeScene();
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
        GUIApplication.showAlert(Alert.AlertType.WARNING, "Login error", errorMessage);
    }

    @Override
    public void askNumPlayerAgain() {
        GUIApplication.showAlert(Alert.AlertType.WARNING, "Create Game error", "The game is for 2 to 4 players");
    }

    @Override
    public void askIDAgain() {
        GUIApplication.showAlert(Alert.AlertType.WARNING, "Join Game error", "This game id is invalid");
    }

    @Override
    public void askNumTilesAgain() {
        GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "you can choose from 1 to 3 tiles");
    }

    @Override
    public void askTilesAgain() {
        GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "You can only take adjacent tiles with a free edge");
    }

    @Override
    public void askColumnAgain() {
        GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "The number of the column is invalid");
    }

    @Override
    public void boardRefill() {

    }


    public ClientInterface getClient(){
        return client;
    }


}
