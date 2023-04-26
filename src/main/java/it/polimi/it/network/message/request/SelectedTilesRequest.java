package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.Payload;


import java.util.List;

public class SelectedTilesRequest extends Payload {

    List<Tile> choosenTiles;

    public SelectedTilesRequest(MessageType messageType, List<Tile> choosenTiles) {
        this.choosenTiles = choosenTiles;
    }

    public List<Tile> getChoosenTiles() {
        return choosenTiles;
    }
}
