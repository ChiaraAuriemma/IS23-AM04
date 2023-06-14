package it.polimi.it.network.client;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.view.ViewInterface;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Serializable {

    /**
     *
     * @param userName
     * @throws RemoteException
     * @throws IOException
     */
    void login(String userName) throws RemoteException, IOException;

    void createGame(int playerNumber) throws RemoteException;

    void joinGame(int gameId) throws RemoteException;

    void tilesNumMessage(int numOfTiles) throws RemoteException;

    ViewInterface getView() throws RemoteException;

    void selectedTiles(List<Tile> choices) throws IOException;

    void chooseColumn(int column) throws RemoteException;

    void sendChatMessage(String chatMessage) throws RemoteException;

    void setView(String viewChoice) throws RemoteException;

    void setGameStage(GameStage gameStage) throws RemoteException;
    TurnStages getGameStage()throws RemoteException;

    void setStageToEndGame();

    void sendChatPrivateMessage(String chatMessage, String receiver) throws RemoteException;

}
