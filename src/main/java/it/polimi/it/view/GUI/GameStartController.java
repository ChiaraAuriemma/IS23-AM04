package it.polimi.it.view.GUI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.ClientRMIApp;
import it.polimi.it.network.client.ClientTCP;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.*;
import java.rmi.NotBoundException;


//public class GameStartController implements GuiInterface {

    /*
    private static Stage stage;
    private ClientInterface client;
    private GUIApplication guiApp;
    private GUIHandler guiHandler;

    public void GotoLoginTCP(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //guiApp.setCurrentScene(scene);
        guiApp.setCurrentController(fxmlLoader.getController());
        guiApp.getCurrentController().setReferenceGUI(guiApp);
        setUpTCP();
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    public void GotoLoginRMI(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //guiApp.setCurrentScene(scene);
        guiApp.setCurrentController(fxmlLoader.getController());
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    public void setUpTCP() throws IOException {
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(new FileReader("src/main/resources/ServerConfig.json"));
        JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
        ClientTCP clientTCP = new ClientTCP(jsonObject.get("portTCP").getAsInt(), jsonObject.get("ip").getAsString());
        BufferedReader in = new BufferedReader(new InputStreamReader(clientTCP.getServerSocket().getInputStream()));
        GUIHandler guiHandler = new GUIHandler();
        //guiApp.setClientTOGUI(in,guiHandler,clientTCP);
        Thread thread = new Thread(guiApp.getClientTOGui());
        thread.start();
    }

    public void setClient(ClientInterface client){
        this.client = client;
    }

    @Override
    public void setReferenceGUI(GUIApplication gui) {
        this.guiApp = gui;
    }

    @Override
    public void setReferenceHandler(GUIHandler handler) {
        this.guiHandler = handler;
    }

    @Override
    public void setStage(Stage stageRef) {
        stage = stageRef;
    }

    @Override
    public String getType() {
        return "start";
    }

     */


//}