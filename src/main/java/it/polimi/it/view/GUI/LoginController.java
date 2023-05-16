package it.polimi.it.view.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private Stage stage;
    public void GotoJoinGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/JoinGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    public void GotoCreateGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("CreateGame.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }
}

