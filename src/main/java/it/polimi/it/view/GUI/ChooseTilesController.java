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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseTilesController implements GuiInterface, Initializable {

    private static ClientInterface client;
    private static GUIApplication  guiApp;
    private static GUIHandler guiHandler;
    private static Stage stage;
    private Image tmp1,tmp2,tmp3;

    private int count = 0;
    private static ArrayList<Tile> Tiles;



    @FXML
    GridPane LivingRoom;

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
    }


    public void chooseTiles(ImageView imageView, int i, int j) throws IOException {
        PossibleColors color = getColor(imageView);
        Tile t = new Tile(i,j,color);
        Tiles.add(t);
        sendTiles();
    }

    public void sendTiles() throws IOException {
        if(GUIApplication.getNumTiles() == Tiles.size()) {
            client.selectedTiles(Tiles);
        }
    }


    public PossibleColors getColor(ImageView image){
        tmp1 = new Image(getClass().getResource("/Images/Trofei1.1.png").toString());
        tmp2 = new Image(getClass().getResource("/Images/Trofei1.2.png").toString());
        tmp3 = new Image(getClass().getResource("/Images/Trofei1.3.png").toString());
        if(image.getImage().equals(tmp1) || image.getImage().equals(tmp2) || image.getImage().equals(tmp3)){
            return PossibleColors.CYAN;
        }

        tmp1 = new Image(getClass().getResource("/Images/Piante1.1.png").toString());
        tmp2 = new Image(getClass().getResource("/Images/Piante1.2.png").toString());
        tmp3 = new Image(getClass().getResource("/Images/Piante1.3.png").toString());
        if(image.getImage().equals(tmp1) || image.getImage().equals(tmp2) || image.getImage().equals(tmp3)){
            return PossibleColors.PINK;
        }

        tmp1 = new Image(getClass().getResource("/Images/Giochi1.1.png").toString());
        tmp2 = new Image(getClass().getResource("/Images/Giochi1.2.png").toString());
        tmp3 = new Image(getClass().getResource("/Images/Giochi1.3.png").toString());
        if(image.getImage().equals(tmp1) || image.getImage().equals(tmp2) || image.getImage().equals(tmp3)){
            return PossibleColors.YELLOW;
        }

        tmp1 = new Image(getClass().getResource("/Images/Cornici1.png").toString());
        tmp2 = new Image(getClass().getResource("/Images/Cornici1.2.png").toString());
        tmp3 = new Image(getClass().getResource("/Images/Cornici1.3.png").toString());
        if(image.getImage().equals(tmp1) || image.getImage().equals(tmp2) || image.getImage().equals(tmp3)){
            return PossibleColors.BLUE;
        }

        tmp1 = new Image(getClass().getResource("/Images/Gatti1.1.png").toString());
        tmp2 = new Image(getClass().getResource("/Images/Gatti1.2.png").toString());
        tmp3 = new Image(getClass().getResource("/Images/Gatti1.3.png").toString());
        if(image.getImage().equals(tmp1) || image.getImage().equals(tmp2) || image.getImage().equals(tmp3)){
            return PossibleColors.GREEN;
        }

        tmp1 = new Image(getClass().getResource("/Images/Libri1.1.png").toString());
        tmp2 = new Image(getClass().getResource("/Images/Libri1.2.png").toString());
        tmp3 = new Image(getClass().getResource("/Images/Libri1.3.png").toString());
        if(image.getImage().equals(tmp1) || image.getImage().equals(tmp2) || image.getImage().equals(tmp3)){
            return PossibleColors.WHITE;
        }

        return null;
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
        return "choose_tiles";
    }

}
