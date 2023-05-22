package it.polimi.it.view.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalGoalCardController implements Initializable {

    private Stage stage;
    @FXML
    ImageView PersonalGoalCard;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imageUrl1 = getClass().getResource("/Images/Personal_Goals.png");
        Image card1 = new Image(imageUrl1.toString());
        PersonalGoalCard.setImage(card1);

    }
    public void GotoGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }
}
