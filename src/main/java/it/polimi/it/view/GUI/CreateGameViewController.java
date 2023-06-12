package it.polimi.it.view.GUI;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;



import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Objects;

public class CreateGameViewController implements GuiInterface{

    private static Stage stage;

    private static ClientInterface client;
    private static GUIApplication guiApp;
    private static GUIHandler guiHandler;

    @FXML
    TextField NumOfPlayers;

    public void GotoGame(ActionEvent actionEvent) throws IOException {
        if(NumOfPlayers.getText().length() != 0) {
            int players = Integer.parseInt(NumOfPlayers.getText());
            client.createGame(players);
            GUIApplication.changeScene();
        }else {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Create Game error", "You must enter a number of players");
        }
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
        return "create_game";
    }
}