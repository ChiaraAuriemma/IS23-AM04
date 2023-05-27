package it.polimi.it.view.GUI;

import it.polimi.it.model.Card.PersonalGoalCards.PersonalGoalCard;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalGoalCardController implements Initializable, GuiInterface {

    private static Stage stage;
    private static ClientInterface client;
    private static GUIApplication guiApp;
    private static GUIHandler guiHandler;
    @FXML
    ImageView PersonalGoalCard;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image card = new Image(GUIApplication.getPersonalCard().toString());
        PersonalGoalCard.setImage(card);

    }


    public void GotoGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        GuiInterface currentController = fxmlLoader.getController();
        currentController.setClient(client);
        GUIApplication.setCurrentController(currentController);
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
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
        return "personal";
    }
}
