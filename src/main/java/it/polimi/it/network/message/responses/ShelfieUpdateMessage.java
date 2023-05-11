package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class ShelfieUpdateMessage extends Payload  implements Serializable {

    private static final long serialVersionUID = 3886151742823681824L;
    private User user;
    private int column;
    private Tile[][] newShelfie;

    public ShelfieUpdateMessage(User user, Tile[][] newShelfie){
        this.user = user;
        this.newShelfie=newShelfie;

    }

    public User getUser() {
        return user;
    }

    public int getColumn() {
        return column;
    }

    public Tile[][] getShelfie() {
        return newShelfie;
    }
}
