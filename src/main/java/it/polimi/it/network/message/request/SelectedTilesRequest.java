package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;


import java.util.List;

public class SelectedTilesRequest extends Message {

    List<Tile> choosenTiles;

    public SelectedTilesRequest(MessageType messageType, List<Tile> choosenTiles) {
        super(messageType);
        this.choosenTiles = choosenTiles;
    }

    public List<Tile> getChoosenTiles() {
        return choosenTiles;
    }
}
