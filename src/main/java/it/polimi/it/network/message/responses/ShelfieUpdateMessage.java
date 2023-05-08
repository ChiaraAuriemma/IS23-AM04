package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

public class ShelfieUpdateMessage extends Payload {

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
