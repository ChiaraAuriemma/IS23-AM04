package it.polimi.it.view.GUI;

import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.GUIHandler;
import javafx.stage.Stage;

public interface GuiInterface {
    void setReferenceGUI(GUIApplication gui);
    void setReferenceHandler(GUIHandler handler);

    void setClient(ClientInterface client);

    void setStage(Stage stage);

    String getType();


}
