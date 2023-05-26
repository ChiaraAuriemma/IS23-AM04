package it.polimi.it.view.GUI;

import it.polimi.it.network.client.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GUIApplication extends Application {

    private static GUIHandler guiHandler;
    private static GuiInterface currentController;

    private static ClientInterface client;


    private static String nickname;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        setCurrentController(fxmlLoader.getController());
        currentController.setClient(client);
        //GuiInterface currentController = fxmlLoader.getController();
        //currentController.setClient(client);
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.setResizable(false); //----> volendo si può evitare che la finestra venga ingrandita
        stage.show();
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
