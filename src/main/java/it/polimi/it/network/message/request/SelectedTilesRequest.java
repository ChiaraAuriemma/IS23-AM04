package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.Payload;


import java.io.Serializable;
import java.util.List;

/**
 * TCP message used for sending to the server the client's List of Tiles that it wants to take
 */
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
