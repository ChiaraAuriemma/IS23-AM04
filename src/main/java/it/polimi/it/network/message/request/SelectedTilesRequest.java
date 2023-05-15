package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.Payload;


import java.io.Serializable;
import java.util.List;

public class SelectedTilesRequest extends Payload implements Serializable {

    private static final long serialVersionUID = -2041763978799635846L;
    private List<Tile> choosenTiles;

    public SelectedTilesRequest( List<Tile> choosenTiles) {
        this.choosenTiles = choosenTiles;
    }

    public List<Tile> getChoosenTiles() {
        return choosenTiles;
    }
}
