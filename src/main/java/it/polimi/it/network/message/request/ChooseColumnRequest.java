package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.List;

public class ChooseColumnRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -1516091115924889631L;
    int columnNumber;

    public ChooseColumnRequest(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

}
