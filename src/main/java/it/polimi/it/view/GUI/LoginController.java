package it.polimi.it.view.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController {

    private Stage stage;
    private String userName;
    @FXML
    TextField UsernameField;

    public void GotoJoinGame(ActionEvent actionEvent) throws IOException {
        //devo mandare l'usernameField alla funzione di login
        userName = UsernameField.getText();
        System.out.println(userName);
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/JoinGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    public void GotoCreateGame(ActionEvent actionEvent) throws IOException {
        userName = UsernameField.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/CreateGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

}

