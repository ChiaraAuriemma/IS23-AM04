package it.polimi.it.view.GUI;
import it.polimi.it.network.client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;


/**
 * Controller of the Login scene that it's visualized when a client connects to the server; here te client has ti write its nickname
 * and choose between create or join a game
 */
public class LoginController implements GuiInterface{


    @FXML
    TextField UsernameField;
    @FXML
    Label LoginText;
    private static ClientInterface client;

    /**
     * Method that trigger when the button "Join Game" is pressed after the client has written his nickname; it changes scene to "Join game"
     * @param actionEvent is the trigger of the method and in this case it's the press of the button "Join Game"
     * @throws IOException .
     */

    public void GotoJoinGame(ActionEvent actionEvent) throws IOException {
        GUIApplication.setCreateOrJoin("JOIN");
        if(UsernameField.getText().length() > 0 && UsernameField.getText().length() < 12){
            String nickname = UsernameField.getText();
            while(nickname.length() < 12){
                nickname = nickname.concat(" ");
            }
            client.login(nickname);
            GUIApplication.setNickname(nickname);
        }else{
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Login error", "Invalid username, try again");
        }
    }
    /**
     * Method that trigger when the button "Create Game" is pressed after the client has written his nickname; it changes scene to "Create game"
     * @param actionEvent is the trigger of the method and in this case it's the press of the button "Create Game"
     * @throws IOException .
     */
    public void GotoCreateGame(ActionEvent actionEvent) throws IOException {
        GUIApplication.setCreateOrJoin("CREATE");
        if(UsernameField.getText().length() > 0 && UsernameField.getText().length() < 12){
            String nickname = UsernameField.getText();
            while(nickname.length() < 12){
                nickname = nickname.concat(" ");
            }
            client.login(nickname);
            GUIApplication.setNickname(nickname);
        }else{
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Login error", "Invalid username, try again");
        }
    }

    /**
     * Setter method for the reference of the client
     * @param clientRef is the reference of the client
     */
    @Override
    public void setClient(ClientInterface clientRef) {
        client = clientRef;
    }

}

