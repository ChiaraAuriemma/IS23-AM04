package it.polimi.it.network.message.request;



import it.polimi.it.network.message.Payload;

public class TilesNumRequest extends Payload {

    int numTiles;

    public TilesNumRequest(int numTiles) {
        this.numTiles = numTiles;
    }

    public int getNumTiles() {
        return numTiles;
    }
}
