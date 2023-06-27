package it.polimi.it.view.GUI;

import it.polimi.it.network.client.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class of the GUI that manages the flow of the game in the GUI
 */
public class GUIApplication extends Application {

    private static GuiInterface currentController;
    private static ClientInterface client;
    private static String createOrJoin;
    private static String nickname;
    private static URL PersonalCard;
    private static URL CommonCard1;
    private static Integer IDCommon1;
    private static URL CommonCard2;
    private static Integer IDCommon2;
    private static Image[][] board = new Image[9][9];
    private static Stage stageRef;
    private static int numTiles;
    private static ArrayList<Integer> points = new ArrayList<>(4);
    private static ArrayList<String> players = new ArrayList<>(4);
    private static HashMap<String, Image[][]> shelfies = new HashMap<>();
    private static HashMap<String, Integer> finalPoints = new HashMap<>();
    private static String currentChat;


    /**
     * Method that takes the clients' nicknames and the clients' points at the end of the game
     * and put them in a HashMap
     * @param users is the List of the clients' nicknames
     * @param points is ArrayList of the clients' points
     */
    public static void setFinalPoint(List<String> users, ArrayList<Integer> points) {
        int i = 0;
        for(String s: users){
            finalPoints.put(s, points.get(i));
            i++;
        }
    }

    /**
     * Getter of the HashMap finalPoints
     * @return the HashMap final points
     */
    public static HashMap<String, Integer> getFinalPoints() {
        return finalPoints;
    }

    /**
     * Method that is called when the GUI starts and show the "Login" scene
     * @param stage is the stage of the client
     * @throws IOException .
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setCurrentController(fxmlLoader.getController());
        currentController.setClient(client);
        stageRef = stage;
        for(int i=0;i<4 ;i++){
            points.add(i,0);
        }
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    /**
     * Method that sets the visualization order of the nicknames in the scenes
     * @param order is the turn order of the game
     */
    public static void setPlayers(ArrayList<String> order){
        players.clear();
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

    /**
     * Method that change the scene based on the TurnStages of the client
     * @throws IOException .
     */
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
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/GameTurn.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/GameTurn.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/GameTurn.fxml"));
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
        if(client.getGameStage().equals(TurnStages.ENDGAME)){
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/EndGame.fxml"));
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

    /**
     * Method that prints the alerts
     * @param alertType is the type of the alert
     * @param title is the title of the alert
     * @param content is the content of the alert
     */
    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    /**
     * Getter that get the client's chat
     * @return the current chat
     */
    public static String getCurrentChat() {
        return currentChat;
    }


    /**
     * Setter that set the chat of the client
     * @param currentChat is the chat of the client
     */
    public static void setCurrentChat(String currentChat) {
        GUIApplication.currentChat = currentChat;
    }

    /**
     * Getter of the String createOrJoin
     * @return the string
     */
    public static String getCreateOrJoin() {
        return createOrJoin;
    }

    /**
     * Setter of the String createOrJoinRef
     * @param createOrJoinRef String based on the choice of the client to create or join the game
     */
    public static void setCreateOrJoin(String createOrJoinRef) {
        createOrJoin = createOrJoinRef;
    }


    /**
     * Setter of the client's nickname
     * @param nicknameRef is the reference of the client's nickname
     */
    public static void setNickname(String nicknameRef) {
        nickname = nicknameRef;
    }

    /**
     * Getter of the client's nickname
     * @return the client's nickname
     */
    public static String getNickname() {
        return nickname;
    }

    /**
     * Getter of the ArrayList of the players
     * @return the ArrayList of the players
     */
    public static ArrayList<String> getPlayers() {
        return players;
    }


    /**
     * Getter of the ArrayList of the players' points
     * @return the ArrayList of the players' points
     */
    public static ArrayList<Integer> getPoints() {
        return points;
    }


    /**
     * Setter of the players' points in the points ArrayList
     * @param i is the index of the arrayList based on the game order
     * @param p are the player's points
     */
    public static void setPoints(int i,int p) {
        points.set(i,p);
    }

    /**
     * Getter of the Personal card URL
     * @return the Personal card URL
     */
    public static URL getPersonalCard() {
        return PersonalCard;
    }


    /**
     * Setter of the Personal card URL
     * @param personalCardRef is the reference of the Personal card URL
     */
    public static void setPersonalCard(URL personalCardRef) {
        PersonalCard = personalCardRef;
    }


    /**
     * Getter of the first common card
     * @return the first common card URL
     */
    public static URL getCommonCard1() {
        return CommonCard1;
    }


    /**
     * Setter of the first common card URL and ID
     * @param commonCard1Ref is the first common card URL
     * @param id is the first common card ID
     */
    public static void setCommonCard1(URL commonCard1Ref, int id) {
        CommonCard1 = commonCard1Ref;
        IDCommon1 = id;
    }

    public static URL getCommonCard2() {
        return CommonCard2;
    }

    /**
     * Setter of the second common card URL and ID
     * @param commonCard2Ref is the second common card URL
     * @param id is the second common card ID
     */
    public static void setCommonCard2(URL commonCard2Ref, int id) {
        CommonCard2 = commonCard2Ref;
        IDCommon2 = id;
    }


    /**
     * Getter of the Image of the Living Room
     * @return the Image of the Living Room
     */
    public static Image[][] getBoard() {
        return board;
    }


    /**
     * Setter of Image of the Living Room
     * @param boardRef is the Image of the Living Room
     */
    public static void setBoard(Image[][] boardRef) {
        board = boardRef;
    }


    /**
     * Getter of the number of tiles that the client wants to take
     * @return the number of tiles that the client wants to take
     */
    public static int getNumTiles() {
        return numTiles;
    }


    /**
     * Setter of the number of tiles that the client wants to take
     * @param numTilesRef is the number of tiles that the client wants to take
     */
    public static void setNumTiles(int numTilesRef) {
        numTiles = numTilesRef;
    }


    /**
     * Setter of the controller bounded to the scene
     * @param guiControllerRef is the controller bounded to the scene
     */
    public static void setCurrentController(GuiInterface guiControllerRef){
        currentController = guiControllerRef;
    }


    /**
     * Getter of the HashMap of the Images of the client's bookshelves
     * @return the HashMap of the Images of the client's bookshelves
     */
    public static HashMap<String, Image[][]> getShelfies() {
        return shelfies;
    }


    /**
     * Setter of the HashMap of the Images of the client's bookshelves
     * @param player is the nickname of the client
     * @param shelfie is the Image of the client's bookshelf
     */
    public static void setShelfies(String player, Image[][] shelfie) {
        shelfies.put(player,shelfie);
    }


    /**
     * Setter of the client
     * @param clientInt is the client's reference
     */
    public static void setClient(ClientInterface clientInt){
        client = clientInt;
    }


    /**
     * Getter of the client
     * @return the client's reference
     */
    public static ClientInterface getClient() {
        return client;
    }


    /**
     * Getter of the ID of the second common card
     * @return the ID of the second common card
     */
    public static Integer getIDCommon2() {
        return IDCommon2;
    }


    /**
     * Getter of the ID of the first common card
     * @return the ID of the first common card
     */
    public static Integer getIDCommon1() {
        return IDCommon1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}