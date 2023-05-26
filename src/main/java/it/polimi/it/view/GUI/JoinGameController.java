package it.polimi.it.view.GUI;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.io.IOException;

public class JoinGameController implements GuiInterface {

    private static Stage stage;
    private static ClientInterface client;
    private static GUIApplication guiApp;
    private static GUIHandler guiHandler;


    @FXML
    TextField GameID;


    public void GotoGame(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(GameID.getText());
        client.joinGame(id);
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //guiApp.setCurrentController(fxmlLoader.getController());
        //guiApp.getCurrentController().setReferenceGUI(guiApp);
        //guiApp.getCurrentController().setClient(client);
        GuiInterface currentController = fxmlLoader.getController();
        currentController.setClient(client);
        GUIApplication.setCurrentController(currentController);
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    public void setClient(ClientInterface clientRef){
        client = clientRef;
    }

    @Override
    public void setReferenceGUI(GUIApplication guiRef) {
        guiApp = guiRef;
    }

    @Override
    public void setReferenceHandler(GUIHandler handlerRef) {
        guiHandler = handlerRef;
    }

    @Override
    public void setStage(Stage stageRef) {
        stage = stageRef;
    }

    @Override
    public String getType() {
        return "join";
    }
}