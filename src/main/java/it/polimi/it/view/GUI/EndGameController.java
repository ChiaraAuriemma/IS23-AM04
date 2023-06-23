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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.Button;
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

    private ArrayList<String> total;


    @FXML
    TextFlow textFlow;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HashMap<String, Integer> fPoints = new HashMap<>(GUIApplication.getFinalPoints());
        total = new ArrayList<>(GUIApplication.getPlayers().size());

        for(String s : GUIApplication.getFinalPoints().keySet()){
            fPoints.put(s, GUIApplication.getFinalPoints().get(s));
        }

        int j = 1;
        while (fPoints.size()!= 0){
            int maxValue = 0;
            String maxKey = " ";
            for(String str : fPoints.keySet()){
                int value = fPoints.get(str);
                String key = str;
                if(value > maxValue){
                    maxValue = value;
                    maxKey = key;
                }
            }
            total.add(j + ": " + maxKey + "        " + maxValue);
            fPoints.remove(maxKey,maxValue);
            j++;
        }

        Text title = new Text("GG to the Winner");
        title.setFill(Color.RED);
        title.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
        textFlow.getChildren().addAll(title, new Text("\n"), new Text("\n"));

        for (String str : total){
               Text text = new Text(str);
               text.setFill(Color.BLUE);
               text.setFont(Font.font("Helvetica", FontWeight.BOLD, 22));
               textFlow.getChildren().addAll(text, new Text("\n"), new Text("\n"));
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
