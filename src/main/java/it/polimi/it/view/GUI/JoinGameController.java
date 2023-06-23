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

/**
 * Controller of the "Join game" scene where the client has to write the game ID of the game it wants to connect
 */
public class JoinGameController implements GuiInterface {

    private static Stage stage;
    private static ClientInterface client;
    private static GUIApplication guiApp;
    private static GUIHandler guiHandler;


    @FXML
    TextField GameID;

    /**
     * Method that triggers when the button "Start" is pressed after the client has written the game ID to join a certain game
     * @param actionEvent is the trigger of this method and in this case it's represented by the click of the button "Start"
     * @throws IOException^
     */
    public void GotoGame(ActionEvent actionEvent) throws IOException {
        if(GameID.getText().length() != 0) {
            int id = Integer.parseInt(GameID.getText());
            client.joinGame(id);
            GUIApplication.changeScene();
        }else {
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Join Game error", "You must enter a game id");
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
        return "join";
    }
}