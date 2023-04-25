package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;

import java.util.List;

public class ChooseColumnRequest extends Message {

    int columnNumber;
    List<Tile> orderedTiles;

    public ChooseColumnRequest(MessageType messageType, int columnNumber, List<Tile> orderedTiles) {
        super(messageType);
        this.columnNumber = columnNumber;
        this.orderedTiles = orderedTiles;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public List<Tile> getOrderedTiles() {
        return orderedTiles;
    }
}
