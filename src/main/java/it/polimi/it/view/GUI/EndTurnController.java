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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EndTurnController implements GuiInterface, Initializable {

    ClientInterface client;

    Stage stage;

    private GUIApplication guiApp;

    private GUIHandler guiHandler;

    private HashMap<Integer,GridPane> gridOfPlayers;
    private HashMap<Integer,Label> nicknames;

    @FXML
    GridPane LivingRoom;
    @FXML
    Label Player1;
    @FXML
    Label Player2;
    @FXML
    Label Player3;
    @FXML
    Label Player4;

    @FXML
    GridPane Player1Grid;

    @FXML
    GridPane Player2Grid;

    @FXML
    GridPane Player3Grid;

    @FXML
    GridPane Player4Grid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image[][] board;
        board = GUIApplication.getBoard();
        for(int i=0; i<9;i++){
            for(int j=0; j<9;j++){
                if(board[i][j] != null){
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(45);
                    imageView.setFitWidth(47);
                    imageView.setImage(board[i][j]);
                    LivingRoom.add(imageView,j,i);
                }
            }
        }

        gridOfPlayers = new HashMap<>();
        nicknames = new HashMap<>();
        nicknames.put(0,Player1);
        gridOfPlayers.put(0,Player1Grid);
        nicknames.put(1,Player2);
        gridOfPlayers.put(1,Player2Grid);
        nicknames.put(2,Player3);
        gridOfPlayers.put(2,Player3Grid);
        nicknames.put(3,Player4);
        gridOfPlayers.put(3,Player4Grid);

        nicknames.forEach((k,v)->{
            if(GUIApplication.getPlayers().get(k) != null)
                v.setText(GUIApplication.getPlayers().get(k));
            else v.setVisible(false);
        });
        Player1.setTextFill(Color.BLUE);

        gridOfPlayers.forEach((k,v)->{
            if(GUIApplication.getPlayers().get(k) != null){
                Image[][] shelfImage1;
                if(GUIApplication.getShelfies() != null && GUIApplication.getShelfies().get(GUIApplication.getPlayers().get(k)) != null){
                    shelfImage1 = GUIApplication.getShelfies().get(GUIApplication.getPlayers().get(k));
                    for(int i=0; i < 6 ;i++){
                        for(int j=0; j<5;j++){
                            if(shelfImage1[i][j] != null){
                                ImageView imageView = new ImageView();
                                if(k == 0){
                                    imageView.setFitWidth(43);
                                    imageView.setFitHeight(40);
                                }else {
                                    imageView.setFitHeight(20);
                                    imageView.setFitWidth(19);
                                }
                                imageView.setImage(shelfImage1[i][j]);
                                v.add(imageView,j,5-i);
                            }
                        }
                    }
                }
            }
        });


    }
    public void GoToPersonalGoalCard(ActionEvent actionEvent) throws IOException {
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

    public void GoToCommonGoalCards(ActionEvent actionEvent) throws IOException {
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
        return "create_game";
    }


}
