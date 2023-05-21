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

public class CommonGoalCardController implements Initializable {
    private Stage stage;
    @FXML
    ImageView CommonGoalCard1;
    @FXML
    ImageView CommonGoalCard2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imageUrl1 = getClass().getResource("/Images/1.jpg");
        Image card1 = new Image(imageUrl1.toString());
        CommonGoalCard1.setImage(card1);
        URL imageUrl2 = getClass().getResource("/Images/2.jpg");
        Image card2 = new Image(imageUrl2.toString());
        CommonGoalCard2.setImage(card2);

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
