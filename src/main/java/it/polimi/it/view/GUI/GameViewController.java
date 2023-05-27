package it.polimi.it.view.GUI;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameViewController implements Initializable, GuiInterface {

    private static Stage stage;
    @FXML
    ImageView tile1LV;
    @FXML
    ImageView tile2LV;
    @FXML
    ImageView tile3LV;
    @FXML
    ImageView tile4LV;
    @FXML
    ImageView tile5LV;
    @FXML
    ImageView tile6LV;
    @FXML
    ImageView tile7LV;
    @FXML
    ImageView tile8LV;
    @FXML
    ImageView tile9LV;
    @FXML
    ImageView tile10LV;
    @FXML
    ImageView tile11LV;
    @FXML
    ImageView tile12LV;
    @FXML
    ImageView tile13LV;
    @FXML
    ImageView tile14LV;
    @FXML
    ImageView tile15LV;
    @FXML
    ImageView tile16LV;
    @FXML
    ImageView tile17LV;
    @FXML
    ImageView tile18LV;
    @FXML
    ImageView tile19LV;
    @FXML
    ImageView tile20LV;
    @FXML
    ImageView tile21LV;
    @FXML
    ImageView tile22LV;
    @FXML
    ImageView tile23LV;
    @FXML
    ImageView tile24LV;
    @FXML
    ImageView tile25LV;
    @FXML
    ImageView tile26LV;
    @FXML
    ImageView tile27LV;
    @FXML
    ImageView tile28LV;
    @FXML
    ImageView tile29LV;
    @FXML
    ImageView tile30LV;
    @FXML
    ImageView tile31LV;
    @FXML
    ImageView tile32LV;
    @FXML
    ImageView tile33LV;
    @FXML
    ImageView tile34LV;
    @FXML
    ImageView tile35LV;
    @FXML
    ImageView tile36LV;
    @FXML
    ImageView tile37LV;
    @FXML
    ImageView tile38LV;
    @FXML
    ImageView tile39LV;
    @FXML
    ImageView tile40LV;
    @FXML
    ImageView tile41LV;
    @FXML
    ImageView tile42LV;
    @FXML
    ImageView tile43LV;
    @FXML
    ImageView tile44LV;
    @FXML
    ImageView tile45LV;
    @FXML
    ImageView tile1BS;
    @FXML
    ImageView tile2BS;
    @FXML
    ImageView tile3BS;
    @FXML
    ImageView tile4BS;
    @FXML
    ImageView tile5BS;
    @FXML
    ImageView tile6BS;
    @FXML
    ImageView tile7BS;
    @FXML
    ImageView tile8BS;
    @FXML
    ImageView tile9BS;
    @FXML
    ImageView tile10BS;
    @FXML
    ImageView tile11BS;
    @FXML
    ImageView tile12BS;
    @FXML
    ImageView tile13BS;
    @FXML
    ImageView tile14BS;
    @FXML
    ImageView tile15BS;
    @FXML
    ImageView tile16BS;
    @FXML
    ImageView tile17BS;
    @FXML
    ImageView tile18BS;
    @FXML
    ImageView tile19BS;
    @FXML
    ImageView tile20BS;
    @FXML
    ImageView tile21BS;
    @FXML
    ImageView tile22BS;
    @FXML
    ImageView tile23BS;
    @FXML
    ImageView tile24BS;
    @FXML
    ImageView tile25BS;
    @FXML
    ImageView tile26BS;
    @FXML
    ImageView tile27BS;
    @FXML
    ImageView tile28BS;
    @FXML
    ImageView tile29BS;
    @FXML
    ImageView tile30BS;
    @FXML
    Label Username;


    private double mouseAnchorX;
    private double mouseAnchorY;

    private static ClientInterface client;
    private static GUIApplication guiApp;
    private static GUIHandler guiHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Username.setText(GUIApplication.getNickname());
        URL imageUrl1 = getClass().getResource("/Images/Cornici1.png");
        Image tile1 = new Image(imageUrl1.toString());
        tile1LV.setImage(tile1);
        makeDraggable(tile1LV);
        URL imageUrl2 = getClass().getResource("/Images/Gatti1.1.png");
        Image tile2 = new Image(imageUrl2.toString());
        tile2LV.setImage(tile2);
        URL imageUrl3 = getClass().getResource("/Images/Libri1.2.png");
        Image tile3 = new Image(imageUrl3.toString());
        tile3LV.setImage(tile3);
        URL imageUrl12 = getClass().getResource("/Images/Piante1.3.png");
        Image tile12 = new Image(imageUrl12.toString());
        tile12LV.setImage(tile12);
        URL imageUrl36 = getClass().getResource("/Images/Trofei1.1.png");
        Image tile36 = new Image(imageUrl36.toString());
        tile36LV.setImage(tile36);
        URL imageUrl45 = getClass().getResource("/Images/Trofei1.2.png");
        Image tile45 = new Image(imageUrl45.toString());
        tile45LV.setImage(tile45);
    }


    public void makeDraggable(Node node){

        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });
    }

    public void GotoPersonalGoalCard(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/PersonalGoalCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        GuiInterface currentController = fxmlLoader.getController();
        currentController.setClient(client);
        GUIApplication.setCurrentController(currentController);
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("My Shelfie");
        stage.setScene(scene);
        stage.show();
    }

    public void GotoCommonGoalCards(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("/CommonGoalCards.fxml"));
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
        return "game";
    }
}

