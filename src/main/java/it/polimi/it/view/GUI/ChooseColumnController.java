package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ChooseColumnController implements GuiInterface, Initializable {

    private static ClientInterface client;
    private static GUIApplication  guiApp;
    private static GUIHandler guiHandler;
    private static Stage stage;

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

        Player1.setText(GUIApplication.getNickname());
        Player1.setTextFill(Color.BLUE);

        int i=0;
        while(!GUIApplication.getNickname().equals(GUIApplication.getPlayers().get(i))){
            i++;
        }

        //tentativo di array circolare-->> da migliorare e estendere fino a 4 giocatori
        if(i==0){
            Player2.setText(GUIApplication.getPlayers().get(1));
        }else if(i==1){
            Player2.setText(GUIApplication.getPlayers().get(0));
        }


        Image[][] shelfImage;
        if(GUIApplication.getShelfies() != null && GUIApplication.getShelfies().get(Player1.getText()) != null){
            shelfImage = GUIApplication.getShelfies().get(Player1.getText());
            for(i=0; i < 6 ;i++){
                for(int j=0; j<5;j++){
                    if(shelfImage[i][j] != null){
                        ImageView imageView = new ImageView();
                        imageView.setFitHeight(36);
                        imageView.setFitWidth(41);
                        imageView.setImage(shelfImage[i][j]);
                        Player1Grid.add(imageView,j,5-i);
                    }
                }
            }
        }


        Image[][] shelfImage2;
        if(GUIApplication.getShelfies() != null && GUIApplication.getShelfies().get(Player2.getText()) != null) {
            shelfImage2 = GUIApplication.getShelfies().get(Player2.getText());
            for (i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (shelfImage2[i][j] != null) {
                        ImageView imageView = new ImageView();
                        imageView.setFitHeight(36);
                        imageView.setFitWidth(41);
                        imageView.setImage(shelfImage2[i][j]);
                        Player2Grid.add(imageView, j, 5-i);
                    }
                }
            }
        }

    }

    public void ChooseColumn(ActionEvent actionEvent) throws RemoteException {
        int num = Integer.parseInt(((Button)(actionEvent.getSource())).getId());
        client.chooseColumn(num);
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
        return "choose_column";
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
}
