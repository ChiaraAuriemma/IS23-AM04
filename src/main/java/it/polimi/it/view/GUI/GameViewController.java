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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameViewController implements Initializable, GuiInterface {

    private static Stage stage;

    @FXML
    Label Player1;
    @FXML
    Label Player2;
    @FXML
    Label Player3;
    @FXML
    Label Player4;
    @FXML
    GridPane LivingRoom;


    private static ClientInterface client;
    private static GUIApplication guiApp;
    private static GUIHandler guiHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(GUIApplication.getPlayers().get(0) != null){
            Player1.setText(GUIApplication.getPlayers().get(0));
            if(Player1.getText().equals(GUIApplication.getNickname()))
                Player1.setTextFill(Color.RED);
        }
        if(GUIApplication.getPlayers().get(1) != null){
            Player2.setText(GUIApplication.getPlayers().get(1));
            if(Player2.getText().equals(GUIApplication.getNickname()))
                Player2.setTextFill(Color.RED);
        }
        if(GUIApplication.getPlayers().get(1) != null){
            Player3.setText(GUIApplication.getPlayers().get(1));
            if(Player3.getText().equals(GUIApplication.getNickname()))
                Player3.setTextFill(Color.RED);
        }
        if(GUIApplication.getPlayers().get(1) != null){
            Player4.setText(GUIApplication.getPlayers().get(1));
            if(Player4.getText().equals(GUIApplication.getNickname()))
                Player4.setTextFill(Color.RED);
        }

        Image[][] board;
        board = GUIApplication.getBoard();
        for(int i=0; i<9;i++){
            for(int j=0; j<9;j++){
                if(board[i][j] != null){
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(31);
                    imageView.setFitWidth(32);
                    imageView.setImage(board[i][j]);
                    LivingRoom.add(imageView,j,i);
                }
            }
        }
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

