package it.polimi.it.network.message.request;



import it.polimi.it.network.message.Payload;

import java.io.Serializable;

public class TilesNumRequest extends Payload  implements Serializable {

    private static final long serialVersionUID = -7710363129197514326L;
    int numTiles;

    public TilesNumRequest(int numTiles) {
        this.numTiles = numTiles;
    }

    public int getNumTiles() {
        return numTiles;
    }
}
