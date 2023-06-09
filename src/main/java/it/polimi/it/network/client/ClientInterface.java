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
    public void login(String userName) throws RemoteException, IOException;

    public void createGame(int playerNumber) throws RemoteException;

    public void joinGame(int gameId) throws RemoteException;

    public void tilesNumMessage(int numOfTiles) throws RemoteException;

    public ViewInterface getView() throws RemoteException;

    public void selectedTiles(List<Tile> choices) throws IOException;

    public void chooseColumn(int column) throws RemoteException;

    void sendChatMessage(String chatMessage) throws RemoteException;

    void setView(String viewChoice) throws RemoteException;

    void setGameStage(GameStage gameStage) throws RemoteException;
    TurnStages getGameStage()throws RemoteException;

    void sendChatPrivateMessage(String chatMessage, String receiver) throws RemoteException;

}
