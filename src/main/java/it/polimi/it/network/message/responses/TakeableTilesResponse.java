package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;
import java.util.List;

public class TakeableTilesResponse extends Payload  implements Serializable {

    private static final long serialVersionUID = -4063188883685505347L;
    private List<List<Tile>> choosableTilesList;

    private int num;
    public TakeableTilesResponse(List<List<Tile>> choosableTilesList, int num) {
        this.choosableTilesList = choosableTilesList;
        this.num = num;
    }

    public List<List<Tile>> getChoosableTilesList() {
        return choosableTilesList;
    }

    public int getNum() {
        return num;
    }
}
