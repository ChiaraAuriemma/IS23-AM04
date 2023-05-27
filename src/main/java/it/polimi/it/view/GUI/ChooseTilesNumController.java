package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.awt.*;
import javafx.scene.control.TextField;
import java.rmi.RemoteException;

public class ChooseTilesNumController implements GuiInterface{

    private static ClientInterface client;
    private static GUIApplication  guiApp;
    private static GUIHandler guiHandler;
    private static Stage stage;
    @FXML
    TextField NumOfTiles;

    @Override
    public void setReferenceGUI(GUIApplication guiRef) {
        guiApp = guiRef;
    }

    public void setClient(ClientInterface clientRef){
        client = clientRef;
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
        return "choose_num_tiles";
    }

    public void GoToChooseTiles(ActionEvent actionEvent) throws RemoteException {
        if(NumOfTiles.getText().length() !=0) {
            int num = Integer.parseInt(NumOfTiles.getText());
            client.tilesNumMessage(num);
        }else {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
        }
    }
}
