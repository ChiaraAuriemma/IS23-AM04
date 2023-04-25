package it.polimi.it.network.message.responses;

import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;

import java.util.List;

public class TakeableTilesResponse extends Message {

    List<List<Tile>> choosableTilesList;

    public TakeableTilesResponse(MessageType messageType, List<List<Tile>> choosableTilesList) {
        super(messageType);
        this.choosableTilesList = choosableTilesList;
    }

    public List<List<Tile>> getChoosableTilesList() {
        return choosableTilesList;
    }
}
