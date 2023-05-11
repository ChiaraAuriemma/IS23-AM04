package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.List;

public class ChooseColumnRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -1516091115924889631L;
    int columnNumber;
    List<Tile> orderedTiles;

    public ChooseColumnRequest(int columnNumber) {
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
