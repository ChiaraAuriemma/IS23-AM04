package it.polimi.it.network.message.request;


import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;

public class TilesNumRequest extends Message {

    int numTiles;

    public TilesNumRequest(MessageType messageType, int numTiles) {
        super(messageType);
        this.numTiles = numTiles;
    }

    public int getNumTiles() {
        return numTiles;
    }
}
