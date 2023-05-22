package it.polimi.it.view.GUI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.network.client.ClientRMIApp;
import it.polimi.it.network.client.ClientTCP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;

public class GameStartController {

    private Stage stage;
    private String connectionType;
    private GUIApplication guiApplication;

    /*
    public GameStartController(GUIApplication guiApplication){
        this.guiApplication = guiApplication;
    }

     */

    public void GotoLoginTCP(ActionEvent actionEvent) throws IOException, NotBoundException {
        connectionType = "TCP";
        //setConnectionType(connectionType);
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    public void GotoLoginRMI(ActionEvent actionEvent) throws IOException {
        connectionType = "RMI";
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    /*
    public void setConnectionType(String connectionType) throws IOException, NotBoundException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader( new FileReader("src/main/resources/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader,JsonObject.class);
        if (connectionType.equalsIgnoreCase("TCP")){
            ClientTCP clientTCP = new ClientTCP(jsonObject.get("portTCP").getAsInt(),jsonObject.get("ip").getAsString(),guiApplication);
            Thread thread = new Thread(clientTCP);
            thread.start();
        }//if(connectionType.equalsIgnoreCase("RMI")){
           // ClientRMIApp clientRMI = new ClientRMIApp(jsonObject.get("portRMI").getAsInt(),jsonObject.get("ip").getAsString());
            //clientRMI.startClient();
        //}


    }

     */



}