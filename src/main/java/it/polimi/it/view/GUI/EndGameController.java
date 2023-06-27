package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Controller of the "End Game" scene that is seen when the game ends and give the possibility to the clients to restart a new game o join a new one
 */
public class EndGameController implements GuiInterface, Initializable {

    ClientInterface client;
    @FXML
    TextFlow textFlow;


    /**
     * Method that initialize the end game scene when the game is finished, taking each client's points and creating a leaderboard in decrescendo order
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HashMap<String, Integer> fPoints = new HashMap<>(GUIApplication.getFinalPoints());
        ArrayList<String> total = new ArrayList<>(GUIApplication.getPlayers().size());

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



    /**
     * Setter method for the reference of the client
     * @param clientRef clientRef is the reference of the client
     */
    public void setClient(ClientInterface clientRef){
        client = clientRef;
    }

    /**
     * scene-switching method in JoinGame
     * @param actionEvent is the trigger of this method and in this case it's the click of the button "JoinGame"
     * @throws IOException .
     */
    public void GoToJoin(ActionEvent actionEvent) throws IOException {
        GUIApplication.setCreateOrJoin("JOIN");
        client.setStageToCreate();
        GUIApplication.changeScene();

    }

    /**
     * scene-switching method in CreateGame
     * @param actionEvent is the trigger of this method and in this case it's the click of the button "CreateGame"
     * @throws IOException .
     */
    public void GoToCreate(ActionEvent actionEvent) throws IOException {
        GUIApplication.setCreateOrJoin("CREATE");
        client.setStageToCreate();
        GUIApplication.changeScene();

    }
}
