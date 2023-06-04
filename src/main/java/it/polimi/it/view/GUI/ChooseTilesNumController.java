package it.polimi.it.view.GUI;

import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ChooseTilesNumController implements GuiInterface, Initializable {

    private static ClientInterface client;
    private static GUIApplication  guiApp;
    private static GUIHandler guiHandler;
    private static Stage stage;
    private static ArrayList<Tile> Tiles;

    private HashMap<Integer,GridPane> gridOfPlayers;
    private HashMap<Integer,Label> nicknames;


    @FXML
    GridPane LivingRoom;
    @FXML
    GridPane Player1Grid;
    @FXML
    GridPane Player2Grid;
    @FXML
    Label Player1;
    @FXML
    Label Player2;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        Tiles = new ArrayList<>(0);
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
                    int finalI = i;
                    int finalJ = j;
                    imageView.setOnMouseClicked(mouseEvent -> {
                        try {
                            chooseTiles(imageView, finalI, finalJ);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        }


        gridOfPlayers = new HashMap<>();
        nicknames = new HashMap<>();
        nicknames.put(0,Player1);
        gridOfPlayers.put(0,Player1Grid);
        nicknames.put(1,Player2);
        gridOfPlayers.put(1,Player2Grid);
        //nicknames.put(2,Player3);
        //gridOfPlayers.put(2,Player3Grid);
        //nicknames.put(3,Player4);
        //gridOfPlayers.put(3,Player4Grid);

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
    }

    public void EndTurn(ActionEvent actionEvent) throws IOException {
        if(Tiles.size() == 0){
            GUIApplication.showAlert(Alert.AlertType.WARNING, "Tiles error", "Invalid number, try again");
            GUIApplication.changeScene();
        }else{
            int num = Tiles.size();
            client.tilesNumMessage(num);
            client.selectedTiles(Tiles);
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

}
