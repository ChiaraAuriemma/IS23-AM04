package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EndGameController implements GuiInterface, Initializable {

    ClientInterface client;

    Stage stage;

    private GUIApplication guiApp;

    private GUIHandler guiHandler;
    private HashMap<Integer,Label> nicknames;
    private HashMap<Integer, Label> points;

    @FXML
    Label player1;
    @FXML
    Label player2;
    @FXML
    Label player3;
    @FXML
    Label player4;

    @FXML
    Label points1;
    @FXML
    Label points2;
    @FXML
    Label points3;
    @FXML
    Label points4;

    @FXML
    ListView<Label> leaderBoard;

    private ObservableList<Label> finalPoints;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nicknames = new HashMap<>(4);
        points = new HashMap<>(4);
        finalPoints = FXCollections.observableArrayList();

        nicknames.put(0, player1);
        points.put(0, points1);

        nicknames.put(1, player2);
        points.put(1, points2);

        nicknames.put(2, player3);
        points.put(2, points3);

        nicknames.put(3, player4);
        points.put(3, points4);

        nicknames.forEach((k, v) -> {
            if (k < GUIApplication.getPlayers().size() && GUIApplication.getPlayers().get(k) != null){
                v.setText(GUIApplication.getPlayers().get(k));
                v.setOpacity(1);
            }
        });

        points.forEach((k,v) -> {
            if (k < GUIApplication.getPlayers().size() && GUIApplication.getPoints().get(k) != null){
                v.setText(GUIApplication.getPoints().get(k).toString());
                v.setOpacity(1);
            }
        });


        while(points.size() != 0) {
            int maxValue = 0;
            int maxKey = 0;
            for (Map.Entry<Integer, Label> entry : points.entrySet()) {
                int key = entry.getKey();
                int value = Integer.parseInt(entry.getValue().toString());
                if (value > maxValue) {
                    maxValue = value;
                    maxKey = key;
                }
            }
            String mergedLabel = nicknames.get(maxKey).getText() + "    " +points.get(maxKey).getText();
            Label person = new Label(mergedLabel);
            finalPoints.add(person);
            points.remove(maxKey);
            nicknames.remove(maxKey);
        }
        leaderBoard.setItems(finalPoints);
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
