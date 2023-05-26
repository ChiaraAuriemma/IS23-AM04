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
        if(UsernameField.getText().length() > 0 && UsernameField.getText().length() < 12){
            client.login(UsernameField.getText());
            GUIApplication.setNickname(UsernameField.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/JoinGame.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            GuiInterface currentController = fxmlLoader.getController();
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("My Shelfie");
            stage.setScene(scene);
            stage.show();
        }else{
            showAlert(Alert.AlertType.WARNING, "Login error", "Invalid username, try again");
        }
    }

    public void GotoCreateGame(ActionEvent actionEvent) throws IOException {
        if(UsernameField.getText().length() > 0 && UsernameField.getText().length() < 12){
            client.login(UsernameField.getText());
            GUIApplication.setNickname(UsernameField.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/CreateGame.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            GuiInterface currentController = fxmlLoader.getController();
            currentController.setClient(client);
            GUIApplication.setCurrentController(currentController);
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("My Shelfie");
            stage.setScene(scene);
            stage.show();
        }else{
            showAlert(Alert.AlertType.WARNING, "Login error", "Invalid username, try again");
        }
    }

    public void setUsername(String username){
        this.username = username ;
    }

    public String getUsername(){
        return username;
    }

    public void askNicknameAgain() throws IOException {
        showAlert(Alert.AlertType.WARNING, "Login error", "Invalid username, try again");
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        GuiInterface currentController = fxmlLoader.getController();
        currentController.setClient(client);
        GUIApplication.setCurrentController(currentController);
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

