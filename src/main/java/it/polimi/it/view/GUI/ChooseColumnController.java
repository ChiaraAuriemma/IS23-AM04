package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class ChooseColumnController implements GuiInterface {

    private static ClientInterface client;
    private static GUIApplication  guiApp;
    private static GUIHandler guiHandler;
    private static Stage stage;

    public void ChooseColumn1(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(1);
    }

    public void ChooseColumn2(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(2);
    }

    public void ChooseColumn3(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(3);
    }

    public void ChooseButton4(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(4);
    }

    public void ChooseButton5(ActionEvent actionEvent) throws RemoteException {
        client.chooseColumn(5);
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
