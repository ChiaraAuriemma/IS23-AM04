package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.message.Payload;

import java.util.List;

public class ShelfieUpdateMessage extends Payload {

    private User user;
    private int column;
    private List<Tile> chosen;

    public ShelfieUpdateMessage(User user, int column, List<Tile> chosen){
        this.user = user;
        this.column = column;
        this.chosen = chosen;
    }

    public User getUser() {
        return user;
    }

    public int getColumn() {
        return column;
    }

    public List<Tile> getChosen() {
        return chosen;
    }
}
