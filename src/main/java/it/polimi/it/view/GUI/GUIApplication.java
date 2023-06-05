package it.polimi.it.view.GUI;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.client.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIApplication extends Application {

    private static GUIHandler guiHandler;
    private static GuiInterface currentController;
    private static ClientInterface client;

    private static String createOrJoin;
    private static String nickname;

    private static URL PersonalCard;
    private static URL CommonCard1;
    private static URL CommonCard2;
    private static Image[][] board = new Image[9][9];
    private static Stage stageRef;

    private static int numTiles;

    private static ArrayList<String> players = new ArrayList<>(4);
    private static HashMap<String, Image[][]> shelfies = new HashMap<>();




    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setCurrentController(fxmlLoader.getController());
        currentController.setClient(client);
        stageRef = stage;
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void setPlayers(ArrayList<String> order){
        int i=0;
        int j=1;

        players.add(0,nickname);
        while(!nickname.equals(order.get(i))){
            i++;
        }
        i++;
        while(i<order.size()){
            players.add(j,order.get(i));
            i++;
            j++;
        }
        for(i=0; !order.get(i).equals(nickname); i++){
            players.add(j,order.get(i));
            j++;
        }
    }

    public static void changeScene() throws IOException {
        if(client.getGameStage().equals(TurnStages.LOGIN)){
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            setCurrentController(fxmlLoader.getController());
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stageRef.setTitle("My Shelfie");
            stageRef.setScene(scene);
            stageRef.setResizable(false);
            stageRef.show();
        }
        if(client.getGameStage().equals(TurnStages.CREATEorJOIN)){
            if(createOrJoin.equals("JOIN")){
                FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/JoinGame.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                setCurrentController(fxmlLoader.getController());
                currentController.setClient(client);
                GUIApplication.setCurrentController(currentController);
                stageRef.setTitle("My Shelfie");
                stageRef.setScene(scene);
                stageRef.setResizable(false);
                stageRef.show();
            }
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/CreateGame.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                setCurrentController(fxmlLoader.getController());
                currentController.setClient(client);
                GUIApplication.setCurrentController(currentController);
                stageRef.setTitle("My Shelfie");
                stageRef.setScene(scene);
                stageRef.setResizable(false);
                stageRef.show();
            }
        }
        if(client.getGameStage().equals(TurnStages.NOTHING)){
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/WaitingRoom.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            setCurrentController(fxmlLoader.getController());
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stageRef.setTitle("My Shelfie");
            stageRef.setScene(scene);
            stageRef.setResizable(false);
            stageRef.show();
        }
        if(client.getGameStage().equals(TurnStages.CHOOSECOLUMN)){
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Game.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            setCurrentController(fxmlLoader.getController());
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stageRef.setTitle("My Shelfie");
            stageRef.setScene(scene);
            stageRef.setResizable(false);
            stageRef.show();
        }
        if(client.getGameStage().equals(TurnStages.TILESNUM)){
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Game.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            setCurrentController(fxmlLoader.getController());
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stageRef.setTitle("My Shelfie");
            stageRef.setScene(scene);
            stageRef.setResizable(false);
            stageRef.show();
        }
        if(client.getGameStage().equals(TurnStages.CHOOSETILES)){
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Game.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            setCurrentController(fxmlLoader.getController());
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stageRef.setTitle("My Shelfie");
            stageRef.setScene(scene);
            stageRef.setResizable(false);
            stageRef.show();
        }
        if(client.getGameStage().equals(TurnStages.NOTURN)){
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/EndTurn.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            setCurrentController(fxmlLoader.getController());
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stageRef.setTitle("My Shelfie");
            stageRef.setScene(scene);
            stageRef.setResizable(false);
            stageRef.show();
        }


    }

    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static String getCreateOrJoin() {
        return createOrJoin;
    }

    public static void setCreateOrJoin(String createOrJoinRef) {
        createOrJoin = createOrJoinRef;
    }

    public static void setGuiHandler(GUIHandler guiHandlerRef){
        guiHandler = guiHandlerRef;
    }

    public static GUIHandler getGuiHandler(){
        return guiHandler;
    }

    public static void setNickname(String nicknameRef) {
        nickname = nicknameRef;
    }

    public static String getNickname() {
        return nickname;
    }

    public static ArrayList<String> getPlayers() {
        return players;
    }


    public static URL getPersonalCard() {
        return PersonalCard;
    }

    public static void setPersonalCard(URL personalCardRef) {
        PersonalCard = personalCardRef;
    }

    public static URL getCommonCard1() {
        return CommonCard1;
    }

    public static void setCommonCard1(URL commonCard1Ref) {
        CommonCard1 = commonCard1Ref;
    }

    public static URL getCommonCard2() {
        return CommonCard2;
    }

    public static void setCommonCard2(URL commonCard2Ref) {
        CommonCard2 = commonCard2Ref;
    }

    public static Image[][] getBoard() {
        return board;
    }

    public static void setBoard(Image[][] boardRef) {
        board = boardRef;
    }

    public static int getNumTiles() {
        return numTiles;
    }

    public static void setNumTiles(int numTilesRef) {
        numTiles = numTilesRef;
    }

    public static GuiInterface getCurrentController(){
        return currentController;
    }

    public static void setCurrentController(GuiInterface guiControllerRef){
        currentController = guiControllerRef;
    }

    public static HashMap<String, Image[][]> getShelfies() {
        return shelfies;
    }

    public static void setShelfies(String player, Image[][] shelfie) {
        shelfies.put(player,shelfie);
    }

    public static void setClient(ClientInterface clientint){
        client = clientint;
    }

    public static ClientInterface getClient() {
        return client;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
