package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.util.List;

public class ChooseColumnRequest extends Payload {

    int columnNumber;
    List<Tile> orderedTiles;

    public ChooseColumnRequest(int columnNumber, List<Tile> orderedTiles) {
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
