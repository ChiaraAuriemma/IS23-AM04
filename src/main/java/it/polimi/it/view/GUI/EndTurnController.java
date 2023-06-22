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
import javafx.scene.control.TextArea;


import java.awt.*;
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
    @FXML
    TextArea textMessage;

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
            case 3:
                shelfieP3.setOpacity(1);
                labelPoints3.setOpacity(1);
                Points3.setOpacity(1);
                break;
            case 4:
                shelfieP3.setOpacity(1);
                labelPoints3.setOpacity(1);
                Points3.setOpacity(1);
                shelfieP4.setOpacity(1);
                labelPoints4.setOpacity(1);
                Points4.setOpacity(1);
                break;
            default:
                break;
        }

        nicknames.forEach((k, v) -> {
            if (k < GUIApplication.getPlayers().size() && GUIApplication.getPlayers().get(k) != null){
                v.setText(GUIApplication.getPlayers().get(k));
                v.setOpacity(1);
            }
        });
        Player1.setTextFill(Color.BLUE);

        points.forEach((k,v) -> {
            if (k < GUIApplication.getPlayers().size() && GUIApplication.getPoints().get(k) != null){
                v.setText(GUIApplication.getPoints().get(k).toString());
                v.setOpacity(1);
            }
        });

        gridOfPlayers.forEach((k, v) -> {
            if (k < GUIApplication.getPlayers().size() && GUIApplication.getPlayers().get(k) != null) {
                Image[][] shelfImage1;
                if (GUIApplication.getShelfies() != null && GUIApplication.getShelfies().get(GUIApplication.getPlayers().get(k)) != null) {
                    shelfImage1 = GUIApplication.getShelfies().get(GUIApplication.getPlayers().get(k));
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (shelfImage1[i][j] != null) {
                                ImageView imageView = new ImageView();
                                if (k == 0) {
                                    imageView.setFitWidth(43);
                                    imageView.setFitHeight(40);
                                } else {
                                    imageView.setFitHeight(20);
                                    imageView.setFitWidth(19);
                                }
                                imageView.setImage(shelfImage1[i][j]);
                                v.add(imageView, j, 5 - i);
                            }
                        }
                    }
                }
            }

        });

        if(GUIApplication.getCurrentChat() != null)
            textMessage.setText(GUIApplication.getCurrentChat());

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
            String sender = client.getNickname().trim();
            if(chatMessage.getText().contains("<<")){
                String[] splittedMessage = chatMessage.getText().split("<<");
                if(splittedMessage.length == 1){
                    GUIApplication.showAlert(Alert.AlertType.WARNING, "Empty message error", "You didn't write anything");
                }else{
                    String message = sender + ": " + splittedMessage[1];
                    String receiver = splittedMessage[0];
                    client.sendChatPrivateMessage(message, receiver);
                }
            }else{
                client.sendChatMessage(sender + ": " + chatMessage.getText());
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