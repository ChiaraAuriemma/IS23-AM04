package it.polimi.it.view.GUI;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Objects;

public class LoginController implements GuiInterface{

    private static Stage stage;

    @FXML
    TextField UsernameField;
    @FXML
    Label LoginText;
    private String username;
    private static GUIApplication guiApp;
    private static GUIHandler guiHandler;
    private static ClientInterface client;


    public void GotoJoinGame(ActionEvent actionEvent) throws IOException {
        GUIApplication.setCreateOrJoin("JOIN");
        if(UsernameField.getText().length() > 0 && UsernameField.getText().length() < 12){
            client.login(UsernameField.getText());
            GUIApplication.setNickname(UsernameField.getText());
            /*
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource(Objects.requireNonNull(GUIApplication.changeScene())));
            Scene scene = new Scene(fxmlLoader.load());
            GuiInterface currentController = fxmlLoader.getController();
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("My Shelfie");
            stage.setScene(scene);
            stage.show();

             */
        }else{
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Login error", "Invalid username, try again");
        }
    }

    public void GotoCreateGame(ActionEvent actionEvent) throws IOException {
        GUIApplication.setCreateOrJoin("CREATE");
        if(UsernameField.getText().length() > 0 && UsernameField.getText().length() < 12){
            client.login(UsernameField.getText());
            GUIApplication.setNickname(UsernameField.getText());
            /*
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource(Objects.requireNonNull(GUIApplication.changeScene())));
            Scene scene = new Scene(fxmlLoader.load());
            GuiInterface currentController = fxmlLoader.getController();
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("My Shelfie");
            stage.setScene(scene);
            stage.show();

             */
        }else{
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Login error", "Invalid username, try again");
        }
    }

    public void setUsername(String username){
        this.username = username ;
    }

    public String getUsername(){
        return username;
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
    public void setClient(ClientInterface clientRef) {
        client = clientRef;
    }

    @Override
    public void setStage(Stage stageRef) {
        stage = stageRef;
    }

    @Override
    public String getType() {
        return "login";
    }

}

