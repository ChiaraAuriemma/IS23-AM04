package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    @FXML
    TextField chatMessage;

    @FXML
    ImageView shelfieP2;
    @FXML
    ImageView shelfieP3;
    @FXML
    ImageView shelfieP4;

    @FXML
    Label labelPoints2;
    @FXML
    Label labelPoints3;
    @FXML
    Label labelPoints4;

    @FXML
    Label Points2;
    @FXML
    Label Points3;
    @FXML
    Label Points4;
    @FXML
    Label Points1;

    private HashMap<Integer, Label> points;



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

        gridOfPlayers = new HashMap<>(4);
        nicknames = new HashMap<>(4);
        points = new HashMap<>(4);

        nicknames.put(0, Player1);
        gridOfPlayers.put(0, Player1Grid);
        points.put(0,Points1);

        nicknames.put(1, Player2);
        gridOfPlayers.put(1, Player2Grid);
        points.put(1,Points2);

        nicknames.put(2, Player3);
        gridOfPlayers.put(2, Player3Grid);
        points.put(2,Points3);

        nicknames.put(3, Player4);
        gridOfPlayers.put(3, Player4Grid);
        points.put(3,Points4);


        int numPlayer = GUIApplication.getPlayers().size();
        switch (numPlayer){
            case 2:
                shelfieP3.setVisible(false);
                labelPoints3.setVisible(false);
                Points3.setVisible(false);
                shelfieP4.setVisible(false);
                labelPoints4.setVisible(false);
                Points4.setVisible(false);
                break;
            case 3:
                shelfieP4.setVisible(false);
                labelPoints4.setVisible(false);
                Points4.setVisible(false);
                break;
            default:
                break;
        }

        nicknames.forEach((k,v)->{
            if(k<GUIApplication.getPlayers().size() && GUIApplication.getPlayers().get(k) != null)
                v.setText(GUIApplication.getPlayers().get(k));
            else v.setVisible(false);
        });
        Player1.setTextFill(Color.BLUE);

        points.forEach((k,v) -> {
            if (k < GUIApplication.getPoints().size() && GUIApplication.getPoints().get(k) != null)
                v.setText(GUIApplication.getPoints().get(k).toString());
            else {
                v.setVisible(false);
            }
        });

        gridOfPlayers.forEach((k,v)->{
            if(k<GUIApplication.getPlayers().size() && GUIApplication.getPlayers().get(k) != null){
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
                }else v.setVisible(false);
            }else v.setVisible(false);
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

    public void sendMessage(ActionEvent actionEvent) throws IOException{
        if(chatMessage.getText().length()!=0){
            if(chatMessage.getText().contains(">>")){
                String[] splittedMessage = chatMessage.getText().split("<<");
                if(splittedMessage.length == 1){
                    GUIApplication.showAlert(Alert.AlertType.WARNING, "Empty message error", "You didn't write anything");
                }else{
                    String message = splittedMessage[1];
                    String receiver = splittedMessage[0];
                    client.sendChatPrivateMessage(message, receiver);
                }
            }else{
                client.sendChatMessage(chatMessage.getText());
            }
        }else{
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Empty message error", "You didn't write anything");
        }
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
