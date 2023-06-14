package it.polimi.it.view.GUI;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import it.polimi.it.network.client.TurnStages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GameViewController implements GuiInterface, Initializable {

    private static ClientInterface client;
    private static GUIApplication  guiApp;
    private static GUIHandler guiHandler;
    private static Stage stage;
    private static ArrayList<Tile> Tiles;

    private HashMap<Integer,GridPane> gridOfPlayers;
    private HashMap<Integer,Label> nicknames;
    private HashMap<Integer, ImageView> playersShelfie;
    private HashMap<Integer, Label> labelPoints;
    private HashMap<Integer, Label> points;

    @FXML
    GridPane LivingRoom;

    @FXML
    Label Player1;
    @FXML
    GridPane Player1Grid;
    @FXML
    Label Points1;
    @FXML
    ImageView shelfieP1;

    @FXML
    Label labelPoints1;

    @FXML
    VBox vBoxGrid;
    @FXML
    GridPane Player2Grid;
    @FXML
    GridPane Player3Grid;
    @FXML
    GridPane Player4Grid;

    @FXML
    VBox vBoxNicknames;
    @FXML
    Label Player2;
    @FXML
    Label Player3;
    @FXML
    Label Player4;

    @FXML
    VBox vBoxShelfie;
    @FXML
    ImageView shelfieP2;
    @FXML
    ImageView shelfieP3;
    @FXML
    ImageView shelfieP4;

    @FXML
    VBox vBoxLabelPoints;
    @FXML
    Label labelPoints2;
    @FXML
    Label labelPoints3;
    @FXML
    Label labelPoints4;

    @FXML
    VBox vBoxPoints;
    @FXML
    Label Points2;
    @FXML
    Label Points3;
    @FXML
    Label Points4;


    @FXML
    Label Text;
    @FXML
    TextField num;

    @FXML
    TextField chatMessage;
    @FXML
    TextArea textMessage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Tiles = new ArrayList<>(0);
        Image[][] board;
        board = GUIApplication.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != null) {
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(45);
                    imageView.setFitWidth(47);
                    imageView.setImage(board[i][j]);
                    LivingRoom.add(imageView, j, i);
                    int finalI = i;
                    int finalJ = j;
                    imageView.setOnMouseClicked(mouseEvent -> {
                        try {
                            imageView.setOpacity(0.5);
                            chooseTiles(imageView, finalI, finalJ);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }


        gridOfPlayers = new HashMap<>(4);
        nicknames = new HashMap<>(4);
        playersShelfie = new HashMap<>(4);
        labelPoints = new HashMap<>(4);
        points = new HashMap<>(4);

        nicknames.put(0, Player1);
        gridOfPlayers.put(0, Player1Grid);
        playersShelfie.put(0, shelfieP1);
        labelPoints.put(0,labelPoints1);
        points.put(0,Points1);
        
        nicknames.put(1, Player2);
        gridOfPlayers.put(1, Player2Grid);
        playersShelfie.put(1, shelfieP2);
        labelPoints.put(1,labelPoints2);
        points.put(1,Points2);
        
        nicknames.put(2, Player3);
        gridOfPlayers.put(2, Player3Grid);
        playersShelfie.put(2, shelfieP3);
        labelPoints.put(2,labelPoints3);
        points.put(2,Points3);
        
        nicknames.put(3, Player4);
        gridOfPlayers.put(3, Player4Grid);
        playersShelfie.put(3, shelfieP4);
        labelPoints.put(3,labelPoints4);
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

        /*
        try {
            if (client.getGameStage().equals(TurnStages.TILESNUM))
                Text.setText("Choose how many tiles you want by clicking on the board");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        try {
            if (client.getGameStage().equals(TurnStages.CHOOSETILES))
                Text.setText("You have already chosen number of tiles, now choose the tiles");

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        try {
            if (client.getGameStage().equals(TurnStages.CHOOSECOLUMN))
                Text.setText("You have already chosen number of tiles and tiles , you just have to choose the column!");

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

         */

        if(GUIApplication.getCurrentChat() != null)
            textMessage.setText(GUIApplication.getCurrentChat());
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
        return "choose_num_tiles";
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

    public void chooseTiles(ImageView imageView, int i, int j) throws IOException {
        PossibleColors color = getColor(imageView);
        Tile t = new Tile(i,j,color);
        Tiles.add(t);
        imageView.setOnMouseClicked(mouseEvent -> {
            imageView.setOpacity(1);
            try {
                removeTiles(imageView,i,j);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void removeTiles(ImageView imageView, int i, int j) throws IOException {
        PossibleColors color = getColor(imageView);
        Tile t = new Tile(i,j,color);
        Tiles.remove(t);
        GUIApplication.changeScene(); //--->meglio aggiornare o poter tornare indietro solo una volta??
    }

    public void ChooseTiles(ActionEvent actionEvent) throws IOException {
        if(!client.getGameStage().equals(TurnStages.CHOOSETILES)){
            GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Game rules", "It's not time to choose the tiles");
            GUIApplication.changeScene();
        }else {
            if(Tiles.size() == 0){
                GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
                GUIApplication.changeScene();
            }else {
                client.selectedTiles(Tiles);
            }
        }

        /*
        if(Tiles.size() == 0){
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
            GUIApplication.changeScene();
        }else {
            int num = Tiles.size();
            client.tilesNumMessage(num);
            //wait...
            client.selectedTiles(Tiles);
            int id = Integer.parseInt(((Button) (actionEvent.getSource())).getId());
            client.chooseColumn(id);
        }

         */

        /*
        if (client.getGameStage().equals(TurnStages.TILESNUM)) {
            if(Tiles.size() == 0){
                GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
                GUIApplication.changeScene();
            }else {
                int num = Tiles.size();
                client.tilesNumMessage(num);
                GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Game rules", "You have chosen"+num + "tiles");
            }

        }else if(client.getGameStage().equals(TurnStages.CHOOSETILES)){
            if(Tiles.size() == 0){
                GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
                GUIApplication.changeScene();
            }else {
                client.selectedTiles(Tiles);
                GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Game rules", "You have chosen the tiles");
            }

        }else if(client.getGameStage().equals(TurnStages.CHOOSECOLUMN)){
            int id = Integer.parseInt(((Button) (actionEvent.getSource())).getId());
            client.chooseColumn(id);
        }

         */



    }

    public void ChooseColumn(ActionEvent actionEvent) throws IOException {
        if(!client.getGameStage().equals(TurnStages.CHOOSECOLUMN)) {
            GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Game rules", "It's not time to choose the column");
            GUIApplication.changeScene();
        }else {
            int id = Integer.parseInt(((Button) (actionEvent.getSource())).getId());
            client.chooseColumn(id);
        }

    }

    public void ChooseNumberOfTiles(ActionEvent actionEvent) throws IOException {
        if(!client.getGameStage().equals(TurnStages.TILESNUM)) {
            GUIApplication.showAlert(Alert.AlertType.INFORMATION, "Game rules", "It's not time to choose the number of tiles");
            GUIApplication.changeScene();
        }else {
            if(num.getText().length() == 0){
                GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
                GUIApplication.changeScene();
            }else {
                String tmp = num.getText();
                int num = Integer.parseInt(tmp);
                client.tilesNumMessage(num);
            }
        }
    }


    public PossibleColors getColor(ImageView image){

        if((image.getImage().getUrl().equals((getClass().getResource("/Images/Trofei1.1.png")).toString()) || (image.getImage().getUrl().equals((getClass().getResource("/Images/Trofei1.2.png")).toString()))
                || (image.getImage().getUrl().equals((getClass().getResource("/Images/Trofei1.3.png")).toString())))){
            return PossibleColors.CYAN;
        }


        if((image.getImage().getUrl().equals((getClass().getResource("/Images/Piante1.1.png")).toString()) || (image.getImage().getUrl().equals((getClass().getResource("/Images/Piante1.2.png")).toString()))
                || (image.getImage().getUrl().equals((getClass().getResource("/Images/Piante1.3.png")).toString())))){
            return PossibleColors.PINK;
        }


        if((image.getImage().getUrl().equals((getClass().getResource("/Images/Giochi1.1.png")).toString()) || (image.getImage().getUrl().equals((getClass().getResource("/Images/Giochi1.2.png")).toString()))
                || (image.getImage().getUrl().equals((getClass().getResource("/Images/Giochi1.3.png")).toString())))){
            return PossibleColors.YELLOW;
        }


        if((image.getImage().getUrl().equals((getClass().getResource("/Images/Cornici1.png")).toString()) || (image.getImage().getUrl().equals((getClass().getResource("/Images/Cornici1.2.png")).toString()))
                || (image.getImage().getUrl().equals((getClass().getResource("/Images/Cornici1.3.png")).toString())))){
            return PossibleColors.BLUE;
        }


        if((image.getImage().getUrl().equals((getClass().getResource("/Images/Gatti1.1.png")).toString()) || (image.getImage().getUrl().equals((getClass().getResource("/Images/Gatti1.2.png")).toString()))
                || (image.getImage().getUrl().equals((getClass().getResource("/Images/Gatti1.3.png")).toString())))){
            return PossibleColors.GREEN;
        }

        if((image.getImage().getUrl().equals((getClass().getResource("/Images/Libri1.1.png")).toString()) || (image.getImage().getUrl().equals((getClass().getResource("/Images/Libri1.2.png")).toString()))
                || (image.getImage().getUrl().equals((getClass().getResource("/Images/Libri1.3.png")).toString())))){
            return PossibleColors.WHITE;
        }

        return null;

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

}
