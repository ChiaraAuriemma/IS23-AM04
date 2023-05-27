package it.polimi.it.view.GUI;

import it.polimi.it.network.client.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;

public class GUIApplication extends Application {

    private static GUIHandler guiHandler;
    private static GuiInterface currentController;

    private static ClientInterface client;

    private static String createOrJoin;
    private static String nickname;

    private static URL PersonalCard;
    private static URL CommonCard1;
    private static URL CommonCard2;
    private static Stage stageRef;


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

    public static void changeScene() throws IOException {
        /*
        if(client.getGameStage().equals(TurnStages.LOGIN)){
            return "/Login.fxml";
        }
        if(client.getGameStage().equals(TurnStages.CREATEorJOIN)){
            if(createOrJoin.equals("JOIN"))
                return "/JoinGame.fxml";
            else return "/CreateGame.fxml";
        }
        if(client.getGameStage().equals(TurnStages.NOTHING)){
            return "/WaitingRoom.fxml";
        }
        if(client.getGameStage().equals(TurnStages.NOTURN) || client.getGameStage().equals(TurnStages.TILESNUM) ||
                client.getGameStage().equals(TurnStages.CHOOSETILES) || client.getGameStage().equals(TurnStages.CHOOSECOLUMN)){
            return "/Game.fxml";
        }

         */

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
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/ChooseColumn.fxml"));
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
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/ChooseTilesNum.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            setCurrentController(fxmlLoader.getController());
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stageRef.setTitle("My Shelfie");
            stageRef.setScene(scene);
            stageRef.setResizable(false);
            stageRef.show();
        }
        if(client.getGameStage().equals(TurnStages.NOTURN) || client.getGameStage().equals(TurnStages.CHOOSETILES)){
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

    public static GuiInterface getCurrentController(){
        return currentController;
    }

    public static void setCurrentController(GuiInterface guiControllerRef){
        currentController = guiControllerRef;
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
