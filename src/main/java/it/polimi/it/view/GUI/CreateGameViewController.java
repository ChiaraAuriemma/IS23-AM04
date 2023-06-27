package it.polimi.it.view.GUI;
import it.polimi.it.network.client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.io.IOException;


/**
 * Controller of the "CreateGame" scene that is seen when the player choose to create a new game
 */
public class CreateGameViewController implements GuiInterface{

    private static ClientInterface client;
    @FXML
    TextField NumOfPlayers;

    /**
     * Method that triggers when the button "Start" is pressed and change the scene in GameTurn
     * @param actionEvent is the trigger of this method and in this case it's the click of the button "Start"
     * @throws IOException ?
     */

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
}