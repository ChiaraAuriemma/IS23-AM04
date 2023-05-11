package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.List;

public class TakeableTilesResponse extends Payload  implements Serializable {

    private static final long serialVersionUID = -4063188883685505347L;
    List<List<Tile>> choosableTilesList;

    public TakeableTilesResponse(List<List<Tile>> choosableTilesList) {
        this.choosableTilesList = choosableTilesList;
    }

    public List<List<Tile>> getChoosableTilesList() {
        return choosableTilesList;
    }
}
