package it.polimi.it.network.message.request;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Payload;

import java.util.List;

public class ChooseOrderRequest extends Payload {

    private int column;
    private List<Tile> chosen;

    public ChooseOrderRequest(int column, List<Tile> chosen){
        this.column = column;
        this.chosen = chosen;
    }

    public int getColumn() {
        return column;
    }

    public List<Tile> getChosen() {
        return chosen;
    }
}
