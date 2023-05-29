package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ChooseColumnController implements GuiInterface, Initializable {

    private static ClientInterface client;
    private static GUIApplication  guiApp;
    private static GUIHandler guiHandler;
    private static Stage stage;

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
                    //LivingRoom.add(imageView,j,i);
                }
            }
        }

    }

    public void ChooseColumn0(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(0);
    }

    public void ChooseColumn1(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(1);
    }

    public void ChooseColumn2(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(2);
    }

    public void ChooseButton3(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(3);
    }

    public void ChooseButton4(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(4);
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

}
