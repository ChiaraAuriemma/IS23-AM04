package it.polimi.it.view.GUI;

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

public class CommonGoalCardController implements Initializable, GuiInterface {
    private static Stage stage;
    private URL common1;
    private URL common2;
    private static GUIApplication guiApp;
    private static GUIHandler guiHandler;
    private static ClientInterface client;

    @FXML
    ImageView CommonGoalCard1;
    @FXML
    ImageView CommonGoalCard2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL common1 = getClass().getResource("/Images/1.jpg");
        Image card1 = new Image(common1.toString());
        CommonGoalCard1.setImage(card1);
        URL common2 = getClass().getResource("/Images/2.jpg");
        Image card2 = new Image(common2.toString());
        CommonGoalCard2.setImage(card2);

    }

    public void setCommon1(URL image){
        common1 = image;
    }

    public void setCommon2(URL image){
        common2 = image;
    }

    public void GotoGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //guiApp.setCurrentController(fxmlLoader.getController());
        //guiApp.getCurrentController().setClient(client);
        GuiInterface currentController = fxmlLoader.getController();
        currentController.setClient(client);
        GUIApplication.setCurrentController(currentController);
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void setReferenceGUI(GUIApplication guiRef) {
        guiApp = guiRef;
    }

    public void setClient(ClientInterface clientRef){
        client = clientRef;
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
        return "common";
    }
}
