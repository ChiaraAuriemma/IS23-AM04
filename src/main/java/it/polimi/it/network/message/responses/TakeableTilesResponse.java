package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.util.List;

public class TakeableTilesResponse extends Payload {

    List<List<Tile>> choosableTilesList;

    public TakeableTilesResponse(List<List<Tile>> choosableTilesList) {
        this.choosableTilesList = choosableTilesList;
    }

    public List<List<Tile>> getChoosableTilesList() {
        return choosableTilesList;
    }
}
