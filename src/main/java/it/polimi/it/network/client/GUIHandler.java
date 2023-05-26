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
    private GUIApplication gui;
    private PersonalGoalCardController personalGoalCardController;
    private CommonGoalCardController commonGoalCardController;
    private LoginController loginController;





    public GUIApplication getGui(){
        return gui;
    }

    //--------------------------------------------------------------------------------------------------------------
    // metodi per inviare alla network informazioni




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
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals2.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals3.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals4.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals5.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals6.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals7.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals8.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals9.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals10.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals11.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/Personal_Goals12.png");
            personalGoalCardController.setPersonal(imageUrl);
        }
    }

    @Override
    public void setCommon1View(CommonGoalCard card1) {
        int id = card1.getID();
        if(id == 1){
            URL imageUrl = getClass().getResource("/Images/1.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/2.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/3.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/4.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/5.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/6.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/7.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/8.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/9.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/10.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/11.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/12.jpg");
            commonGoalCardController.setCommon1(imageUrl);
        }
    }

    @Override
    public void setCommon2View(CommonGoalCard card2) {
        int id = card2.getID();
        if(id == 1){
            URL imageUrl = getClass().getResource("/Images/1.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==2){
            URL imageUrl = getClass().getResource("/Images/2.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==3){
            URL imageUrl = getClass().getResource("/Images/3.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==4){
            URL imageUrl = getClass().getResource("/Images/4.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==5){
            URL imageUrl = getClass().getResource("/Images/5.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==6){
            URL imageUrl = getClass().getResource("/Images/6.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==7){
            URL imageUrl = getClass().getResource("/Images/7.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==8){
            URL imageUrl = getClass().getResource("/Images/8.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==9){
            URL imageUrl = getClass().getResource("/Images/9.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==10){
            URL imageUrl = getClass().getResource("/Images/10.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==11){
            URL imageUrl = getClass().getResource("/Images/11.jpg");
            commonGoalCardController.setCommon2(imageUrl);
        }
        if(id==12){
            URL imageUrl = getClass().getResource("/Images/12.jpg");
            commonGoalCardController.setCommon2(imageUrl);
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

    }

    @Override
    public void setEndToken(String user) {

    }

    @Override
    public void NotifyTurnStart(int maxValueofTiles, String username) {

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
    public void joinOrCreate(String username) {

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
    public void askColumn() {

    }

    @Override
    public void askNicknameAgain() {
        LoginController loginController = (LoginController) GUIApplication.getCurrentController();
        loginController.showAlert(Alert.AlertType.WARNING, "Login error", "This nickname is already in use");

    }

    @Override
    public void askNumPlayerAgain() {

    }

    @Override
    public void askIDAgain() {

    }

    @Override
    public void askNumTilesAgain() {

    }

    @Override
    public void askTilesAgain() {

    }

    @Override
    public void askColumnAgain() {

    }

    @Override
    public void boardRefill() {

    }

    public void setReferenceGui(GUIApplication gui){
        this.gui = gui;
    }

    public ClientInterface getClient(){
        return client;
    }


}
