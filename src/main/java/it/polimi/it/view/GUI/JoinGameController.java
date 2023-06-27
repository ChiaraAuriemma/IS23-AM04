package it.polimi.it.view.GUI;
import it.polimi.it.network.client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.io.IOException;


/**
 * Controller of the "Join game" scene where the client has to write the game ID of the game it wants to connect
 */
public class JoinGameController implements GuiInterface {

    private static ClientInterface client;
    @FXML
    TextField GameID;

    /**
     * Method that triggers when the button "Start" is pressed after the client has written the game ID to join a certain game
     * @param actionEvent is the trigger of this method and in this case it's represented by the click of the button "Start"
     * @throws IOException .
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

    /**
     * Setter method for the reference of the client
     * @param clientRef clientRef is the reference of the client
     */
    public void setClient(ClientInterface clientRef){
        client = clientRef;
    }
}