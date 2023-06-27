package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

/**
 * ShelfieUpdate Message: notifies a TCP client that a shelfie was updated.
 */
public class ShelfieUpdateMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = 3886151742823681824L;
    private String username;
    private int column;
    private Tile[][] newShelfie;

    public ShelfieUpdateMessage(String username, Tile[][] newShelfie){
        this.username = username;
        this.newShelfie=newShelfie;

    }

    public String getUsername() {
        return username;
    }

    public int getColumn() {
        return column;
    }

    public Tile[][] getShelfie() {
        return newShelfie;
    }
}
